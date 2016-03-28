#pragma once
#include "Savings.h"
class SavingsPlus :	public Savings
{
public:	SavingsPlus(string type, double balance, string name, int vector_size);
	~SavingsPlus(void);

	void advanceMonth();

	virtual bool withDrawFromSavings(double amount);

};

