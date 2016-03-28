#pragma once
#include "Savings.h"
class RegSavings :	public Savings
{
public:	RegSavings(string type, double balance, string name, int vector_size);
	~RegSavings(void);

	void advanceMonth();

	virtual bool withDrawFromSavings(double amount);
};

