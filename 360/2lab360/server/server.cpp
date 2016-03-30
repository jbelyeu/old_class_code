#include <iostream>
#include <string.h>
#include <ctype.h>
#include <exception>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <cstring>
#include <queue>
#include <vector>
#include <pthread.h>
#include <semaphore.h>
#include <fstream>
#include <sstream>

using namespace std;

const int SOCKET_ERROR   =   -1;
const int BUFFER_SIZE    =   100;
const int MAX_MSG_SIZE   =   1024;
const int QUEUE_SIZE     =   5;
const string MESSAGE     =   "This is the message";

sem_t mutEx;
sem_t spaceOnQ;
sem_t numOfTasks;

queue<int> tasks;

bool suppressContent;
bool printRequest;
bool printResponse;
struct sockaddr_in address;

int threadCount;
string directory;
int port;


struct ThreadInfo
{
	int ID;
};

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
		if (! isdigit(c) )
		{
			return false;
		}
	}
	return true;
}

int processArgs(bool& suppressContent, bool& printRequest, bool& printResponse,
				int& numThreads, string& directory, int argc, char* argv[])
{
	int option;
	bool error = false;

	while ( ( option = getopt( argc, argv, "dUe") ) != -1)
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
			case'?':
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
	numThreads = atoi(argv[optind + 1] );
	directory = argv[optind + 2];
	if (directory[directory.size()] == '/')
	{
		directory = directory.substr(0, directory.size() - 1);
	}
	if (directory[0] == '/')
	{
		directory = directory.substr(1, directory.size());
	}

	//cout << "request directory is: " << directory << endl;

	return atoi(argv[optind]);
}

bool isWhitespace(char c)
{
    switch (c)
    {
        case '\r':
        case '\n':
        case ' ':
        case '\0':
            return true;
        default:
            return false;
    }
}

void strip(char* line)
{
    int len = strlen(line);
    while (isWhitespace(line[len]) )
    {
        line[len --] = '\0';
    }
}

int startConnection()
{
	printf("Starting server\n");
	int socketHandle = socket (AF_INET, SOCK_STREAM, 0);

	if(socketHandle == SOCKET_ERROR)
	{
		perror("ERROR CREATING SOCKET");
		return -1;
		exit(0);
	}

	address.sin_addr.s_addr = INADDR_ANY;
	address.sin_port = htons(port);
	address.sin_family = AF_INET;
	int addressSize = sizeof(address);
	printf("Binding to port %d\n", port);

	if (bind (socketHandle, (struct sockaddr* ) &address, addressSize )
						== SOCKET_ERROR )
	{
		printf("Failed to connect to host\n");
		return -1;
		exit(0);
	}

	getsockname(socketHandle, (struct sockaddr*) &address, (socklen_t*)&addressSize);
//	printf("Socket opened as descriptor %d on port %d for i/o\n", socketHandle, ntohs(address.s
	return socketHandle;
}

void closeConnection(int socketHandle)
{
	if (shutdown(socketHandle, 2) == SOCKET_ERROR)
	{
		perror("FAILED TO CLOSE SOCKET\n");
        exit(0);
	}
    if(close(socketHandle) == SOCKET_ERROR)
    {
        perror("FAILED TO CLOSE SOCKET\n");
        exit(0);
    }
}

//Read the line one char at a time
char* getLine (int fileDescriptor, int& readLength )
{
	char tline[MAX_MSG_SIZE];
	char *line;
	stringstream totalRead;

	int messagesize = 0;
	int amtread = 0;
	while((amtread = read(fileDescriptor, tline + messagesize, 1)) < MAX_MSG_SIZE)
	{
		if (amtread > 0)
		{
			messagesize += amtread;
		}
		else
		{
			perror("Socket Error is:");
		//	fprintf(stderr, "Read Failed on file descriptor %d messagesize = %d\n", fileDescrip
			break;
			exit(2);
		}

		if (tline[messagesize - 1] == '\n')
		{
			break;
		}

	}
	tline[messagesize] = '\0';
	strip(tline); //strips whitespace

	line = (char *)malloc((strlen(tline) + 1) * sizeof(char));
	strcpy(line, tline);
	readLength = messagesize;
	return line;
}

void getHeaderLines(vector<char *> &headerLines, bool envformat, int descriptor)
{
	// Read the headers, look for specific ones that may change our responseCode
	char *line;
	char *topLine;

	int readLength = 0; //keeps track of the number of chars read, but here it doesn't matter
	topLine = getLine(descriptor, readLength);

	while(strlen(topLine) != 0)
	{
		if (strstr(topLine, "Content-Length") || strstr(topLine, "Content-Type"))
		{
			line = strdup(topLine);
		}
		else
		{
			line = (char *)malloc((strlen(topLine) + 10) * sizeof(char));
			sprintf(line, "%s", topLine);
		}
		headerLines.push_back(line);
		free(topLine);
		topLine = getLine(descriptor, readLength);
	}
	free(topLine);
}

string parseHeaders(vector<char* > headers)
{
    string requestedData = "";
    const char* GET = "GET";
    bool grabRequest = false;
    char space = ' ';

    for (char* header : headers)
    {
        if (strstr(header, GET))
        {
			for (unsigned i = 0; i < strlen(header); ++i)
			{
            	if (!grabRequest && header[i] == space)
                {
                    grabRequest = true;
                }
                else if (grabRequest)
                {
                    if (header[i] == space)
                    {
                        grabRequest = false;
                        break;
                    }
                    requestedData += header[i];
                }
            }
            break;
        }
    }

	if (requestedData[0] == '/')
	{
//		requestedData = requestedData.substr(1, requestedData.size());
	}
    return requestedData;
}

string getFileType(string URI)
{
	string type = "";
	if (URI.find(".html") != string::npos)
	{
		type = "text/html";
	}
	else if (URI.find(".txt") != string::npos)
	{
		type = "text/plain";
	}
	else if (URI.find(".jpg") != string::npos)
	{
		type = "image/jpg";
	}
	else if (URI.find(".gif") != string::npos)
	{
		type = "image/gif";
	}

	return type;
}

//just builds and returns the headers
string buildHeaders(string responseCode, int size, string filetype)
{
	stringstream header;
	header << "HTTP/1.1 " << responseCode << "\r\n";
	header << "Content-Length: " << size << "\r\n";
	header << "Content-Type: " << filetype << "\r\n\r\n";
	return header.str();
}

string buildDirectoryIndex(string dir) 
{
	string dirname = dir;
	if (strstr(dirname.c_str(), directory.c_str() ) != NULL  )
	{
//		dirname = dirname.substr(directory.size());
	}
	//cout << "directory: " <<dir <<endl;
	vector<string> names;
	char buffer[1024];
	FILE* stream;
	int rval;
	string command = "ls " + dir;
	stream = popen (command.c_str(), "r");
	while ( (rval = fread(buffer, 1, 1024, stream)) > 0) 
	{}

	string dirEntry = "";
	for (char c : buffer)
	{
		if (c != '\n')
		{
			dirEntry.push_back(c);
		}
		else
		{
			names.push_back(dirEntry);
			dirEntry = "";
		}	
	}
	pclose (stream);

	string html = "<!DOCTYPE html>\n<html>\n\t<body>\n";
	for (unsigned i = 0; i < names.size(); i++)
	{
//		html += "\t\t<a href=\"" + names[i] + "\">" + names[i] + "</a><br />\n";

		html += "\t\t<a href=\"" + dirname + "/" + names[i] + "\">" + names[i] + "</a><br />\n";
	}
	html += "\t</body>\n</head>\n";
	//cout << "Directory index:\n " << html << endl;
	//cout <<"Directory size: " << names.size();
	return html;
}


void sendResponse(string header, string filename, bool isDir, int socketDescriptor)
{
//	cout << "headers going to socket " << socketDescriptor << endl;
//	char portBuffer[256];
//	strcpy(portBuffer, header.c_str());
//	cout << portBuffer;
//	write(socketDescriptor, &portBuffer, strlen(portBuffer)); //write the headers
//	
//	cout << "Entering send function\n";
//
//	if (! isDir)
//	{
//		cout << "Sending directory response" << endl;
//
//		strcpy(portBuffer, buildDirectoryIndex(filename).c_str() );
//		write(socketDescriptor, &portBuffer, strlen(portBuffer) );
//	}
//	else
//	{
//
//		cout << "Sending file response" << endl;
//		unsigned file = open(filename.c_str(), O_RDONLY, S_IREAD);
//	 	int bufferLen = 1;
//
//		while (bufferLen > 0)
//		{
//			bufferLen = read(file, portBuffer, 1024);
//			if (bufferLen > 0)
//			{
//				cout << portBuffer;
//				write(socketDescriptor, &portBuffer, strlen(portBuffer )); //write the body piece by piece
//			}
//		}
//	}
//	close(socketDescriptor);
//	cout << "Done sending file response" << endl;

}

void returnRequestedData(string URI, int socketDescriptor, bool imageFile)
{
	char portBuffer[1024];
//	cout << "return requested data to socket " << socketDescriptor << endl;
	string header;
	bool isDir = false;
//	cout << URI << endl;
	struct stat fileStats;
	if (stat (URI.c_str(), &fileStats) )
	{
//		cout << "Error in requested data \n" << endl;
		URI = "/notFoundError.html";
		struct stat errorFile;
		stat (URI.c_str(), &errorFile);
		int size = errorFile.st_size;
		header = buildHeaders("404 Not Found", size, "text/html");
	}
	else if (S_ISREG(fileStats.st_mode) )
	{
//		cout << "Requested data is file" << endl;
		string type = getFileType(URI);
		int size = fileStats.st_size;
		header = buildHeaders("200 OK", size, type);	
	}
	else if (S_ISDIR(fileStats.st_mode) ) 
	{
//		cout << "Requested data is directory" << endl;

		isDir = true;

//		cout << "Sending directory response: " << URI <<  endl;
	
		struct stat dirstat;
		string indexFile = URI + "index.html";
		if (stat (indexFile.c_str(), &dirstat) )
		{
			string directoryHTML = buildDirectoryIndex(URI);
			header = buildHeaders("200 OK", directoryHTML.size(), "text/html");
//			cout << "\ndirectory header" << endl;
//			cout << header << endl;
		
			strcpy(portBuffer, header.c_str());
//			cout << portBuffer;
		
			write(socketDescriptor, &portBuffer, strlen(portBuffer) ); //write the headers
			strcpy(portBuffer, directoryHTML.c_str());
			write(socketDescriptor, &portBuffer, strlen(portBuffer) );
		}
		else
		{
			unsigned file = open(URI.c_str(), ios::binary);
	 		int bufferLen = 1;

			while (bufferLen > 0)
			{
				bufferLen = read(file, portBuffer, 1024);
				if (bufferLen > 0)
				{
//					cout << "\t" << portBuffer;
//					send(socket,(const void *) &portBuffer, bufferLen, 0);
					write(socketDescriptor, &portBuffer, bufferLen); //write the body piece by piece
				}
			}
		
		}
		close(socketDescriptor);
		return; 
	}
	else
	{
		perror ("Error in requested data\n");
//		exit(0);
	}
//	cout << "About to send response to socket " << socketDescriptor << endl;
	
//	cout << "headers going to socket " << socketDescriptor << endl;
	strcpy(portBuffer, header.c_str());
//	cout << portBuffer;
	write(socketDescriptor, &portBuffer, strlen(portBuffer)); //write the headers
	
//	cout << "Entering send function\n";

	if (isDir)
	{
	}
	else
	{

//		cout << "Sending file response" << endl;
		unsigned file = open(URI.c_str(), ios::binary);
	 	int bufferLen = 1;

		while (bufferLen > 0)
		{
			bufferLen = read(file, portBuffer, 1024);
			if (bufferLen > 0)
			{
//				cout << "\t" << portBuffer;
//				send(socket,(const void *) &portBuffer, bufferLen, 0);
				write(socketDescriptor, &portBuffer, bufferLen); //write the body piece by piece
			}
		}
	}
	close(socketDescriptor);
//	cout << "Done sending file response" << endl;

}

void processRequest(int socketDescriptor)
{
//	cout << "Processing request on socket " <<  socketDescriptor << endl;
	vector<char*> headers;
	//reads in the header data
	getHeaderLines(headers, false, socketDescriptor);

//  take the read-in data and parse headers for URI, use sample code to read them
	string requestedData = parseHeaders(headers);
//	cout << "requestData: " << requestedData << endl;
	if (requestedData[0] == '/')
	{
		requestedData = requestedData.substr(1, requestedData.size());
	}
	string requestDir = requestedData.substr(0, directory.size());
	if (directory != requestDir) 
	{
//		cout << "No, it's here" << endl;
//		cout << directory << endl;
//		cout << requestDir <<endl;
		requestedData = directory + "/" + requestedData;
	}
	if (requestedData.size() == 0)
	requestedData = directory;
//	cout << "Client has requested " << requestedData << endl;
	returnRequestedData(requestedData, socketDescriptor, true );
}

void serve()
{   
    int socketHandle = startConnection();
	int addressSize = sizeof(address);

	if (listen (socketHandle, QUEUE_SIZE) == SOCKET_ERROR)
    {
        printf("Listen failed\n");
        exit(0);
    }
    
	for (;;)
    {
		printf("Listening for connection\n");

		int newSocketHandle = accept(socketHandle, (struct sockaddr* ) &address, (socklen_t* ) &addressSize);

		if (newSocketHandle == SOCKET_ERROR)
		{
			perror("ERROR: could not accept connections");
			exit(0);
		}
        else
        {
            cout << "ADDING NEW CONNECTION" << endl;
            //add a new task to the queue
			sem_wait(&spaceOnQ);

            sem_wait(&mutEx);
            tasks.push(newSocketHandle);
            sem_post(&mutEx);
			sem_post(&numOfTasks);
            cout << "CONNECTION ADDED\n";
        }
    }
	shutdown(socketHandle, 2);
	close(socketHandle);
}

void *serveData(void* dataPointer)
{
    int socketDescriptor = -1;

    while ( 1 )
    {
 		sem_wait(&numOfTasks);//wait until there are tasks
 		sem_wait(&mutEx); //wait for the queue to be available

        if (tasks.size() > 0)
        {
            socketDescriptor = tasks.front();
            tasks.pop();
        }
        sem_post(&mutEx); //release the queue
		sem_post(&spaceOnQ);

     	if(socketDescriptor > -1)
		{
			cout << "Starting task: " << socketDescriptor << endl;
			processRequest(socketDescriptor);
		}
    }
	return dataPointer;
}

void createThreadPool()
{
	//initialize semaphores
	sem_init(&mutEx, 0, 1);
	sem_init(&spaceOnQ, 0, 100);
	sem_init(&numOfTasks, 0, 0);

	struct ThreadInfo info;
	pthread_t threads[threadCount];

	for (int i = 0; i < threadCount; ++i)
	{
		info.ID = i;

		sem_wait(&mutEx);

		cout << "Creating threads" << endl;

	//    pthread_create(&threads[i], NULL, serveData, );     
		pthread_create(&threads[i], NULL, &serveData, (void*) &info);
		sem_post(&mutEx);
	}
}

int main(int argc, char* argv[])
{
	if (argc > 7 || argc < 4)
	{
		argumentError();
	}

	suppressContent = false, printRequest = false, printResponse = false;
	threadCount = -1;
	directory = "";
	port = -1;

	try
	{
		port = processArgs(suppressContent, printRequest, printResponse, threadCount, directory, argc, argv);
	}
	catch (exception e)
	{
		argumentError();
	}

	createThreadPool();
	serve();

	return 0;
}
