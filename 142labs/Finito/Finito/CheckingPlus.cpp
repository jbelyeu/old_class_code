#include "CheckingPlus.h"


CheckingPlus::CheckingPlus(string type, double balance, string name, int vector_size)
			:Checking(type, balance, name, vector_size)
{
	monthlyFee = 0;
}
/*
Checking+:
There is no monthly fee for Checking+ accounts unless a monthly fee is imposed.
The monthly fee of 6 US dollars is imposed only if the current balance drops below 300 US dollars.
*/
void CheckingPlus::advanceMonth()
{
	defBal -= monthlyFee;
}

/*
Checking+:
If the ( current balance - amount) is at least 300 dollars, subtract the amount from the current balance and return true.
If the ( current balance - amount) is below  300 dollars, impose a monthly fee of $6, subtract the amount from the current
balance, and return true.  If there are insuffient funds, apply a $5 fee to the current balance and return
false.
*/
bool CheckingPlus :: writeCheck(double amount)
{
	bool written = true;
	const int penalty = 5;

	if (defBal < amount)
	{
		defBal -= penalty;
		written = false;
		return written;
	}
	if ((defBal - amount) >= 300)
	{
		defBal -= amount;
		return written;
	}
	else if ((defBal - amount) < 300)
	{
		monthlyFee = 6;
		defBal -= amount;
		return written;
	}
}


CheckingPlus::~CheckingPlus(void){}
