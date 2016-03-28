#include "SavingsPlus.h"


SavingsPlus::SavingsPlus(string type, double balance, string name, int vector_size)
			:Savings(type, balance, name, vector_size)
{
	interest = (0.0125/12);
}

/*
Savings+:
Earns interest of 1.25% per year. If the balance drops below 1000 US dollars, the 
interest drops to 1% per year. 
*/

void SavingsPlus ::advanceMonth()
{
	if (defBal >= 1000)
	{
		defBal += (defBal*interest);
	}
	else if (defBal < 1000)
	{
		interest = (0.01/12);
		defBal += (defBal*interest);
	}
}
/*
Savings+:
Return true if the (current - amount) is at least 1000 US dollars. Return true, but drop interest
from .125% to .1% if the balance drops below 1000 dollars. Return false if the (current balance - amount)
drops below 0 and apply a $5 fee to the current balance. 
*/
bool SavingsPlus::withDrawFromSavings(double amount)
{
	bool withdrawn = true;
	const int penalty = 5;
	if (defBal < amount)
	{
		cout <<endl << "Insufficient funds" <<endl;
		defBal -= penalty;
		withdrawn = false;
		return withdrawn;
	}

	if ((defBal - amount) >= 1000)
	{
		cout <<endl << "All is well." << endl;
		defBal -= amount;
		return withdrawn;
	}
	else if ((defBal - amount) < 1000)
	{
		cout <<endl << "Dropped below min, will be punished." << endl;
		defBal -= amount;
		interest = (0.01/12);
		return withdrawn;
	}
}


SavingsPlus::~SavingsPlus(void){}
