#pragma once
#include "Account.h"
class Checking : public Account
{
	public:	Checking(string type, double balance, string name, int vector_size);
		~Checking(void);

	virtual bool withDrawFromSavings();

	int monthlyFee;
};

