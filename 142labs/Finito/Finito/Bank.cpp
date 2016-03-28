#include <sstream>
#include "Bank.h"
#include "RegChecking.h"
#include "Checking.h"
#include "CheckingPlus.h"
#include "CheckingPlusPlus.h"
#include "Savings.h"
#include "SavingsPlus.h"
#include "CD.h"
#include "RegSavings.h"



Bank::Bank(void){}

//Bank::~Bank(void){}


/*
needs to be updated and bulletproofed. I don't know what "type is not available means", or the 
minumum values for the accounts.
*/
bool Bank :: openAnAccount(string info)
{
	stringstream myStream = stringstream(info);
	string type;
	double balance;
	string name;
	bool created = true;
	
		for (int i = 0; i <= 3; i++)
	{
		if (i == 0)
		{
			myStream >> type;
			
		}
		if (i == 1)
		{
			myStream >> balance;
			
			if ((type == "Checking"|| type == "Saving") && balance <= 0)
			{
				created = false;
				return created;
			}
			if (type == "Checking+" && balance < 300)
			{ 
				created = false;
				return created;
			}
			if (type == "Checking++" && balance < 800)
			{
				created = false;
				return created;
			}
			if (type == "Saving+" && balance < 1000)
			{
				created = false;
				return created;
			}			
		}

		if (i == 2)
		{
			myStream >> name;
			
		}
	}
	AccountInterface* AccountPointer;
	int vector_size = BankAccounts.size();

	if (type == "Checking")	{AccountPointer = new RegChecking(type, balance, name, vector_size);}
	else if (type == "Checking+") {AccountPointer = new CheckingPlus(type, balance, name, vector_size);}
	else if (type == "Checking++") {AccountPointer = new CheckingPlusPlus (type, balance, name, vector_size);}
	else if (type == "Saving") { AccountPointer = new RegSavings(type, balance, name, vector_size);}
	else if (type == "Saving+") { AccountPointer = new SavingsPlus(type, balance, name, vector_size);}
	else if (type == "CD") { AccountPointer = new CD(type, balance, name, vector_size);}
	else {
		created = false;
		return created;	}
	
	Bank ::BankAccounts.push_back(AccountPointer);

	return created;
}

/*bool Bank ::closeAnAccount(int AccountNumber)
{
	
	bool removed = true;

	AccountInterface* requestedAccount = this->getAnAccount(AccountNumber);
	string name = requestedAccount->getAccountOwner();
	cout <<name;
	double CurrentBal = requestedAccount->getCurrentBalance();
	
	if (CurrentBal >= 0)
	{
		vector<AccountInterface*> to_copy;
		cout << "this time" << endl;
		for (int i= 0; i < BankAccounts.size(); i++)
		{			
			if ( i != AccountNumber)
			{
				
				to_copy.push_back(BankAccounts[i]);
			}
		}
		BankAccounts.clear();
		BankAccounts = to_copy;
	}
	else
	{
		cout << "here it is" << endl;
		removed = false;
	}

	return removed;
}*/


bool Bank ::closeAnAccount(int accountNumber)
{
	
	bool closed;
	AccountInterface* Account = this->getAnAccount(accountNumber);
	if (Account == NULL)
	{
		cout <<"NULL"<<endl;
		closed = false;
		return closed;
	}
	else 
	{
		cout <<"Not NULL" <<endl;
		vector<AccountInterface*> to_copy;
		Account->getCurrentBalance();
		
		for (int i= 0; i < BankAccounts.size(); i++)
		{			
			if ( i != accountNumber)
			{
				
				to_copy.push_back(BankAccounts[i]);
			}
		}
		BankAccounts.clear();
		BankAccounts = to_copy;
		closed = true;
		return closed;	
	}
}

AccountInterface* Bank ::getAnAccount(int accountNumber)
{
	for (int i = 0; i< BankAccounts.size(); i++)
	{
			if ((BankAccounts[i]->getAccountNumber())==accountNumber)
		{
			AccountInterface* requestedAccount = BankAccounts[i];

			return requestedAccount;
		}
	}
	return NULL;
}

void Bank ::advanceMonth()
{
	
	for (int i = 0; i < BankAccounts.size(); i++)
	{
		BankAccounts[i]->advanceMonth();
	}
}

int Bank ::getNumberOfAccounts()
{
	int num_accounts = BankAccounts.size();
	return num_accounts;
}

	