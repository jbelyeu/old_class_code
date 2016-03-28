#include<iostream>
#include<fstream>
#include<string>
#include"LinkList.h"

using namespace std;

//1 reads in from file. Will need to call other functions depending on the commands listed.
void readCommand(char* argv[])
{
	LinkList<string> list;
	ifstream inFile;
	inFile.open(argv[1]);
	string command;
	int index = 0;
	string item;
	ofstream outFile;
	outFile.open(argv[2]);

	while (inFile >> command )
	{
		if (command == "insert")
		{
			inFile >> index >> item;
			list.insert(index, item);
			outFile << "insert " << index << " " << item <<endl;
			//call insert command function
		}
		else if (command == "remove")
		{
			inFile >> index;
			item = list.remove(index);
			outFile << command << " " << index << " " << item <<endl;
			//call remove function
		}
		else if (command == "print")
		{
			outFile << "print" << endl;
			list.print(outFile);			
			//call print function
		}
		else if (command == "find")
		{
			inFile >> item;
			index = list.find(item);
			outFile << command << " " << item << " " << index <<endl;
			//call find function
		}
		else if (command == "clear")
		{
			//call clear function
			list.clear();
			outFile << command << endl;
		}
		else
		{
			cout << "Error: Unknown command" << endl;
		}
	}
	outFile.close();
	inFile.close();
}


int main(int argc, char* argv[])
{
	//call function to read in from file
	readCommand(argv);
	return 0;
}