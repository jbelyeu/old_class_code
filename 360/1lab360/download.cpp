//#pragma once
//#include <iostream>
//#include <string>
//#include <sstream>
//#include <string.h>
//#include <netdb.h>
//#include <sys/socket.h>
//#include <sys/types.h>
//#include <netinet/in.h>
#include "client.h"
#include <exception>
using namespace std;

void setFlags(int argc, char* argv[], bool& verbose, bool& repeatRequest, int& count, bool& flagError)
{	
	//if 4 args, no flags present, return
	//if 5 args, it can't be -c because that's a 2-part arg
	//if 6, it can't be -d for the same reason
	//both present if 6 args, either may be first
	switch (argc)
	{	
		case 4:
			return;
		case 5:
			if (strcmp(argv[1], "-d") == 0)
			{
				verbose = true;
				return;
			}
			flagError = true;
			break;
		case 6:
			if (strcmp(argv[1], "-c") == 0)
			{
				repeatRequest = true;
				stringstream ss;
				ss << argv[2];
				ss >> count;
				return;
			}
			flagError = true;
			break;
		case 7:
			repeatRequest = true;
			verbose = true;

			if (strcmp(argv[1], "-c") == 0)
			{
				stringstream ss;
				ss << argv[2];
				ss >> count;
				return;
			}
			else if (strcmp(argv[2], "-c") == 0)
			{
				stringstream ss;
				ss << argv[3];
				ss >> count;
				return;
			} 			
		default:
			flagError = true;
	}
}

bool isNumeric(string port)
{
	for (char c : port)
	{
		if (! isdigit(c))
		{
			return false;
		}	
	}
	return true;
}

int main(int argc, char* argv[] )
{
	bool verbose = false;
	bool repeatRequest = false;
	int count = 0;
	bool flagError = false;

	if (argc >= 4 && argc <= 7)
	{
		setFlags(argc, argv, verbose, repeatRequest, count, flagError);	
	}
	else
	{
		flagError = true;
	}
	
	if (flagError)
	{
		perror("ERROR: INVALID ARGUMENT(s)\nusage: -d -c [count] client [host] [port#] [URL]");
		return 0;
	}

	string port = argv[argc -2];
	if (! isNumeric(port))
	{
		perror("INVALID PORT");
		exit(0);
	}
	int portAsNum = atoi(port.c_str());
	
	Client newClient = Client(verbose, repeatRequest, count, argv[argc -3], portAsNum,
							argv[argc - 1]);
	try
	{
		if (! repeatRequest)
		{
			newClient.openConnection();
			newClient.performGETAction();
			newClient.closeConnection();
		}
		else
		{
			newClient.countSuccessfulCalls();
		}
	}
	catch(exception& e)
	{
		perror("ERROR CONNECTING TO HOST SERVER");
		return 0;
	}
	return 0;
}
