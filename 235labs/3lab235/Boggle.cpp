#include <set>
#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <list>
#include "tile.h"
#include <cmath>
#include <ctime>

using namespace std;


//1A, 2AAA. Converts words from dictionary to lowercase
string to_lower(string word)
{
	for(unsigned i =0; i < word.size(); i++)
	{
		word[i] = tolower(word[i]);
	}
	return word;
}

//1 Read dictionary, call function to convert to lowercase, store in set
void make_dict(set<string>& dictionary, char* argv[1])
{
	ifstream readDict;
	readDict.open(argv[1]);
	string word;

	while(getline(readDict, word))
	{
		if (word.size() > 3)
		{
			word = to_lower(word);
			dictionary.insert(word);
		}
	}
}
//2AA checks characters to make sure they are alphabetical
bool checkAlpha (string character)
{
	bool alpha = false;
	for (unsigned i = 0; i < character.size(); i++)
	{
		if (isalpha(character[i]))
		{
			alpha = true;
		}
	}
	return alpha;
}

//2A reads letters/combinations from fstream, stores invector. Returns vector<string> characters
vector<string> readCube (char* argv[2])
{
	ifstream inCube;
	inCube.open(argv[2]);
	string character;
	bool alpha;

		vector<string> characters;
		while (inCube >> character)
		{
			alpha = checkAlpha(character);
			if (alpha == true)
			{
				character = to_lower(character);
				characters.push_back(character);
			}
		}
	return characters;
}
//2B converts string from input to tile objects. Return vector of tiles by reference
void toTile(vector<tile>& cube, vector<string>& characters)
{
	for (unsigned i = 0; i < characters.size(); i ++)
	{
		tile newTile = tile(characters[i]);
		cube.push_back(newTile);
	}
}

//2 words holds all words for the tiles . Find square root of size, divide into that many levels, put all vectors created into cube
void create_cube(vector<vector<tile>>& grid, char* argv[1])
{
	vector<string> characters = readCube(argv);
	vector<tile> cube;

	unsigned k = 0;
	double length = sqrt(characters.size());

	for (unsigned j = 0; j <  sqrt(characters.size()); j++)
	{
		grid.push_back(cube);
		
		for ( k; k < length; k++)
		{
			grid[j].push_back(characters[k]);
		}
		length += sqrt(characters.size());
	}	
}

//3 sends to text file.
void print(vector<vector<tile>>& grid,set<string> found, char* argv[3])
{
	ofstream output;
	output.open(argv[3]);

	for (unsigned i = 0; i < grid.size(); i++)
	{
		for (unsigned j = 0; j < grid[i].size(); j++)
		{
			output << grid[i][j].getString() << " ";
		}
		output << endl;
	}
	output <<endl;

	set<string>::iterator iter = found.begin();
	for (iter; iter != found.end(); iter++)
	{
		output << *iter <<endl;
	}
	output.close();
}
// checks conditions in boggle function
bool checker (int row, int column, vector<vector<tile>>& grid)
{
	bool okay = true;
	if ((row < 0 )||(column < 0) || row >= (grid.size()) || column >= (grid.size()))
	{
		okay = false;
		return okay;
	}
	if (grid[column][row].getUsed() == true)
	{
		okay = false;
//		grid[column][row].setUsed(false);
		return okay;
	}
	return true;
}



// searches and recurses. All other functions seem to be working
void boggle(vector<vector<tile>>& grid, set<string>& dictionary, set<string>& found, int row, int column, string word)
{
	if (checker(row, column, grid) == false)
	{
		return;
	}
	
	grid[column][row].setUsed(true);

	word += grid[column][row].getString();
	set<string>::iterator pos = dictionary.lower_bound(word);
	
	if (pos == dictionary.end()|| word.compare(pos->substr(0, word.size())) != 0 )
	{
		grid[column][row].setUsed(false);
		return;
	}

	if (word == *pos)
	{
		found.insert(word);
	}
	
	boggle(grid, dictionary, found, row, (column+1), word);
	boggle(grid, dictionary, found, row, (column-1), word);
	boggle(grid, dictionary, found, (row+1), column, word);
	boggle(grid, dictionary, found, (row-1), column, word);
	boggle(grid, dictionary, found, (row +1), (column+1), word);
	boggle(grid, dictionary, found, (row -1), (column+1), word);
	boggle(grid, dictionary, found, (row +1), (column-1), word);
	boggle(grid, dictionary, found, (row -1), (column-1), word);
	grid[column][row].setUsed(false);
	return;
}
void search(vector<vector<tile>>& grid, set<string>& dictionary, set<string>& found)
{	
	string word;
	for (int row = 0; row < grid.size(); row++)
	{
		for (int column = 0; column < grid[row].size(); column++)
		{
			boggle(grid, dictionary, found, (row), (column), word);
		}
	}
}


int main(int argc, char* argv[])
{
	set<string> dictionary;
	make_dict(dictionary, argv);
	vector<vector<tile>> grid;
	create_cube(grid, argv);
	set<string> found;
	search(grid, dictionary, found);
	print(grid,found, argv);
	return 0;
}