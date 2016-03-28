#pragma once
#include "Checking.h"
class CheckingPlus :public Checking
{
public:	CheckingPlus(string type, double balance, string name, int vector_size);
	~CheckingPlus(void);

	void advanceMonth();

	bool writeCheck(double amount);

};
