#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "DatalogProgram.h"
#include <string>
#include <iostream>


using namespace std;

int main(int argc, char* argv[])
{
	//parser constructor automatically scans file and reads in tokens
	//needs to create a datalog object, read data in from a file and tokenize it (with datalog's scanner),
	//check the syntax, then check the grammar

	DatalogProgram parser = DatalogProgram(argv); 
	try
	{
		parser.parse();
		
		ofstream out;
		out.open(argv[2]);

		out << parser.toString();
	}
	catch (Token ex)
	{
		ofstream out;
		out.open(argv[2]);
		out << "Failure!\n  " << ex.toString();
	}
	_CrtDumpMemoryLeaks();
	return 0;
}