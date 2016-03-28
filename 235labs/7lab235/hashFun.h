#pragma once
#include<string>
using namespace std;

unsigned hashing(const string& item, const int& capacity)
{
	if (capacity == 0) { return 0; }

	unsigned hashIndex = 0;
	for (char c : item)
	{
		hashIndex *= 31;
		hashIndex += c;
	}
	hashIndex = hashIndex % capacity;
	return hashIndex;
}