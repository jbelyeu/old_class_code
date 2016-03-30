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
#include <sys/wait.h>
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

string cToString(char* c_str)
{
	string goodStr = "";
	for (unsigned i = 0; i < strlen(c_str); ++i)
	{	
		goodStr += c_str[i];
	}
	return goodStr;
}

char* stringToC(string s)
{
	if (s.size() <= 0 )
	{
		return '\0';
	}	
	unsigned i = 0;

	char* c = (char*) malloc(sizeof('s') * s.size() + 1);
	for (i = 0; i < s.size(); ++i)
	{
		c[i] = s[i];
	}
	c[i] = '\0';
	return c;
}

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
	const char* POST = "POST";

    bool grabRequest = false;
    char space = ' ';

    for (char* header : headers)
    {
		if (strstr(header, GET) || strstr(header, POST) )
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
	else if (URI.find(".cgi") != string::npos ||
			 URI.find(".pl") != string::npos) 
	{
		//cover case of CGI
		type = "text/htmlcgi";
	}
	return type;
}

//just builds and returns the headers
string buildHeaders(string responseCode, int size, string filetype)
{
	stringstream header;
	header << "HTTP/1.0 " << responseCode << "\r\n";
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
		html += "\t\t<a href=\"" + dirname + "/" + names[i] + "\">" + names[i] + "</a><br />\n";
	}
	html += "\t</body>\n</head>\n";
	//cout << "Directory index:\n " << html << endl;
	//cout <<"Directory size: " << names.size();
	return html;
}

// Change to upper case and replace with underlines for CGI scripts
string cgiFormatter(string str)
{
	
	int i;
	string s = str;
	
	for (i = 0; s[i] != ':'; i++)
	{
		if (s[i] >= 'a' && s[i] <= 'z')
		{
			s[i] = 'A' + (s[i] - 'a');
		}

		if (s[i] == '-')
		{
			s[i] = '_';
		}
	}

	string tempStr = "";
	for (unsigned j = 0; j < str.size(); ++j)
	{
		if (s[j] == ':')
		{
			tempStr += '=';
		}
		else if (! isWhitespace(s[j]))
		{
			tempStr += s[j];
		} 
	}

	//strcpy(str, tempStr.c_str());
	return tempStr;
}

// When calling CGI scripts, you will have to convert header strings
// before inserting them into the environment.  This routine does most
// of the conversion
string formatHeader(string str, string prefix)
{
	str = cgiFormatter(str);
	string retVal = prefix + str;
	return retVal;
}

string getRequestString (string URL)
{
	if (strstr(URL.c_str(), "?"))
	{
		char* token = strtok(stringToC(URL), "?");
		token = strtok(NULL, "?");
		return cToString(token);
	} 
	return "";	
}

char* arrayify(vector<char>& response, string header)
{
	char* arrayC = (char*)malloc(sizeof('s') * (response.size() + header.size()));
	unsigned i = 0;
	for (; i < header.size(); ++i)
	{
		arrayC[i] = header[i];
	}
	for (unsigned j = 0; j< response.size(); ++j)
	{
		arrayC[i] = response[j];
		i++;
	}
	return arrayC;
}

void childProcess(int pipefdParentToChild[], int pipefdChildToParent[], char* envVarArray[], string URI)
{
	close(pipefdParentToChild[1]);
	close(pipefdChildToParent[0]); //close the side of the pipes that we aren't using
	dup2(pipefdParentToChild[0], STDIN_FILENO); //replace std:in with input part of pipe
	dup2(pipefdChildToParent[1], STDOUT_FILENO); 

	char* argList[2];
	argList[0] = stringToC(URI);
	argList[1] = NULL;

	execve(URI.c_str(), argList, envVarArray);
}

void parentProcess(int socketDescriptor, int pipefdParentToChild[], int pipefdChildToParent[], bool post, int contentLength)
{
	close(pipefdParentToChild[0]);
	close(pipefdChildToParent[1]);

	const int MAX_BUFF_LEN = 1024;
	int bufferLen = MAX_BUFF_LEN; //starts at max length to enter while loop
	char portBuffer[MAX_BUFF_LEN];

	if (post)
	{	
		int amountRead = 0;
		int amt = 0;

		perror("here");
		//read the body of the request and write it to the child.
		while ( (amountRead < contentLength) && (amt = read(socketDescriptor, &portBuffer, MAX_BUFF_LEN )))
		{
			amountRead += amt;
			stringstream ss;
			write (pipefdParentToChild[1], &portBuffer, amt);
			for (char c : portBuffer)
			{	
				ss << c;
			}
			perror(ss.str().c_str());
		}
		perror("ereh");
		bufferLen = MAX_BUFF_LEN;
	}
	int status;
	wait(&status);

	vector<char> response;
	while (bufferLen == MAX_BUFF_LEN)
	{
		bufferLen = read(pipefdChildToParent[0], portBuffer, MAX_BUFF_LEN);
		if (bufferLen > 0)
		{
			for (int i = 0; i < bufferLen; ++i)
			{
				response.push_back(portBuffer[i]); //put the entire message into char vector
			}
		}
	}

	string headerStr = "HTTP/1.0 200 OK\r\nMIME-Version:1.0\r\nContent_Type: text/html\r\n";
	char* responseArray = arrayify(response, headerStr); //converts the vector into an array and adds the header
	
	close(pipefdParentToChild[1]);
	close(pipefdChildToParent[0]);

	if (!suppressContent)
	{
		cout << "Response";
		cout << responseArray << endl;		
	}
	if (printResponse)
	{
		cout << "Response Headers: \n";
		cout << headerStr << endl;;
	}


	write(socketDescriptor, responseArray, strlen(responseArray));
	close(socketDescriptor);
}

int parseContentLength(vector<char*>& headers)
{
	string parsedNum = "";
	for (char* header : headers)
	{
		if (strstr(header, "Content-Length") != NULL)
		{
			bool start = false;
			for (unsigned i = 0; i < strlen(header); ++i)
			{
				if (start)
				{
					parsedNum += header[i];
				}
				else if (header[i] == ':')
				{
					start = true;
					if (header[i+1] == ' ')
					{
						i++;
					}
				}
				if (header[i +1] == *stringToC("\n"))
				{
					cout << "origin: " << parsedNum << endl;
					return atoi(stringToC(parsedNum));
				}
			}
		}
	}

	if (parsedNum.size() == 0)
	{
		return 0;
	}
	return atoi(stringToC(parsedNum));
}

void handleCGI (string fullURI, string URI, int socketDescriptor, vector<char*>& headers)
{
	int pipefdChildToParent[2];
	int pipefdParentToChild[2];
	pipe(pipefdParentToChild); //create pipe and store fds
	pipe(pipefdChildToParent);
		
	string space = "";
	string HTTP_PREFIX = "HTTP_";

	vector<string> environmentVars;
	environmentVars.push_back(stringToC(URI));
	bool post = false;

	//put the env vars into array and pass them into the execve call
	for (char* cheader : headers)
	{
		string header = cToString(cheader);

		if (strstr(header.c_str(), "GET"))
		{
			environmentVars.push_back("REQUEST_METHOD=GET");
		}
		else if ( strstr (header.c_str(), "POST") )
		{
			environmentVars.push_back("REQUEST_METHOD=POST");
			post = true;
		}
		else if (strstr(header.c_str(), "Content_Length") || strstr(header.c_str(), "Content-Type"))
		{
			formatHeader(header, space);
		}
		else
		{
			header = formatHeader(header, HTTP_PREFIX);
			environmentVars.push_back(header + '\0');
		}
	}

	environmentVars.push_back(stringToC("GATEWAY_INTERFACE=CGI/1.1"));
	environmentVars.push_back(stringToC("REQUEST_URI=" + URI));
	environmentVars.push_back(stringToC("QUERY_STRING=" + getRequestString(fullURI)));
	
	char* envVarArray[environmentVars.size() +1];
	envVarArray[environmentVars.size()] = NULL;
	
	for (unsigned i = 0; i < environmentVars.size(); ++i )
	{
		string ch = environmentVars[i];
		envVarArray[i ] = stringToC(ch);
	}
	int processID = fork();
	if (processID == 0)
	{
		childProcess(pipefdParentToChild, pipefdChildToParent, envVarArray, URI);
	}
	else
	{
		int contentLength = parseContentLength(headers);
		parentProcess(socketDescriptor, pipefdParentToChild, pipefdChildToParent, post, contentLength);
	}
}

void returnRequestedData(string URI, int socketDescriptor, bool imageFile, vector<char*>& headers)
{
	char portBuffer[1024];
	string fullURI = "";	
	string header;
	bool isDir = false;
	if (strstr(stringToC(URI), "?") != NULL)
	{
		bool found = false;
		string newURI = "";
		for (unsigned i = 0; i < URI.size(); ++i)
		{
			if (URI[i] == '?')
			{
				found = true;
			}
			if (!found)
			{
				newURI += URI[i];
			}
		}
		fullURI = URI;
		URI = newURI;
	}
	struct stat fileStats;
	if (stat (URI.c_str(), &fileStats) )
	{
		URI = "/notFoundError.html";
		struct stat errorFile;
		stat (URI.c_str(), &errorFile);
		int size = errorFile.st_size;
		header = buildHeaders("404 Not Found", size, "text/html");
	}
	else if (S_ISREG(fileStats.st_mode) )
	{
		string type = getFileType(URI);
		if (type == "text/htmlcgi")
		{
			cout << "treating as CGI" << endl;
			//start process to run fork and exec, pipe the output back to the parent
			handleCGI(fullURI, URI, socketDescriptor, headers);
			return;
		}
		int size = fileStats.st_size;
		header = buildHeaders("200 OK", size, type);	
	}
	else if (S_ISDIR(fileStats.st_mode) ) 
	{
		isDir = true;

		struct stat dirstat;
		string indexFile = URI + "index.html";
		if (stat (indexFile.c_str(), &dirstat) )
		{
			string directoryHTML = buildDirectoryIndex(URI);
			header = buildHeaders("200 OK", directoryHTML.size(), "text/html");
		
			strcpy(portBuffer, header.c_str());
		
			write(socketDescriptor, &portBuffer, strlen(portBuffer) ); //write the headers
			if (printResponse)
			{
				cout << portBuffer << endl;
			}
			strcpy(portBuffer, directoryHTML.c_str());
			write(socketDescriptor, &portBuffer, strlen(portBuffer) );

			if (!suppressContent)
			{
				cout << portBuffer << endl;
			}
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
					write(socketDescriptor, &portBuffer, bufferLen); //write the body piece by piece
					if (! suppressContent)
					{
						cout << portBuffer;
					}
				}
			}
		
		}
		close(socketDescriptor);
		return; 
	}
	else
	{
		perror ("Error in requested data\n");
	}
	strcpy(portBuffer, header.c_str());
	write(socketDescriptor, &portBuffer, strlen(portBuffer)); //write the headers
	if (printResponse)
	{
		cout << portBuffer << endl;;
	}
	
	if (isDir)	{}
	else
	{
		unsigned file = open(URI.c_str(), ios::binary);
	 	int bufferLen = 1;

		while (bufferLen > 0)
		{
			bufferLen = read(file, portBuffer, 1024);
			if (bufferLen > 0)
			{
				write(socketDescriptor, &portBuffer, bufferLen); //write the body piece by piece
				if (!suppressContent)
				{
					cout << portBuffer;
				}
			}
		}
	}
	close(socketDescriptor);
}

void processRequest(int socketDescriptor)
{
	vector<char*> headers;
	//reads in the header data
	getHeaderLines(headers, false, socketDescriptor);
		
	if (printRequest)
	{
		for (char * head : headers)
		{
			cout << head << endl;
		}
	}

//  take the read-in data and parse headers for URI, use sample code to read them
	string requestedData = parseHeaders(headers);
	if (requestedData[0] == '/')
	{
		requestedData = requestedData.substr(1, requestedData.size());
	}
	string requestDir = requestedData.substr(0, directory.size());
	if (directory != requestDir) 
	{
		requestedData = directory + "/" + requestedData;
	}
	if (requestedData.size() == 0)
	{
		requestedData = directory;
	}
	returnRequestedData(requestedData, socketDescriptor, true, headers );
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

	for (unsigned i = 0; i < (unsigned)threadCount; ++i)
	{
		info.ID = i;
		sem_wait(&mutEx);
		cout << "Creating threads" << endl;
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
