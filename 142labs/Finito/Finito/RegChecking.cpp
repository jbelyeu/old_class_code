#include "RegChecking.h"


RegChecking::RegChecking(string type, double balance, string name, int vector_size)
			:Checking(type, balance, name, vector_size)
{
	monthlyFee = 6;
}

//	A regular checking accounts has a monthly fee of 6 US dollars which is deducted  automatically from
//  the balance
void RegChecking::advanceMonth()
{
	defBal -= monthlyFee;
}

/*
Regular Checking:
If ( current balance - amount ) is below zero, apply a $5 penalty fee to the current balance and return 
false. Otherwise subtract the amount from the current balance and return true.
*/
bool RegChecking ::writeCheck(double amount)
{
	const int penalty = 5;
	bool written = true;

	if ((defBal - amount)<0)
	{
		defBal -= penalty;
		written = false;
		return written;
	}
	else 
	{
		defBal -= amount;
		return written;
	}
}




RegChecking::~RegChecking(void){}
