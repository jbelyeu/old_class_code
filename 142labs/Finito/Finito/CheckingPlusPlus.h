#pragma once
#include "Checking.h"
class CheckingPlusPlus :public Checking
{
public:	CheckingPlusPlus(string type, double balance, string name, int vector_size);
	~CheckingPlusPlus(void);	

	void advanceMonth();

	bool writeCheck(double amount);

	double interest;
};

