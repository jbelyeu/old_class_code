#pragma once
#include "savings.h"

class CD : public Savings
{
public:	CD(string type, double balance, string name, int vector_size);
	~CD(void);

	void advanceMonth();

	virtual bool withDrawFromSavings(double amount);

	int months;
	
	double initialBal;

	static const int maturity = 9;
};