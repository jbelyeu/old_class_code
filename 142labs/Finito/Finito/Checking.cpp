#include "Checking.h"


Checking::Checking(string type, double balance, string name, int vector_size)
		:Account(type, balance, name, vector_size)
{
	monthlyFee = 0;
}
bool Checking ::withDrawFromSavings()
{
	bool withdrawn = false;
	return withdrawn;
}


Checking::~Checking(void){}
