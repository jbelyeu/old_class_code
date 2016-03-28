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
void battle_fun(vector<Player*> line_up)
{
	const int position1 = 0;
	const int position2 = 1;
	string player1, player2, throw1, throw2;
	player1 = line_up[position1]->getName();
	player2 = line_up[position2]->getName();

	throw1 = line_up[position1]->GetRPSThrow();
	throw2 = line_up[position2]->GetRPSThrow();

	cout << player1 <<" throws " <<throw1 <<endl;
	cout << player2 << " throws " <<throw2 << endl << endl;

	if (throw1 == throw2)
	{
		cout << "\t\tA draw!" <<endl;
		line_up[position1]->CatGotIt();
		line_up[position2]->CatGotIt();
	}
	else if ((throw1 == "Rock"&&throw2 == "Scissors")||(throw1 == "Scissors" &&throw2 == "Paper") || (throw1=="Paper" &&throw2=="Scissors"))
	{
		cout << player1 << " wins!" <<endl;
		line_up[position1]->winMatch();
		line_up[position2]->loseMatch();
	}
	else 
	{
		cout << player2 << " wins!" << endl;
		line_up[position2]->winMatch();
		line_up[position1]->loseMatch();
	}
}