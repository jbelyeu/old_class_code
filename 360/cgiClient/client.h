//#pragma once
#include <iostream>
#include <string>
#include <sstream>
#include <string.h>
#include <netdb.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <fstream>
#include <unistd.h>
#include <signal.h>
#define SOCKET_ERROR        -1
#define BUFFER_SIZE         100
#define HOST_NAME_SIZE      255

using namespace std;

class Client
{
public:
	Client(bool iverbose, bool irepeatRequest, int icount, string ihostname, int iport, string iresourcePath) :
		verbose(iverbose),
		repeatRequest(irepeatRequest),
		count(icount),
		hostname(ihostname),
		port(iport),
		resourcePath(iresourcePath)		
	{}

	~Client(){}	

	string toString()
	{
		stringstream ss;
		ss << "verbose: " << verbose << endl;
		ss << "repeat: " << repeatRequest << endl;
		ss << "count: " << count << endl;
		ss << "host:  " << hostname << endl;
		ss << "port:  " << port << endl;
		ss << "resource:  " << resourcePath << endl;
		
		return ss.str();
	}

	void countSuccessfulCalls()
	{
		for (int i = 0; i < count; ++i)
		{
			openConnection();
			performGETAction();
			closeConnection();
		}

		cout << endl << successfulCalls << " Downloads with OK status code." << endl;
	}

	void openConnection()
	{
		struct sockaddr_in address;
		struct hostent* hostInfo;
		long hostAddressNum;
 	 
		//specify IPv4, TCP, let the system choose the protocol
		socketNum = socket(AF_INET, SOCK_STREAM, 0);
		if (socketNum == SOCKET_ERROR)
		{
			perror("ERROR CREATING SOCKET\n");
			exit(0);
		}
		
		//gets the IP address of the host
		hostInfo = gethostbyname(hostname.c_str());
		if (hostInfo == NULL)
		{
			string errorMsg = "INVALID HOST NAME: " +  hostname + "\n";
			perror(errorMsg.c_str());
			exit(0);
		}
	
		bzero( (char*) &address, sizeof(address) );
		memcpy(&hostAddressNum, hostInfo->h_addr, hostInfo->h_length);
		address.sin_addr.s_addr = hostAddressNum;
		address.sin_port = htons(port); //host-to-network numeric conversion
		address.sin_family = AF_INET; //Address Family INET
		
		int connectionVal = connect(socketNum, (struct sockaddr*)&address, sizeof(address));

		if ( connectionVal == SOCKET_ERROR )
		{
			perror("ERROR CONNECTING TO HOST\nCOULD NOT CONNECT...EXITING\n");
			exit(0);
		}		
	}

	void performGETAction ()
	{
		string requestString = "GET " + resourcePath + " HTTP/1.1\r\nHost: " + 
		hostname + "\r\nAccept: */*\r\nContent-Type: text/html\r\n";
	
	
		cout << requestString <<endl;
		if (verbose & ! repeatRequest)
		{
			cout << "Request: \n" << requestString <<endl;
		}

		int numBytes = 0;
		int requestLength = strlen(requestString.c_str());
		numBytes = write(socketNum, requestString.c_str(), requestLength );
		
		if (numBytes < 0)
		{
			perror("GET ACTION FAILED");
			exit(0);
		}
		
		vector<char*> headerLines;	

		getHeaderLines(headerLines, false );	

		if (verbose && ! repeatRequest)
		{
			for (char* line : headerLines)
			{
				cout << line << endl;
			}
			cout << endl;
		}
		int bodyLength = getBodyLength(headerLines);
	
		vector <char* > bodyLines;
		getBodyLines(bodyLines, bodyLength);

		if (! repeatRequest)
		{
			for (char* line : bodyLines)
			{	
				cout << line << endl;
			}
		}	
	
	}

	void closeConnection()
	{
		if (close(socketNum) == SOCKET_ERROR)
		{
			perror ("ERROR DISCONNECTING FROM HOST\nCOULD NOT CLOSE SOCKET...EXITING\n");
			exit(0);
		}
	}
	
private:
	bool verbose;
	bool repeatRequest;
	int count;
	string hostname;
	int port;
	string resourcePath;
	int socketNum;
	const int MAX_MSG_SIZE = 1024;
	int contentLength = -1;
	int successfulCalls = 0;
//	char* http = "HTTP_";		
	//determines if character is whitespace
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
		
	// strips whitespaces
	void strip(char *line)
	{
		int len = strlen(line);
		while (isWhitespace(line[len]))
		{
			line[len--] = '\0';
		}
	}

	//check the status header. If OK is present, increment counter
	void incrementSuccess(char* header)
	{
		strip(header);
		string okayCode = "OK" ;
		if (strstr(header, okayCode.c_str()))
		{
			successfulCalls ++;
		}
	}

	int getBodyLength(vector<char* >& headers)
	{
		string contentHeader = "Content-Length";
		string statusLine = "HTTP";

		string number = "";
		for (char* header : headers)
		{
			if (strstr(header, contentHeader.c_str()) )
			{
				strip(header);

				for (int i = strlen(header); i > 0; --i)
				{
					char space = ' ';
					char colon = ':' ;
					if (header[i-1] == space || header[i-1] == colon) 
					{
						return atoi(number.c_str());
					}
					number = header[i-1] + number;
				}
			}
			else if (strstr(header, statusLine.c_str()))
			{
				incrementSuccess(header);
			}
		}
		return true;
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

		void tweakCGIChars(char* str)
		{
			char* s;
			
			s = str;
			for (int i = 0; s[i] != ':'; i++)
			{
				//if lowercase alpha, cast to upper
				if (s[i] >= 'a' && s[i] <= 'z')
				{
					s[i] = 'A' + (s[i] = 'a');
				}
				//replace dashes with underlines	
				if (s[i] == '-')
				{
					s[i] = '_';
				}
			}
		}

	char* formatHeader ( char* str, char* prefix)
	{
		char* result = (char*) malloc (strlen (str) + strlen (prefix) );
		char* value = strchr(str, ':') + 2;	
		tweakCGIChars(str);
		*(strchr(str, ':') ) = '\0';
		sprintf(result, "%s%s=%s", prefix, str, value);
		return result;
	}

	void getHeaderLines(vector<char *> &headerLines, bool envformat)
	{
		// Read the headers, look for specific ones that may change our responseCode
		char *line;
		char *topLine;

		int readLength = 0; //keep track of the number of chars read, but here it doesn't matter
		topLine = getLine(socketNum, readLength);
		
		while(strlen(topLine) != 0)
		{
			//format the header specially for CGI
			if (strstr(topLine, "Content-Length") || strstr(topLine, "Content-Type"))
			{
				if (envformat)
				{
//					string emptyStr = "";
//					line = formatHeader(topLine, emptyStr.c_str());
				}
				else
				{
					line = strdup(topLine);
				}
			}
			else
			{
				if (envformat)
				{
//					line = formatHeader(topLine, http);
				}
				else
				{
					line = (char *)malloc((strlen(topLine) + 10) * sizeof(char));
					sprintf(line, "%s", topLine);    
				}
			}
			headerLines.push_back(line);
			free(topLine);
			topLine = getLine(socketNum, readLength);
		}
		free(topLine);
	}
		
	void getBodyLines(vector<char*>& bodyLines, int amountToRead)
	{
		int amountRead = 0;	
		int totalAmountRead = 0;
		char *line;
		char *topLine;

		//keeps track of how much has been read for length check
		topLine = getLine(socketNum, amountRead);
		totalAmountRead = amountRead;
	
		do
		{
			line = (char *)malloc((strlen(topLine) + 10) * sizeof(char));
			sprintf(line, "%s", topLine);    

			bodyLines.push_back(line);
			free(topLine);
			topLine = getLine(socketNum, amountRead);
			totalAmountRead += amountRead;
		}
		while( totalAmountRead < amountToRead );
		
		line = (char *)malloc((strlen(topLine) + 10) * sizeof(char));
		sprintf(line, "%s", topLine);    
		bodyLines.push_back(line);
		free(topLine);
	}
};
