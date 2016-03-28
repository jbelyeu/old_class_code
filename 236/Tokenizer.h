#pragma once

#include <string>
#include <vector>
#include "Token.h"
#include <fstream>
#include <map>
#include <set>
#include <iostream>
using namespace std;

class Tokenizer
{
private:
	vector<Token> tokens;
	map<string, tokenType> validTypes;
	set<string> reserved;
	set<string> singleCharTypes;
	set<string> allowedSpaces;
	int position;

	bool tokenizeLine(string newLine, int lineNumber, char* argv[]) //decide which type it is, create token class object, store it in vector
	{
		bool error = false;
		for (unsigned i = 0; i < newLine.size(); i++) //Loop through string to examine characters
		{
			stringstream converter;
			string toTokenize;
			converter << newLine[i];
			converter >> toTokenize;


			if (toTokenize == ":"  && newLine[i + 1] == '-') //potential for out of bounds error here
			{
				i++;
				toTokenize += "-";

				Token newToken = Token(COLON_DASH, toTokenize, lineNumber);
				tokens.push_back(newToken);
			}
			else if (toTokenize == "'")
			{
				i++;
				error = tokenizeString(argv, lineNumber, i, newLine);
			}
			else if (singleCharTypes.find(toTokenize) != singleCharTypes.end())
			{
				Token newToken = Token(validTypes[toTokenize], toTokenize, lineNumber);//needs error-checking
				tokens.push_back(newToken);
			}
			else if (toTokenize[0] == '#')
			{
				break;
			}
			else
			{
				error = helpTokenizeLine(newLine, lineNumber, argv, toTokenize, i);
				if (error)
				{
					return error;
				}
			}
		}
		return error;
	}
	
	//break up tokenizeLine because of cyclomatic complexity
	bool helpTokenizeLine(string newLine, int lineNumber, char* argv[], string toTokenize, unsigned i) 
	{
		bool error = false;
		if (isalpha(newLine[i]))
		{
			error = tokenizeIdentifier(argv, toTokenize, lineNumber, i, newLine);
			toTokenize = "";
			i--;
		}		
		else if (!isspace(toTokenize[0]) && toTokenize.size() > 0)
		{
			this->printError(argv, lineNumber);
			error = true;
		}
	
		return error;
	}

	bool tokenizeString (char* argv[], int lineNumber, unsigned i, string newLine)
	{
		string temp = "'";
		bool endQuote = false;
		unsigned j = i;
		for (; j < newLine.size(); j++)
		{
			if (newLine[j] == temp[0])
			{
				endQuote = true;
				break;
			}
		}
		string toTokenize = newLine.substr(i, (j - i));
		i = j;


		if (!endQuote)
		{
			this->printError(argv, lineNumber);
			return true;
		}
		Token newToken = Token(STRING, toTokenize, lineNumber);
		tokens.push_back(newToken);
		return false;
	}

	bool tokenizeIdentifier(char* argv[], string toTokenize, int lineNumber, unsigned i, string newLine)
	{
		stringstream converter;
		while (isalnum(newLine[i])) //potential for out of range here, too
		{
			converter << newLine[i];
			i++;
		}
		if (i < newLine.size())
		{
			if (!(isspace(newLine[i]) || newLine[i] == '(' || ':')) //if not whitespace, left paren, or colon, then there is an error
			{
				this->printError(argv, lineNumber);
				return true;
			}
		}
		converter >> toTokenize;

		if (reserved.find(toTokenize) == reserved.end()) //if not reserved, then ID
		{
			Token newToken = Token(ID, toTokenize, lineNumber);//needs error-checking
			tokens.push_back(newToken);
		}
		else //reserved
		{
			Token newToken = Token(validTypes[toTokenize], toTokenize, lineNumber);//needs error-checking
			tokens.push_back(newToken);
		}
		return false;
	}

public:
	Tokenizer()
	{
		validTypes[","] = COMMA;
		validTypes["."] = PERIOD;
		validTypes["?"] = Q_MARK;
		validTypes["("] = LEFT_PAREN;
		validTypes[")"] = RIGHT_PAREN;
		validTypes[":"] = COLON;
		validTypes[":-"] = COLON_DASH;
		validTypes["Schemes"] = SCHEMES;
		validTypes["Facts"] = FACTS;
		validTypes["Rules"] = RULES;
		validTypes["Queries"] = QUERIES;
		validTypes["ID"] = ID;
		validTypes["string"] = STRING;

		reserved.insert("Schemes");
		reserved.insert("Facts");
		reserved.insert("Rules");
		reserved.insert("Queries");

		singleCharTypes.insert(",");
		singleCharTypes.insert(".");
		singleCharTypes.insert("?");
		singleCharTypes.insert("(");
		singleCharTypes.insert(")");
		singleCharTypes.insert(":");

		allowedSpaces.insert("");
		allowedSpaces.insert(" ");
		allowedSpaces.insert("\n");
		allowedSpaces.insert("\t");
		allowedSpaces.insert("\r");

		position = 0;
	}

	bool scan(char* argv[])
	{
		ifstream datalog;
		datalog.open(argv[1]);
		int lineNumber = 0;
		bool error = false;

		while (!datalog.eof() && !error)
		{
			lineNumber++;
			string newLine;
			getline(datalog, newLine);
			error = tokenizeLine(newLine, lineNumber, argv);
		}
		if (!error)
		{
			Token newToken = Token(FEND, "EOF", lineNumber);
			tokens.push_back(newToken);
		}
		datalog.close();
		return error;
	}

	void print(char* argv[])
	{
		ofstream output;
		output.open(argv[2]);
		for (unsigned i = 0; i < tokens.size(); i++)
		{
			output << tokens[i].toString() << endl;
		}
		output << "Total Tokens = " << tokens.size();
		output.close();
	}

	void printError(char* argv[], unsigned line)
	{
		ofstream output;
		output.open(argv[2]);
		for (unsigned i = 0; i < tokens.size(); i++)
		{
			output << tokens[i].toString() << endl;
		}

		output <<"Input Error on line " << line;

	
		output.close();
	}

	Token getToken()
	{
		Token nextTok = tokens[position];
		position++;
		return nextTok;
	}

	Token viewNext()
	{
		Token nextTok = tokens[position];
		return nextTok;
	}


	~Tokenizer(){};
};