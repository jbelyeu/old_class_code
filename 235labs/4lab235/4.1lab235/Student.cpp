#include "Student.h"

int Student::compares;

Student::Student(string inID, string inName, string inAddress, string inPhone)
{
	ID = inID;
	name = inName;
	address = inAddress;
	phone = inPhone;
}

string Student::getID()
{	
	return ID;
}

int Student::getCompares()
{
	return compares;
}

void Student::clearCompares()
{
	compares = 0;
}


Student::~Student(){}
