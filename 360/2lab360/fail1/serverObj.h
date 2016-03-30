#include <iostream>
#include <string>
#include <ctype.h>
#include <exception>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sstream>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <cstring>
#include <queue>
#include <vector>
#include <pthread.h>
#include <semaphore.h>
using namespace std;

class Server
{
public:
	Server(int iport, bool isuppressContent, bool iprintRequest, 
			bool iprintResponse, int ithreadCount, string idirectory):
		port(iport),
		suppressContent(isuppressContent),
		printRequest(iprintRequest),
		printResponse(iprintResponse),
		threadCount(ithreadCount),
		directory(idirectory)
		{}
		
	~Server(){}

	string toString()
	{
		stringstream ss;
		ss << "Port Number: " << port << endl;
		ss << "Number of threads: " << this->threadCount << endl;
		ss << "Target directory: " << this->directory << endl;
		ss << "Suppress Content: " << suppressContent << endl;
		ss <<  "Print Request headers: " << printRequest << endl;
		ss << "Print Response headers: " << printResponse << endl;
		return ss.str();
	}
	
	void serve()
	{
		const int SOCKET_ERROR = -1;
		startConnection();

		if (listen (socketHandle, QUEUE_SIZE) == SOCKET_ERROR)
		{
			printf("Listen failed\n");
			exit(0);
		}
		
		for (;;)
		{
			printf("Listening for connection\n");
			int newSocketHandle = accept(socketHandle,
					(struct sockaddr* ) &address, (socklen_t* ) &addressSize);
			if (newSocketHandle == SOCKET_ERROR)
			{
				perror("ERROR: could not accept connections");
				exit(0);
			}
			else
			{
				cout << "ADDING NEW CONNECTION" << endl;
				//add a new task to the queue
				sem_wait(&mutEx);
				tasks.push(newSocketHandle);
				sem_post(&mutEx);
				cout << "CONNECTION ADDED\n";
			}
		}
	}

private:
	int port;
	bool suppressContent;
	bool printRequest;
	bool printResponse;
	int threadCount;
	string directory;

	struct sockaddr_in address;
	int addressSize = sizeof(struct sockaddr_in);
	int socketHandle;
	
	static sem_t mutEx;
	static sem_t spaceOnQ;
	static sem_t numOfTasks;

	static queue<int> tasks;

	const int SOCKET_ERROR		=	-1;
	const int BUFFER_SIZE 		=	100;
	const int MAX_MSG_SIZE 		= 	1024;
	const int  QUEUE_SIZE 		=	5;
	const string MESSAGE		=	"This is the message";

	struct ThreadInfo
	{
		int ID;
	};
	
	void startConnection()
	{
		createThreadPool();

		printf("Starting server\n");		
		socketHandle = socket (AF_INET, SOCK_STREAM, 0);
		
		if(socketHandle == SOCKET_ERROR)
		{
			perror("ERROR CREATING SOCKET");
			exit(0);
		}
		address.sin_addr.s_addr = INADDR_ANY;
		address.sin_port = htons(this->port);
		address.sin_family = AF_INET;

		printf("Binding to port %d\n", this->port);
		
		if (bind (socketHandle, (struct sockaddr* ) &address, sizeof(address) )
								== SOCKET_ERROR )
		{
			printf("Failed to connect to host\n");
			exit(0);
		}

		getsockname(socketHandle, (struct sockaddr*) &address, (socklen_t*)&addressSize);
		printf("Socket opened as descriptor %d on port %d for i/o\n", socketHandle, ntohs(address.sin_port) );
	}

	void closeConnection()
	{
		if(close(socketHandle) == SOCKET_ERROR)
		{
			perror("FAILED TO CLOSE SOCKET\n");
			exit(0);
		}
	}
		
	void createThreadPool()
	{
		//initialize semaphores
		sem_init(&mutEx, 0, 1);
		//sem_init(&spaceOnQ, 0, 100);
		//sem_init(&numOfTasks, 0, 0);
		
		struct ThreadInfo info;
		pthread_t threads[this->threadCount];

		for (int i = 0; i < this->threadCount; ++i)
		{
			info.ID = i;

			sem_wait(&mutEx);
	
			cout << "Creating threads" << endl;

//			pthread_create(&threads[i], NULL, serveData, );		
			pthread_create(&threads[i], NULL, &serveData, (void*) &info);
			sem_post(&mutEx);
		}
	}

	static void *serveData(void* dataPointer)
	{
		int socketDescriptor;
		
		while ( 1 )
		{
			sem_wait(&mutEx); //wait for the queue to be available
	
			if (this->tasks.size() > 0)
			{
				socketDescriptor = this->tasks.front();
				this->tasks.pop();
			}
			sem_post(&mutEx); //release the queue

			processRequest(socketDescriptor);
		}	
	}

	void processRequest(int socketDescriptor)
	{
		vector<char*> headers;
		getHeaderLines(headers, false, socketDescriptor);

//		take the read-in data and parse headers for URI, use sample code to read them
	 	string requestedData = parseHeaders(headers);
		cout << "Client has requested " << requestedData << endl;
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
            	fprintf(stderr, "Read Failed on file descriptor %d messagesize = %d\n", fileDescriptor, messagesize);
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

		int readLength = 0; //keep track of the number of chars read, but here it doesn't matter
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
						requestedData += header[i];
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
};
