#include "Player.h"

using namespace std;


Player::Player(string name_in)
{
	name = name_in;
	wins = 0;
	losses = 0;
	ties = 0;
	win_percentage = 0.0;
	
}

Player::~Player(){}

string Player:: getName()
{
	return name;
}

int Player::getWins()
{
	return wins;
}

int Player::getLosses()
{
	return losses;
}

int Player::getTies()
{
	return ties;
}
	
 double Player::getWinPercentage()
{
	if (wins > 0 || losses > 0 || ties > 0)
	{
		win_percentage = wins/(wins+losses+ties);
	}
	else
	{
		win_percentage = 0;
	}

	return win_percentage;
}
 void Player::loseMatch()
 {
	losses++;
 }

 void Player:: winMatch()
 {
	 wins++;
 }

 void Player:: CatGotIt()
 {
	 ties++;
 }

 string Player:: toString()
 {
	 stringstream ss;
	 ss << "Player: " << name << endl;
	 ss << "Wins: " << wins << endl;
	 ss << "Losses: " << losses << endl;
	 ss << "Ties: " << ties << endl;
	 ss << "Win Percentage: " << win_percentage << endl;
	return ss.str();
 }
 string Player:: GetRPSThrow()
 {
	 string r_p_s; //for rock, paper, or scissors
	
	 double rps_throw = rand()%3;	
	 if (rps_throw == 0)
	 {
		 r_p_s = "Rock";
	 }
	 else if (rps_throw == 1)
	 {
		 r_p_s = "Paper";
	 }
	 else 
	 {
		 r_p_s = "Scissors";
	 }

	 return r_p_s;
 }