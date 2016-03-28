#pragma once
#include "Database.h"
#include "DatalogProgram.h"
#include "Predicate.h"
#include "Relation.h"
#include <fstream>
#include "Parameter.h"
using namespace std;

class Interpreter
{
public:
	Interpreter(){}

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
			string name = fact.getName();
			vector<param> params = fact.getParams();

			Tuple data;

			//loops through all params in the params vector, puts them in a Tuple. Seems very inefficient
			for (unsigned j = 0; j < params.size(); j++)
			{
				data.push_back(params[j].getName());
			}

			database.addFact(name, data);

		}
	}

	//CC of 8, watch out
	void processQueries(char* argv[])
	{
		vector<predicate> parserQueries = parser.getQueries();
		ofstream output;
		output.open(argv[2]);

		//loops through all queries in parser
		for (unsigned i = 0; i < parserQueries.size(); i++)
		{
			predicate query = parserQueries[i];
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
					likeVarPositions.push_back (j);
					//allVarPositions.push_back(j); //not sure if I need this one
				}
			}
			
			//run query using constants, then run again on results using the variables if there is a repeat. If not, variables should be meaningless
			Relation response = table.select(queryConstants);

			if (likeVarPositions.size() == 2) //will fail if too many positions. Not currently possible, but be aware in case of changes
			{
				Relation response = response.select(likeVarPositions[0], likeVarPositions[1]);
			}
			
			//cut out unwanted columns (non-variables in the query)
			response = response.project(allVarPositions);

			//rename columns in response to match query name
			response = response.rename(queryParams);

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

};