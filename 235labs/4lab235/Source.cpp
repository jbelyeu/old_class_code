#include<string>
#include<vector>
#include<iostream>
#include<fstream>
#include "Student.h"
#include<algorithm>
#include<list>
#include<set>

using namespace std;

//1 Reads student file data, calls student constructor, stores new students in vector<Student>
void makeStudent(vector<Student>& students, char* argv[])
{
	ifstream inStudents;
	inStudents.open(argv[1]);
	string inID, inName, inAddress, inPhone;
	while (getline(inStudents, inID))
	{
		getline(inStudents, inName);
		getline(inStudents, inAddress);
		getline(inStudents, inPhone);
		Student newStudent = Student(inID, inName, inAddress, inPhone);
		students.push_back(newStudent);
	}	
	inStudents.close();
}

//2 reads query file, stores queries in vector<string>
void readQueries(vector<Student>& queries, char* argv[])
{
	ifstream inQueries;
	inQueries.open(argv[2]);
	string newQuery;
	while(getline(inQueries, newQuery))
	{
		Student queryStudent = Student(newQuery, "", "", "");
		queries.push_back(queryStudent);
	}
}
//3D uses list.sort function
void ListSorter(list<Student>& sortList, ofstream& output)
{
	sortList.sort();
	output << "size: " << sortList.size() <<"    compares: " << sortList.begin()->getCompares() <<endl;
	sortList.begin()->clearCompares();

}
//3C uses std sort function
void StdSort(vector<Student>& sortable, ofstream& output)
{
	
	sort(sortable.begin(), sortable.end());
	output << "size: " << sortable.size() <<"    compares: " << sortable[0].getCompares() <<endl;
	sortable[0].clearCompares();

}
//3B Performs insertion sort operation
void insertion(vector<Student>& sortable, ofstream& output)
{
	for (unsigned i = 1; i < sortable.size(); i++)
	{
		
		Student temp = sortable[i];
		unsigned j = i;
		
		while ((j > 0) && (temp < sortable[j-1]))
		{
			sortable[j] = sortable[j-1];
			j--;
		}
		sortable[j] = temp;
	}

	output << "size: " << sortable.size() <<"    compares: " << sortable[0].getCompares() <<endl;	
	sortable[0].clearCompares();
}

//3A Performs selection sort operation
void selection(vector<Student>& sortable, ofstream& output)
{
	if (sortable.size() < 2) 
	{
		output << "size: " << sortable.size() <<"    compares: 0"  <<endl;
		return;
	}
	

	for (unsigned i = 0; i <= (sortable.size() -2); i++)
	{
		unsigned min = i;
		for (unsigned j = (i+1); j <= (sortable.size()-1); j++)
		{
			if (sortable[j] < sortable[min])
			{
				min = j;
			}
		}
		Student temp = sortable[i];
		sortable[i] = sortable[min];
		sortable[min] = temp;
	}
	output << "size: " << sortable.size() <<"    compares: " << sortable[0].getCompares() <<endl;
	sortable[0].clearCompares();
}
//3E copies students into list for list sort
void makeList(vector<Student>& students, list<Student>& sortList)
{
	sortList.clear();

	for (unsigned i = 0; i < students.size(); i++)
	{
		sortList.push_back(students[i]);
	}

}

//4A chops vector (queries) into 1/4, 1/2
void VecChop(vector<Student>& queries, vector<Student>& quarterQ, vector<Student>& halfQ, vector<Student>& wholeQ)
{
	for(unsigned i = 0; i < ((queries.size()/4)); i++)
	{
		quarterQ.push_back(queries[i]);
	}

	for(unsigned i = 0; i < ((queries.size()/2)); i++)
	{
		halfQ.push_back(queries[i]);
	}
	wholeQ = queries;
}
//4B Self-made linear search
void myLinear(vector<Student>& queries, vector<Student>& searchable, ofstream& output)
{
	searchable[0].clearCompares();
	for(unsigned j = 0; j < queries.size(); j++)
	{
		for( unsigned i = 0; i < searchable.size(); i++)
		{		
			if (searchable[i] == queries[j]){break;}
		}
	}

	output << "size: " << searchable.size() << "    compares: " << Student::compares/searchable.size() <<endl;
	searchable[0].clearCompares();
}
//4C standard find function with vectors
void stdFind(vector<Student>& queries, vector<Student> searchable, ofstream& output)
{
	for (unsigned i = 0; i < queries.size(); i++)
	{
		find(searchable.begin(), searchable.end(), queries[i]);
	}
	output << "size: " << searchable.size() << "    compares: " << Student::compares/searchable.size() <<endl;
	Student::clearCompares();
}
//4D puts things in set
void setMaker(set<Student>& setSearch, vector<Student>& myVector)
{
	for (unsigned i = 0; i < myVector.size(); i++)
	{
		setSearch.insert(myVector[i]);
	}
	Student::clearCompares();
}
//4E std set find
void SetFind(set<Student>& setSearch, vector<Student>& queries, ofstream& output)
{
	
	queries[0].clearCompares();
	for (unsigned i = 0; i < queries.size(); i++)
	{
		setSearch.find(queries[i]);	
	}
	output << "size: " << setSearch.size() << "    compares: " << Student::compares/setSearch.size() <<endl;
	queries[0].clearCompares();
}

//3 copies original vector and calls selection, functions for sorting
void sortCaller(vector<Student>& students, char* argv[])
{
	ofstream output;
	output.open(argv[3]);
	vector<Student> quarterSort, halfSort, wholeSort;
	list<Student> sortList;

	for(unsigned i = 0; i < ((students.size()/4)); i++)
	{
		quarterSort.push_back(students[i]);
	}

	for(unsigned i = 0; i < ((students.size()/2)); i++)
	{
		halfSort.push_back(students[i]);
	}
	
	wholeSort = students;

	output << "Selection Sort" <<endl;
	vector<Student> sortable = quarterSort;

	//select sorts 1/4, 1/2, then 1 vector
	selection(sortable, output);
	sortable = halfSort;
	selection(sortable, output);
	sortable = wholeSort;
	selection(sortable, output);

	output <<"Insertion Sort" <<endl;
	//insert sorts 1/4, 1/2, then 1 vector
	sortable = quarterSort;
	insertion(sortable, output);
	sortable = halfSort;
	insertion(sortable, output);
	sortable = wholeSort;
	insertion(sortable, output);
	output << "std::sort" << endl;
	//Std sorts 1/4, 1/2, then 1 vector
	sortable = quarterSort;
	StdSort(sortable, output);
	sortable = halfSort;
	StdSort(sortable, output);
	sortable = wholeSort;
	StdSort(sortable, output);
	output << "std::list.sort" << endl;
	//list sorts 1/4, 1/2, then 1 list
	makeList(quarterSort, sortList);
	sortable[0].clearCompares();

	ListSorter(sortList, output);
	makeList(halfSort, sortList);
	ListSorter(sortList, output);
	makeList(wholeSort, sortList);
	ListSorter(sortList, output);

	output << "Linear Search" << endl;

	vector<Student> queries, quarterQ, halfQ, wholeQ;
	readQueries(queries, argv);
	VecChop(queries, quarterQ, halfQ, wholeQ);
	//my own linear with 1/4, 1/2, 1
	sortable = quarterSort;
	myLinear(quarterQ, sortable, output);
	sortable = halfSort;
	myLinear(halfQ, sortable, output);
	sortable = wholeSort;
	myLinear(wholeQ, sortable, output);

	output << "std::find" <<endl;
	//std find function
	sortable = quarterSort;
	stdFind(quarterQ, sortable, output);
	sortable = halfSort;
	stdFind(halfQ, sortable, output);
	sortable = wholeSort;
	stdFind(wholeQ, sortable, output);

	//create set, find in it
	output << "std::set.find" <<endl;

	set<Student> setSearch;
	setMaker(setSearch, quarterSort);
	
	//function to run set search
	SetFind(setSearch, quarterQ, output);
	setMaker(setSearch, halfSort);
	SetFind(setSearch, halfQ, output);
	setMaker(setSearch, wholeSort);
	SetFind(setSearch, wholeQ, output);

	output.close();
}


int main( int argc, char* argv[])
{
	vector<Student> students; //creates vector<students>
	makeStudent(students, argv);//reads in student info, creates student objects, packs them into vector
	sortCaller(students, argv); //calls function to sort the students by ID number

	

	
	return 0;
}
