#include "CD.h"


CD::CD(string type, double balance, string name, int vector_size)
	:Savings(type, balance, name, vector_size)
{
	initialBal = balance;
	interest = (0.02/12);
	months = 0;
}

/*
CD:
CD's earn interest of 2% per year. If part or all of the CD account is withdrawn from before maturity
(9 months), then any interest since the withdrawal date is forfeited.
*/

void CD::advanceMonth()
{
	defBal += (defBal*interest);
	months++;
}

bool CD ::withDrawFromSavings(double amount)
{
	bool withdrawn = true;

	if (amount > defBal)//not sure if this should have some type of penalty or not
	{
		withdrawn = false;
		return false;
	}

	if (months < maturity)//same as above. No penalty is mentioned, though
	{
		defBal = initialBal;
		if (defBal < amount)
		{
			withdrawn = false;
			return withdrawn;
		}
		defBal -= amount;
		return withdrawn;
	}
	else 
	{
		defBal -= amount;
		return withdrawn;
	}		
}

CD::~CD(void){}
