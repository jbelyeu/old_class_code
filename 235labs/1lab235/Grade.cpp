#include "Grade.h"


Grade::Grade (string InCourse, string InIDNum, string InLtrGrade)
{
	course = InCourse;
	IDNum = InIDNum;
	LtrGrade = InLtrGrade;
}

string Grade::toString()
{
	string gradeString = IDNum + "    " + LtrGrade + "    " + course + "\n";
	return gradeString;
}
string Grade::getIDNum()
{
	return IDNum;
}

string Grade::getLtdGrade()
{
	return LtrGrade;
}

Grade::~Grade(void){}
