#pragma once
#include <string>
#include <vector>
#include "Parameter.h"

using namespace std;

class predicate
{
private:
	string name;
	vector<param> params;

public:
	predicate(string& nameIn)
	{
		name = nameIn;
	}

	void addParams(vector<param>& paramsIn)
	{
		params = paramsIn;
	}

	string toString()
	{
		string toReturn = name + "(";
		for (unsigned i = 0; i < params.size(); i++)
		{
			toReturn += params[i].toString();
			if (i < params.size() - 1)
			{
				toReturn += ",";
			}
		}

		return toReturn + ")";
	}

	string getName()
	{
		return name;
	}

	vector<param> getParams()
	{
		return params;
	}

	~predicate(){}
};