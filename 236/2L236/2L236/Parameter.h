#pragma once
#include <string>

using namespace std;

class param
{
private:
	string nameOrVal;
	bool hasName;

public:
	param(string id, bool IDIsVal) :
		nameOrVal(id),
		hasName(IDIsVal)
	{};

	string toString()
	{
		if (hasName)
		{
			nameOrVal = "'" + nameOrVal + "'";
		}
		
		return nameOrVal;
	}

	~param(){}
};