#pragma once
#include <string>
#include <vector>
using namespace std;

class Tuple: public vector<string>
{
public:
	Tuple() : vector<string>()
	{}

	~Tuple() {}
};