#include "Savings.h"


Savings::Savings(string type, double balance, string name, int vector_size)
		:Account(type, balance, name, vector_size)
{
	interest = 0;
}


bool Savings::withDrawFromSavings(double amount)
{
	cout <<"here in Savings?" << endl;
	bool withDrawn = false;
	return withDrawn;
}

bool Savings::writeCheck(double amount)
{
	bool written = false;
	return written;
}

Savings::~Savings(void){}
