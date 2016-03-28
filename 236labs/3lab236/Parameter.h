#pragma once
#include <string>

using namespace std;

class param
{
private:
	string nameOrVal;
	bool hasName;

public:
	param(string& id, bool IDIsVal) :
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

	string getName() //maybe shouldn't use this. Not sure
	{
		return nameOrVal;
	}

	bool isConstant()
	{
		return hasName;
	}

	~param(){}
};