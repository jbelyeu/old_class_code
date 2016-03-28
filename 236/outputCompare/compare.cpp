#include <iostream>
#include <fstream>
#include <string>
using namespace std;
int main(int argc, char* argv[])
{
	ifstream theirFile;
	ifstream myFile;

	theirFile.open(argv[1]);
	myFile.open(argv[2]);

	string theirLine;
	string myLine;
	int lineNum = 0;
	bool pause = false;

	while (getline(theirFile, theirLine) && getline(myFile, myLine))
	{
		lineNum++;
		
		if (theirLine != myLine)
		{
			pause = true;
			cout << "Disagreement at line " << lineNum << endl;
			cout << "Theirs says: " << theirLine << endl;
			cout << "Mine says: " << myLine << endl;

			int i = 0;
			(myLine.size() > theirLine.size()) ? i = theirLine.size() : i = myLine.size();
			
			for (int j = 0; j < i; j++)
			{
				if (myLine[j] != theirLine[j])
				{
					cout << "First difference found: " << myLine[j] << endl;
					cout << "Other differences may not be shown for clarity" << endl << endl;
					break;
				}
			}
		}

		
	}

	if (!theirFile.eof())
	{
		pause = true;
		cout << "Their file is longer" << endl;
	}
	
	if (!myFile.eof())
	{
		pause = true;
		cout << "My file is longer" << endl;
	}


	if (pause) { cout << "pause"; }
	return 0;
}
