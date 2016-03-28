#pragma once
#include <string>

using std:: string;

class Student
{
public:
	Student(string InIDNum, string InName, string InAddress, string InPhNumber);
	~Student(void);

	string toString();

	bool operator < (Student s) const
	{
		return IDNum <s.IDNum;
	}

	string getIDNum();
	
	void setGPA(double inGPA);

	double getGPA();
	 
	string getName();

private:
	string IDNum;
	string name;
	string address;
	string phNumber;
	double GPA;


};

