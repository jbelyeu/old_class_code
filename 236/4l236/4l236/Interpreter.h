#pragma once
#include "Database.h"
#include "DatalogProgram.h"
#include "Predicate.h"
#include "Relation.h"
#include <fstream>
#include "Parameter.h"
#include "Rules.h"
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
		for (unsigned i = 0; i < parserSchemes.size(); i++)
		{
			predicate scheme = parserSchemes[i];
			string name = scheme.getName();
			vector<param> params = scheme.getParams();

			Scheme columnTitles;

			//loops through all params in the params vector, puts them in a Scheme. Seems very inefficient
			for (unsigned j = 0; j < params.size(); j++)
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

		for (unsigned i = 0; i < parserFacts.size(); i++)
		{
			predicate fact = parserFacts[i];
			Tuple data = predicateToTuple(parserFacts[i]);
			string name = fact.getName();

			database.addFact(name, data);
		}
	}

	//process rules. QUery with all predicates on right side of each rule, check joinability of resulting relations, join relations, add to correct scheme in DB
	void processRules()
	{
		vector<rule> rules = parser.getRules();
		int totalTuples = 0;

		//checks number of rows, processes rules, rechecks. If rows have been added, reprocesses
		do
		{
			totalTuples = database.getTotalRowCount();
			fixedPointAlg(rules);
			loopsToConverge++;
		} 
		while (database.getTotalRowCount() > totalTuples);
	}

	
	void processQueries(char* argv[])
	{
		vector<predicate> parserQueries = parser.getQueries();
		ofstream output;
		output.open(argv[2]);
		output << "Converged after " << loopsToConverge << " passes through the Rules." << endl;

		//loops through all queries in parser
		for (unsigned i = 0; i < parserQueries.size(); i++)
		{
			predicate query = parserQueries[i];
			Relation response;

			vector<string> allVars = processPredicate(query, response);

			
			output << print(argv, response, query, allVars);
			if (i < parserQueries.size() - 1)
			{
				output << endl;
			}
		}

	}
	

	~Interpreter(){}

private:
	DatalogProgram parser;
	Database database;
	int loopsToConverge;

	string print(char* argv[], Relation& response, predicate& query, vector<string>& allVars)
	{
		stringstream output;

		output << query.toString() << "? ";
		
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

					for (unsigned i = 0; i < allVars.size(); i++)
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
		for (unsigned i = 0; i < params.size(); i++)
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
		for (unsigned j = 0; j < params.size(); j++)
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
		for (unsigned j = 0; j < queryParams.size(); j++)
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

	void fixedPointAlg(vector<rule>& rules)
	{
		for (unsigned i = 0; i < rules.size(); i++)
		{
			vector<predicate> body = rules[i].getBody();

			//seems inefficient to store them thus and use them later. May change this
			vector<Relation> ruleRelations;

			for (unsigned j = 0; j < body.size(); j++)
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
			predicate head = rules[i].getHead();
			ruleRelations[0] = ruleRelations[0].project(head);
			database.Union(head.getName(), ruleRelations[0]);
		}
	}

};