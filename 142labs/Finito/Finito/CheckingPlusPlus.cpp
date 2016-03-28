#include "CheckingPlusPlus.h"


CheckingPlusPlus::CheckingPlusPlus(string type, double balance, string name, int vector_size)
				:Checking(type, balance, name, vector_size)
{
	monthlyFee = 0;
	interest = (0.005/12);
}

/*
Checking++:
There is no monthly fee for Checking++ accounts unless a monthly fee is imposed.
The monthly fee of 6 US dollars is imposed only if the current balance drops below 300 US dollars.
Checking++ gains monthly interest of 1/12 of .5% (Checking ++ earns .5% per year; therefore
earns .005/12 per month) only if the current balance is 800 or more. The monthly interest is 
forfeited if the current balance drops below 800 US dollars.
*/
void CheckingPlusPlus::advanceMonth()
{
	defBal -= monthlyFee;
	if (defBal >= 800)
	{
		defBal += (defBal*interest);
	}
}

/*
Checking++:
If the ( current balance - amount) is at least 800 dollars, substract the amount from the current balance
and return true. If the current balance drops below 800, take away the monthly interest,
subtract the amount from the current balance and return true. If it drops below $300, impose a monthly fee 
of $6. If there are insuffienct funds, immediately apply a $5 fee to the current balance.
*/
bool CheckingPlusPlus::writeCheck(double amount)
{
	const int penalty = 5;
	bool written = true;

	if (defBal < amount)
	{
		defBal -= penalty;
		written = false;
		return written;
	}

	if ((defBal - amount) >= 800)
	{
		defBal -= amount;
		return written;
	}
	else if ((defBal - amount) < 300)
	{
		monthlyFee = 6;
		defBal -= amount;
		interest = 0;
		return written;
	}
	else if ((defBal - amount) < 800)
	{
		interest = 0;
		defBal -= amount;
		return written;
	}
}



CheckingPlusPlus::~CheckingPlusPlus(void){}
