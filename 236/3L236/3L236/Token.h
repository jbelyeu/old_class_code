#pragma once

#include <string>
#include <sstream>
#include <map>
using namespace std;

enum tokenType
{
	COMMA, PERIOD, Q_MARK, LEFT_PAREN, RIGHT_PAREN, COLON, COLON_DASH, SCHEMES,
	FACTS, RULES, QUERIES, ID, STRING, FEND
};

class Token
{

public:
	Token(tokenType type, string value, int line)
	{
		lexeme = (value);
		lineNum = (line);
		kind = (type);

		typeNames[COMMA] = "COMMA";
		typeNames[PERIOD] = "PERIOD";
		typeNames[Q_MARK] = "Q_MARK";
		typeNames[LEFT_PAREN] = "LEFT_PAREN";
		typeNames[RIGHT_PAREN] = "RIGHT_PAREN";
		typeNames[COLON] = "COLON";
		typeNames[COLON_DASH] = "COLON_DASH";
		typeNames[SCHEMES] = "SCHEMES";
		typeNames[FACTS] = "FACTS";
		typeNames[RULES] = "RULES";
		typeNames[QUERIES] = "QUERIES";
		typeNames[ID] = "ID";
		typeNames[STRING] = "STRING";
	}

	~Token(){};

	tokenType getKind()
	{
		return kind;
	}

	string getLexeme()
	{
		return lexeme;
	}

	int getLineNum()
	{
		return lineNum;
	}

	string toString()
	{
		stringstream ss;
		ss << "(" << typeNames[kind] << ",\"" << lexeme << "\"," << lineNum << ")";
		return ss.str();
	}

private:

	tokenType kind;
	string lexeme;
	int lineNum;
	map<tokenType, string> typeNames;
};