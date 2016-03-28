#pragma once
#include <string>

using std::string;

class Grade
{
public:
	Grade(string InCourse, string InIDNum, string InLtrGrade);
	~Grade(void);

	bool operator < (Grade x) const
	{
		return IDNum <x.IDNum||
			(IDNum == x.IDNum && course < x.course)||
			(IDNum == x.IDNum && course == x.course && LtrGrade < x.LtrGrade);
	}
	string toString();
	string getIDNum();
	string getLtdGrade();

private:
	 string course;
	 string IDNum;
	 string LtrGrade;

};

