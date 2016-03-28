#pragma once
#include<string>
using namespace std;

class tile
{
public:
	tile(string inchars);
	
	void setUsed(bool inUsed);
	
	bool getUsed();

	string getString();

private:
	string chars;
	bool used;
};

