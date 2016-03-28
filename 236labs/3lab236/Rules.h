#pragma once
#include <string>
#include "Predicate.h"
#include <vector>

using namespace std;

class rule
{
private:
	predicate head;
	vector<predicate> body;

public:
	rule(predicate& headIn) :
		head(headIn)
	{};

	void addBody(vector<predicate>& bodyIn)
	{
		body = bodyIn;
	}

	string toString()
	{
		string toReturn =  head.toString() + " :- ";
		for (unsigned i = 0; i < body.size(); i++)
		{
			toReturn += body[i].toString();
			if (i < body.size() - 1)
			{
				toReturn += ",";
			}
		}
		return toReturn;
	}

	~rule(){}
};