/*
Test 1:
Input: 1, 2, a, 1
Output: return to menu, prompt for player name, return to menu, display a, stats of zero.
Input: 2, a, 1, 
Output: prompt for name, display error (already exists), return to menu.

Test 2:
Input 2, a, 2, b, 2, c, 3, a, 3, a,
Output: Prompt for, accept three names, prompt for name for line-up, accept "a" twice.
Input: 4, 5, 5, 4, 1
Output: "a" fights "a", displays throws, either winner or draw, displays error (nothing left in line-up), displays list with updated wins/losses/draws,win record.

Test 3:
Input: 2, a, 2, b, 2, c, 2, d, 2, e.
Output: Prompt for, receives 5 names. 
Input: 3, a, 3, b, 3, c, 3, d, 3, e.
Output: Prompt for, receive the 5 names for line-up.
Input: 5, 5, 5.
Output: Plays two rounds, displays throws for a vs. b, then c vs. d, fails on e (lack of players in line-up). Return to menu.
*/

#include <iostream>
#include <string>
#include <vector>
#include <ctime>
#include "Player.h"

using namespace std;

//Creates Player object and places it in vector of all players
void create_player(vector<Player*>& all_players, string name)
{
	for (int j = 0; j < all_players.size(); j++)
	{
		if (name == all_players[j]->getName())
		{
			cout << "That player is already in the game." <<endl;
			return;
		}
	}
	Player* PlayerPointer= new Player(name);
	all_players.push_back(PlayerPointer);
	cout <<name <<" is now sponsored." << endl <<endl;
	return;
}
void show_players(vector<Player*>& all_players)
{
	if (all_players.size() < 1)
	{
		cout << "\tThere are no players listed.\n" <<endl;
	}
	else 
	{
		for (int i = 0; i < all_players.size(); i++)
		{
			cout << all_players[i]->toString() << endl;
		}
	}
}
void add_to_queue(vector<Player*>& line_up, vector<Player*>& all_players, string name)
{
	for (int j = 0; j < all_players.size(); j++)
	{
		if (name == all_players[j]->getName())
		{
			Player* line_pointer = all_players[j];
			line_up.push_back(line_pointer);
			cout << name <<" has been added to the line-up." << endl << endl;
			return;
		}		
	}

	cout << "That player has not yet been sponsored. If you wish to sponsor that player, choose that option on the main menu." <<endl;

}
vector<Player*> battle_deleter(vector<Player*>& line_up)
{
	vector<Player*> temp;
	for (int i =0; i < line_up.size(); i++)
	{
		if (i > 1)
		{
			temp.push_back(line_up[i]);
		}
	}
	return temp;
}

void battle_fun(vector<Player*>& line_up)
{
	const int position1 = 0;
	const int position2 = 1;
	string player1, player2, throw1, throw2;
	player1 = line_up[position1]->getName();
	player2 = line_up[position2]->getName();

	throw1 = line_up[position1]->GetRPSThrow();
	throw2 = line_up[position2]->GetRPSThrow();
	if ((line_up[position1]->getName() )== (line_up[position2]->getName()))
	{
		cout << "\t\tThe players are the same! A draw!" <<endl;
		line_up[position1]->CatGotIt();
		//line_up[position2]->CatGotIt();
		line_up[position2]->getWinPercentage();
		line_up = battle_deleter(line_up);
		return;
	}
	

	cout << player1 <<" throws " <<throw1 <<endl;
	cout << player2 << " throws " <<throw2 << endl << endl;

	if (throw1 == throw2)
	{
		cout << "\t\tA draw!" <<endl;
		line_up[position1]->CatGotIt();
		line_up[position2]->CatGotIt();
		line_up[position1]->getWinPercentage();
		line_up[position2]->getWinPercentage();
	}
	else if ((throw1 == "Rock"&&throw2 == "Scissors")||(throw1 == "Scissors" &&throw2 == "Paper") || (throw1=="Paper" &&throw2=="Rock"))
	{
		cout << player1 << " wins!" <<endl;
		line_up[position1]->winMatch();
		line_up[position2]->loseMatch();
		line_up[position1]->getWinPercentage();
		line_up[position2]->getWinPercentage();

	}
	else 
	{
		cout << player2 << " wins!" << endl;
		line_up[position2]->winMatch();
		line_up[position1]->loseMatch();
		line_up[position1]->getWinPercentage();
		line_up[position2]->getWinPercentage();
	}
	line_up = battle_deleter(line_up);
}

int main()
{
	srand(time(0));
	vector<Player*> all_players;
	vector<Player*> line_up;
	bool play = true;
	int option = 0;
	string name;
	while (play)
	{
		cout << "\tWELCOME TO THIS ALMOST COOL GAME! PLEASE SELECT AN OPTION.\n\n(0) To Quit\n(1) To See All Players" <<endl;
		cout << "(2) To Add a Player \n(3) To Put A Player In The Queue For The Fight \n(4) To See All Players In The Queue" << endl;
		cout << "(5) To Fight" << endl;

		cin >> option;
		cin.ignore();

		if (option == 0)
		{
			play = false;
		}
		else if (option == 1)
		{
			show_players(all_players);

		}
		else if (option == 2)
		{			
			cout << "What is the name of the new player you would like to sponsor? " <<endl;
			getline(cin, name);						
			create_player(all_players, name);
			//cout <<name <<" is now sponsored." << endl <<endl;
		}
		else if (option == 3)
		{
			cout << "Please choose a player to add to the line-up." <<endl;
			getline(cin, name);
			add_to_queue(line_up, all_players, name);			
		}
		else if (option == 4)
		{
			show_players(line_up);
		}
		else if (option == 5)
		{
			if (line_up.size() >= 2)
			{
				battle_fun (line_up);

			}
			else
			{
				cout << "There are insufficient players to hold an interesting match. Please add more" << endl <<endl;
			}
		}
		else 
		{
			cout << "That's not a developed option at this time. Try again later\n" << endl;
		}
	
	
	}
	system ("pause");
	return 0;
}