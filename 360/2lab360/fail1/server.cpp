#include "serverObj.h"
using namespace std;

void argumentError()
{
	cout << "USAGE: server -d -U -e port#" << endl;
	cout << "-d => suppress content printing\n";
	cout << "-U => print request headers\n";
	cout << "-e => print response headers\n";
	cout << "port => #integer\n";
	cout << "number of threads => #integer\n";
	cout << "directory => string\n";
	exit(0);
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

int processArgs(bool& suppressContent, bool& printRequest, bool& printResponse,
				int& numThreads, string& directory, int argc, char* argv[])
{
	//assuming these are the right flags
	int option;
	bool error = false;

	while( (  option = getopt( argc, argv, "dUe") ) != -1 )
	{
		switch (option)
		{
			case 'd':
				suppressContent = true;
				break;
			case 'U':
				printRequest = true;
				break;
			case 'e':
				printResponse = true;
				break;
			case '?':
				error = 1;	
				break;				
		}
	}

	if (error)
	{
		argumentError();
	}
	else if (! isNumeric(argv[optind]) || ! isNumeric(argv[optind +1]))
	{
		cout << "ERROR IN ARGUMENTS: " << argv[optind] << " IS INVALID PORT NUMBER\n";
		argumentError();	
	}
	numThreads = atoi( argv[optind +1] );
	directory = argv[optind +2];
	return  atoi( argv[optind] );
}

int main(int argc, char* argv[])
{
	if (argc > 7 || argc < 4)
	{
		argumentError();
	}
	bool suppressContent = false, printRequest = false, printResponse = false;
	int numThreads = -1;
	string directory;
	int port;
	try 
	{
		port = processArgs(suppressContent, printRequest, printResponse, numThreads, directory, argc, argv);
	}
	catch (exception e)
	{
		argumentError();
	}
	Server server = Server(port, suppressContent, printRequest, printResponse, numThreads, directory);
	server.serve();
	return 0;
}
