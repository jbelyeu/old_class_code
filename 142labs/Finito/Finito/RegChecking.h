#pragma once
#include "Checking.h"
class RegChecking :	public Checking
{
public:	RegChecking(string type, double balance, string name, int vector_size);
	~RegChecking(void);

	void advanceMonth();

	bool writeCheck(double amount);
};

