#include<iostream>
#include<fstream>
#include<string>
#include "AVL.h"

using namespace std;

//1 reads in from file. Will need to call other functions depending on the commands listed.
void readCommand(char* argv[])
{
	AVL<string> Set;
	ifstream inFile;
	inFile.open(argv[1]);
	string command;
	string item;
	ofstream outFile;
	outFile.open(argv[2]);

	while (inFile >> command)
	{
		if (command == "add")
		{
			inFile >> item;
			Set.add(item);
			outFile << "add "<< item << endl;
			//call insert command function
		}
		else if (command == "remove")
		{
			inFile >> item;
			Set.remove(item);
			outFile << command << " " << item << endl;
			//call remove function
		}
		else if (command == "print")
		{
			outFile << "print" << endl;
			Set.print(outFile);
			//call print function
		}
		else if (command == "find")
		{
			inFile >> item;
			bool found = Set.find(item);
			outFile << command << " " << item << " " << found << endl; //will need to change the output bool to a string
			//call find function
		}
		else if (command == "clear")
		{
			//call clear function
			Set.clear();
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