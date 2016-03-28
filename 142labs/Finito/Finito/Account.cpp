#include "Account.h"
#include "Bank.h"
#include "Savings.h"
#include "SavingsPlus.h"
#include "CD.h"
#include "Bank.h"


Account::Account(string type, double balance, string name, int vector_size)
{
	defType = type;
	defBal = balance;
	defName = name;	
	AccountNum = (vector_size +1);
}

int Account::getAccountNumber()
{
	return AccountNum;
}

double Account ::getCurrentBalance()
{
	cout << this->getAccountOwner();
	return defBal;
}
string Account ::getAccountOwner()
{
	return defName;
}

void Account ::advanceMonth()
{}

void Account ::deposit(double amount)
{
	defBal += amount;
}

bool Account ::withdrawFromSavings(double amount)
{
	cout <<"Nope" <<endl;
	return NULL;
}
bool Account ::writeCheck(double amount)
{
	bool written;
	return written;
}



