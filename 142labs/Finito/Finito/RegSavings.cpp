#include "RegSavings.h"


RegSavings::RegSavings(string type, double balance, string name, int vector_size)
			:Savings(type, balance, name, vector_size)
{
	interest = (0.01/12);
}


/*
Regular Savings:
Regular Savings has no monthly fee and earns interest of 1% per year. (Regular Savings earns
.01/12 of interest per month)
*/
void RegSavings ::advanceMonth()
{
	defBal += (defBal*interest);
}

/*

Regular Savings:
Withdraw only if the balance remains nonnegative. If it drops below 0 when attempting to withdraw, 
a fee of 5 US dollars is imposed immediately on the account; return false. 
Return true otherwise and deduct the amount from the currentBalance.
*/

bool RegSavings ::withDrawFromSavings(double amount)
{
	const int penalty = 5;
	bool withdrawn = true;
	
	if (defBal < amount)
	{
		defBal -= penalty;
		withdrawn = false;
		return withdrawn;
	}
	else 
	{
		defBal -= amount;
		return withdrawn;
	}
}

RegSavings::~RegSavings(void){}
