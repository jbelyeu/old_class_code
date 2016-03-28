#pragma once
#include <string>
#include <vector>
#include "Scheme.h"
using namespace std;

class Tuple: public vector<string>
{
public:
	Tuple() : vector<string>(){}

	bool canJoin(Tuple& joinable, vector<pair<unsigned, unsigned>> indeces)
	{
		bool can = true;
		for (unsigned i = 0; i < indeces.size(); i++)
		{
			unsigned leftIndex = indeces[i].first;
			unsigned rightIndex = indeces[i].second;

			if ((*this)[leftIndex] != joinable[rightIndex]) //this looks sketchy...Forcing precedence of dereference operator
			{
				can = false;
			}
		}
		return can;
	}

	//simplified, but still risky
	Tuple Join(Tuple toJoin, vector<pair<unsigned, unsigned>>& indeces)
	{
		
		for (unsigned i = 0; i < indeces.size(); i++)
		{
			toJoin[indeces[i].second] = DELETED;
		}
		for (unsigned i = 0; i < toJoin.size(); i++)
		{
			if (toJoin[i] != DELETED)
			{
				this->push_back(toJoin[i]);
			}
		}
		return *this;
	}

	~Tuple() {}

private:
	const string DELETED = "*****DELETED AND FLAGGED*****";

	
};