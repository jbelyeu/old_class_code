#pragma once
#include "Database.h"
#include "DatalogProgram.h"
#include "Predicate.h"
#include "Relation.h"
#include <fstream>
#include "Parameter.h"
#include "Rules.h"
#include "Node.h"
#include "DependencyGraph.h"
#include <sstream>
using namespace std;

class Interpreter
{
public:
	Interpreter() :
		loopsToConverge(0)
	{}

	void create(char* argv[])
	{
		parser = DatalogProgram(argv);

		try
		{
			parser.parse();
		}
		catch (Token ex)
		{
			ofstream out;
			out.open(argv[2]);
			out << "Failure!\n  " << ex.toString();
		}

		database = Database(parser);
	}

	void loadSchemes()
	{
		vector<predicate> parserSchemes = parser.getSchemes();

		//loops through all schemes in the parser, creates relation for each, and saves it in the database
		for (unsigned i = 0; i < parserSchemes.size(); ++i)
		{
			predicate scheme = parserSchemes[i];
			string name = scheme.getName();
			vector<param> params = scheme.getParams();

			Scheme columnTitles;

			//loops through all params in the params vector, puts them in a Scheme. Seems very inefficient
			for (unsigned j = 0; j < params.size(); ++j)
			{
				columnTitles.push_back(params[j].getName());
			}

			Relation newtable = Relation(name, columnTitles);
			database.addTable(newtable);
		}
		
	}

	void loadFacts()
	{
		vector<predicate> parserFacts = parser.getFacts();

		for (unsigned i = 0; i < parserFacts.size(); ++i)
		{
			predicate fact = parserFacts[i];
			Tuple data = predicateToTuple(parserFacts[i]);
			string name = fact.getName();

			database.addFact(name, data);
		}
	}

	void createGraph()
	{
		vector<rule> rules = parser.getRules();
		vector<predicate> queries = parser.getQueries();

		queryDependencyGraph(rules, queries);
		ruleDependencyGraph(rules);
	}
	
	void processQueries(char* argv[])
	{
		vector<rule> rules = parser.getRules();
		vector<predicate> parserQueries = parser.getQueries();
		ofstream output;
		output.open(argv[2]);
		output << stringifyGraph();

		//loops through all queries in parser
		for (unsigned i = 0; i < parserQueries.size(); ++i)
		{
			predicate query = parserQueries[i];
			output << "\n\n" << query.toString() << "?";
			output << processRulesByOrderNum(i, rules); //processes rules and stringifies the post order numbers

			Relation response; //created here, passed and modified by reference in processPredicate()
			vector<string> allVars = processPredicate(query, response);

			output << print(argv, response, query, allVars);
		}
	}
	

	~Interpreter(){}

private:
	DatalogProgram parser;
	Database database;
	int loopsToConverge;
	DependencyGraph graph;
	const string QUERY = "Q";
	const string RULE = "R";

	string print(char* argv[], Relation& response, predicate& query, vector<string>& allVars)
	{
		stringstream output;

		output <<"\n\n" <<  query.toString() << "? ";
		
		if (response.getNumOfRows() == 0)
		{
			output << "No";
		}
		else
		{
			output << "Yes(" << response.getNumOfRows() << ")";
			set<Tuple> rows = response.getRows();

			vector<param> params = query.getParams();

			if (allVars.size() != 0)
			{
				for (auto iter = rows.begin(); iter != rows.end(); ++iter)
				{
					Tuple row = *iter;
					output << "\n  ";

					for (unsigned i = 0; i < allVars.size(); ++i)
					{
						output << allVars[i] << "='" << row[i] << "'";

						if (i < allVars.size() - 1)
						{
							output << ", ";
						}
					}
				}
			}
		}
		return output.str();
	}

	Scheme makeScheme(vector<param>& params)
	{
		Scheme newScheme;
		for (unsigned i = 0; i < params.size(); ++i)
		{
			newScheme.push_back(params[i].getName());
		}
		return newScheme;
	}

	//turn a single predicate into a tuple
	Tuple predicateToTuple(predicate& toFormat)
	{

		vector<param> params = toFormat.getParams();

		Tuple data;

		//loops through all params in the params vector, puts them in a Tuple. Seems very inefficient
		for (unsigned j = 0; j < params.size(); ++j)
		{
			data.push_back(params[j].getName());
		}
		return data;
	}

	vector<string> processPredicate(predicate& query, Relation& response)
	{
		vector<param> queryParams = query.getParams();
		string queryName = query.getName();

		Relation table = database.getTable(queryName);

		vector<pair<int, string>> queryConstants;
		vector<int> likeVarPositions;
		map<string, int> varsAndPositions; //using a map for searching speed, but I need a vector, too
		vector<string> allVars;
		vector<int> allVarPositions;

		//going to throw something together and improve later, probably with TA help
		for (unsigned j = 0; j < queryParams.size(); ++j)
		{
			string paramName = queryParams[j].getName();

			if (queryParams[j].isConstant())
			{
				//create pair of the location of the value and the value, store in vector
				pair<int, string> indexValue(j, paramName);
				queryConstants.push_back(indexValue);
			}
			//else if the query param is a variable and is not in the likeVars map already, it need to be put there
			else if (varsAndPositions.find(paramName) == varsAndPositions.end())
			{
				allVarPositions.push_back(j);
				varsAndPositions[paramName] = j;
				allVars.push_back(paramName);
			}
			else //variable has been repeated
			{
				//this assumes that a repeated variable is not repeated more than once and that there is only one repeated variable in the query
				likeVarPositions.push_back(varsAndPositions[paramName]);
				likeVarPositions.push_back(j);
			}
		}

		//run query using constants, then run again on results using the variables if there is a repeat. If not, variables should be meaningless
		response = table.select(queryConstants);

		if (likeVarPositions.size() == 2) //will fail if too many positions. Not currently possible, but be aware in case of changes
		{
			response = response.select(likeVarPositions[0], likeVarPositions[1]);
		}

		//cut out unwanted columns (non-variables in the query)
		response = response.project(allVarPositions);

		//rename columns in response to match query name
		response = response.rename(queryParams);
		return allVars;
	}

	void queryDependencyGraph(vector<rule>& rules, vector<predicate>& queries)
	{
		for (unsigned i = 0; i < queries.size(); ++i)
		{
			string nodeName = namer(QUERY, i);
			Node newNode;
			graph[nodeName] = newNode;

			for (unsigned j = 0; j < rules.size(); ++j)
			{
				//if name of rule head is same as name of query, query depends on rule. Add rule to Node->(set of rules)
				if (rules[j].getHead().getName() == queries[i].getName())
				{
					string dependencyName = namer(RULE, j);
					graph[nodeName].addDependency(dependencyName);
				}
			}
		}
	}

	void ruleDependencyGraph(vector<rule>& rules)
	{
		//loop through rules, through all predicates in the body of each one, then through the rules again. 
		//If the name of a predicate in the body is same as name of a rule, put into dependency graph
		for (unsigned i = 0; i < rules.size(); ++i)
		{
			vector<predicate> body = rules[i].getBody();

			//add rule to graph
			Node newNode;
			string nodeName = namer(RULE, i);
			graph[nodeName] = newNode;

			for (unsigned j = 0; j < body.size(); ++j)
			{
				string currentRuleName = body[j].getName();

				for (unsigned k = 0; k < rules.size(); k++)
				{
					if (currentRuleName == rules[k].getHead().getName())
					{
						string dependName = namer(RULE, k);
						graph[nodeName].addDependency(dependName);
					}
				}
			}
		}
	}

	//can't pass these params by reference
	string namer(const string LETTER, unsigned index)
	{
		stringstream streamName;
		streamName << LETTER << (index+1);
		return streamName.str();
	}

	string stringifyGraph()
	{
		stringstream graphStream;
		graphStream << "Dependency Graph";
		string key;

		for (auto& giter : graph)
		{
			graphStream << "\n  " << giter.first << ":";
			set<string> dependencies = giter.second.getEdges() ;
			for (auto& siter : dependencies)
			{
				graphStream << " " << siter;
			}
		}
		return graphStream.str();
	}

	string processRulesByOrderNum(unsigned i, vector<rule>& rules)
	{
		depthFirstSearch(i);
		map<string, int> postorders = graph.getPostOrder();
		string output = stringifyPostorders(postorders);

		string backEdges = graph.findCycles(postorders);
		map<int, string> correctPostOrd = sortPostOrders(postorders);

		//iterate forward over map to get rule evaluation order
		string evalOrder = getEvaluationOrder(correctPostOrd);
		string ruleEval = "\n\n  Rule Evaluation";


		if (backEdges != "\n\n  Backward Edges") //need to check this carefully
		{

			int totalTuples = 0;
			do
			{
				totalTuples = database.getTotalRowCount();
				fixedPointAlg(correctPostOrd, ruleEval, rules);
			}
			while (database.getTotalRowCount() > totalTuples);
		}
		else
		{
			fixedPointAlg(correctPostOrd, ruleEval, rules);
		}

		output += evalOrder + backEdges + ruleEval;
		return output;
	}

	void fixedPointAlg(map<int, string>& postorders, string& ruleEval, vector<rule>& rules)
	{
		for (auto miter : postorders)
		{
			if (miter.second[0] == 'R') //only want rules
			{
				string stringIndex = miter.second.substr(1);
				stringstream ss(stringIndex);
				int index = 0;
				ss >> index;
				index--;

				ruleEval += "\n    " + rules[index].toString(); //put into rule evaluation string
				processRule(rules[index]);  //process
			}
		}
	}

	map<int, string> sortPostOrders(map<string, int>& postorders)
	{
		map<int, string> correctOrder;
		
		for (auto miter : postorders)
		{
			if (miter.first[0] == 'R') //only want rules
			{
				correctOrder[miter.second] = miter.first;
			}
		}
		return correctOrder;
	}

	string getEvaluationOrder(map<int, string>& postorders)
	{
		string evalOrder = "\n\n  Rule Evaluation Order";
		
		for (auto miter : postorders)
		{
			evalOrder += "\n    " + miter.second; //put into rule eval order
		}

		return evalOrder;
	}

	string stringifyPostorders(map<string, int>& postorders)
	{
		stringstream mapStream;
		mapStream << "\n\n  Postorder Numbers";

		//iterate backward over map to get reverse postorder
		
		for (auto miter : postorders)
		{
			mapStream << "\n    " << miter.first << ": " << miter.second;
		}
		return mapStream.str();
	}

	//untested
	void depthFirstSearch(unsigned i)
	{
		//run depth first search of the graph using the given query as the starting point. 
		//After graph has been finished and visited has been set for each, loop back through 
		//the graph, get the post order numbers and corresponding IDs, return them

		graph.depthFirstSearch(namer(QUERY, i)); //no return, but next call gets the results
	}

	void processRule(rule& toProcess)
	{
		vector<predicate> body = toProcess.getBody();

		//seems inefficient to store them thus and use them later. May change this
		vector<Relation> ruleRelations;

		for (unsigned j = 0; j < body.size(); ++j)
		{
			//body is vector of predicates, so call processPredicate on each
			Relation response;
			processPredicate(body[j], response);

			if (ruleRelations.size() > 0)
			{
				//call function to join the already generated relation (there should never be more than one) and the new one
				ruleRelations[0] = ruleRelations[0].Join(response);
			}
			else
			{
				ruleRelations.push_back(response); //store the relation in vector. May not continue doing so in the future
			}
		}
		predicate head = toProcess.getHead();
		ruleRelations[0] = ruleRelations[0].project(head);
		database.Union(head.getName(), ruleRelations[0]);
	}

};