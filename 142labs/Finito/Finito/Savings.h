#pragma once
#include "Account.h"
class Savings : public Account
{
public:	Savings(string type, double balance, string name, int vector_size);
	~Savings(void);

virtual bool withDrawFromSavings(double amount);

bool writeCheck(double amount);

double interest;

};

