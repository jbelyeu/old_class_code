#include "Student.h"


Student::Student (string InIDNum, string InName, string InAddress, string InPhNumber)
{
	IDNum = InIDNum;
	name = InName;
	address = InAddress;
	phNumber = InPhNumber;
	GPA = 0.00;
}

string Student::toString()
{
	string studentString = name + "\n" + IDNum  + "\n" + phNumber + "\n" + address;
	return studentString;
}

void Student::setGPA(double inGPA)
{
	GPA = inGPA;
}
string Student::getName()
{
	return name;
}

double Student::getGPA()
{
	return GPA;
}

string Student::getIDNum()
{
	return IDNum;
}

Student::~Student(void){}
