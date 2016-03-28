#pragma once

#include <vector>
#include <set>
#include "Tokenizer.h"
#include "Token.h"
#include "Rules.h"
#include "Predicate.h"
#include <fstream>
#include <set>

using namespace std;

class DatalogProgram
{
private:
	Tokenizer tokens;
	vector<predicate> schemes;
	vector<predicate> facts;
	vector<rule> rules;
	vector<predicate> queries;
	set<string> domain;
	vector<param> tempParams;
	vector<predicate> tempPredList;

	string outputFormatter(vector<predicate>& toString, string name)
	{
		stringstream output;

		output <<endl << name << "(" << toString.size() << "):";
		for (unsigned i = 0; i < toString.size(); i++)
		{
			output << "\n  " << toString[i].toString();
		}
		return output.str();
	}

	void startParse(Token toCheck)
	{
		//works recursively until all schemes are parsed
		schemesParse(toCheck);
		toCheck = tokens.getToken();
		
		//works recursively until all facts are parsed
		factsParse(toCheck);
		toCheck = tokens.getToken();		
		
		//works recursively until all rules are parsed
		rulesParse(toCheck);
		toCheck = tokens.getToken();
		
		//works recursively until all queries are parsed
		queriesParse(toCheck);
		toCheck = tokens.getToken();
		
		if (toCheck.getKind() == FEND)
		{
			return;
		}
		
		throw toCheck;
	}

	void schemesParse(Token toCheck)
	{
		if (toCheck.getKind() == SCHEMES)
		{
			toCheck = tokens.getToken();
			if (toCheck.getKind() == COLON) //failing here
			{
				toCheck = tokens.getToken();
				schemeList(toCheck);
			}
			else
			{
				throw toCheck;
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void factsParse(Token toCheck)
	{
		if (toCheck.getKind() == FACTS)
		{
			toCheck = tokens.getToken();
			if (toCheck.getKind() == COLON)
			{
				factList(toCheck);
			}
			else
			{
				throw toCheck;
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void rulesParse(Token toCheck)
	{
		if (toCheck.getKind() == RULES)
		{
			toCheck = tokens.getToken();
			if (toCheck.getKind() == COLON)
			{
				ruleList(toCheck);
			}
			else
			{
				throw toCheck;
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void queriesParse(Token toCheck)
	{
		if (toCheck.getKind() == QUERIES)
		{
			toCheck = tokens.getToken();
			if (toCheck.getKind() == COLON)
			{
				toCheck = tokens.getToken();
				queryList(toCheck);
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void schemeList(Token toCheck)
	{
		if (toCheck.getKind() == ID)
		{
			schemeFun(toCheck);

			//recurse if not done with schemes
			if (tokens.viewNext().getKind() != FACTS) 
			{
				toCheck = tokens.getToken();
				schemeList(toCheck);
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void factList(Token toCheck)
	{
		if (tokens.viewNext().getKind() == ID)
		{
			toCheck = tokens.getToken();
			factFun(toCheck);
			toCheck = tokens.getToken();
			if (toCheck.getKind() == PERIOD)
			{
				//if next is not a rule, then it must be an ID, since epsilon has no token
				if (tokens.viewNext().getKind() != RULES)
				{
					factList(toCheck);
				}
			}
			else
			{
				throw toCheck;
			}
		}
	}

	void ruleList(Token toCheck)
	{
		if (tokens.viewNext().getKind() == ID)
		{
			toCheck = tokens.getToken();
			ruleFun(toCheck);
			//if next is not a query, then it must be an ID, since epsilon has no token
			if (tokens.viewNext().getKind() != QUERIES)
			{
				ruleList(toCheck);
			}
		}

	}

	void queryList(Token toCheck)
	{
		if (toCheck.getKind() == ID)
		{
			queryFun(toCheck);
			if (tokens.viewNext().getKind() != FEND)
			{
				toCheck = tokens.getToken();
				queryList(toCheck);
			} 
		}
		else
		{
			throw toCheck;
		}
	}

	void schemeFun(Token& toCheck)
	{
		string name = toCheck.getLexeme();
		predicate newPred = predicate(name);
		predicateFun(toCheck);
		newPred.addParams(tempParams);
		tempParams.clear();
		schemes.push_back(newPred);
	}

	void factFun(Token& toCheck)
	{
		string name = toCheck.getLexeme();
		predicate newPred = predicate(name);
		predicateFun(toCheck);
		newPred.addParams(tempParams);
		tempParams.clear();
		facts.push_back(newPred);
	}

	void ruleFun(Token toCheck)
	{
		string name = toCheck.getLexeme();
		predicate newPred = predicate(name);
		predicateFun(toCheck);
		newPred.addParams(tempParams);
		tempParams.clear();
		rule newRule = rule(newPred);

		toCheck = tokens.getToken();
		if (toCheck.getKind() == COLON_DASH)
		{
			toCheck = tokens.getToken();

			if (toCheck.getKind() == ID)
			{
				predicateList(toCheck);
				newRule.addBody(tempPredList);
				tempPredList.clear();
				rules.push_back(newRule);

				toCheck = tokens.getToken();
				if (toCheck.getKind() != PERIOD)
				{
					throw toCheck;
				}
			}
			else
			{
				throw toCheck;
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void queryFun(Token& toCheck)
	{
		string name = toCheck.getLexeme();
		predicate newPred = predicate(name);
		predicateFun(toCheck);
		newPred.addParams(tempParams);
		tempParams.clear();
		queries.push_back(newPred);


		toCheck = tokens.getToken();
		if (toCheck.getKind() != Q_MARK)
		{
			throw toCheck;
		}
	}

	//only ever called by the rules function and itself
	void predicateList(Token toCheck)
	{
		string name = toCheck.getLexeme();
		predicate newPred = predicate(name);
		predicateFun(toCheck);
		newPred.addParams(tempParams);
		tempParams.clear();
		tempPredList.push_back(newPred);

		if (tokens.viewNext().getKind() == COMMA)
		{
			toCheck = tokens.getToken();
			toCheck = tokens.getToken();

			if (toCheck.getKind() == ID)
			{
				predicateList(toCheck);
			}
			else
			{
				throw toCheck;
			}
		}
	}

	//assumes that toCheck.kind is ID already
	void predicateFun(Token toCheck)
	{
		toCheck = tokens.getToken();
		if (toCheck.getKind() == LEFT_PAREN)
		{
			toCheck = tokens.getToken();
			paramList(toCheck);
			toCheck = tokens.getToken();
			if (toCheck.getKind() != RIGHT_PAREN)
			{
				throw toCheck;
			}
		}
		else
		{
			throw toCheck;
		}
	}

	void paramList(Token& toCheck)
	{
		//for now, not writing a function for params, since everything I can think of that they would do is done here
		
		if (toCheck.getKind() == STRING || toCheck.getKind() == ID) 
		{
			string name = toCheck.getLexeme();
			param newPar = param(name, (toCheck.getKind() == STRING));
			tempParams.push_back(newPar);

			if (toCheck.getKind() == STRING)
			{
				domain.insert(name);				
			}
			if (tokens.viewNext().getKind() == COMMA)
			{
				toCheck = tokens.getToken(); //gets the comma separating the params
				toCheck = tokens.getToken(); //gets the next param, which should be a string
				paramList(toCheck); //type checked by recursive call
			}
		}
		else
		{
			throw toCheck;
		}
	}

public:
	DatalogProgram(char* argv[])
	{		
		tokens.scan(argv);
	}

	DatalogProgram(){}

	void parse()
	{
		Token toCheck = tokens.getToken();
		startParse(toCheck);
	}

	string toString()
	{
		stringstream output;
		output << "Success!";
		output << outputFormatter(schemes, "Schemes");
		output << outputFormatter(facts, "Facts");

		//formatter doesn't work for rules, wrong type
		output << "\nRules" << "(" << rules.size() << "):";
		for (unsigned i = 0; i < rules.size(); i++)
		{
			output << "\n  " << rules[i].toString();
		}

		output << outputFormatter(queries, "Queries");

		//formatter doesn't work for domain, wrong type
		output << "\nDomain" << "(" << domain.size() << "):";
		for (set<string>::iterator i = domain.begin(); i != domain.end(); i++)
		{
			output << "\n  " << "'" << *i << "'";
		}
		return output.str();
	}

	vector<predicate> getSchemes()
	{
		return schemes;
	}

	vector<predicate> getFacts()
	{
		return facts;
	}

	vector<predicate> getQueries()
	{
		return queries;
	}

	vector<rule> getRules()
	{
		return rules;
	}

	~DatalogProgram(){}
};