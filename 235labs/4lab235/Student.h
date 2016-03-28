#pragma once
#include <string>
using namespace std;

class Student
{
public:
	
	static int compares;
	Student(string inID, string inName, string inAddress, string inPhone);

	bool operator < (Student x) const
	{
		compares++;
		return ID < x.getID();
	}
	bool operator > (Student x) const
	{
		compares++;
		return ID > x.getID();
	}
	bool operator == (Student x) const //Check to see if screwed up the sort functions
	{
		compares++;
		return ID == x.getID();
	}

	static int getCompares();

	static void clearCompares();
	
	string getID();


	~Student();
private:
	string ID;
	string name;
	string address;
	string phone;

	
};
