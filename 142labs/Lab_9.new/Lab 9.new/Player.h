#pragma once
#include <iostream>
#include <string>
#include <sstream>
#include <ctime>
#include <cstdlib>

using namespace std;

class Player
{
public:
	Player(string name_in);
	virtual ~Player();

	string getName();

	double getWins();

	double getLosses();

	double getTies();

	void getWinPercentage();

	void loseMatch();

	void winMatch();

	void CatGotIt();

	string toString();

	string GetRPSThrow();

private:
	string name;
	double wins;
	double losses;
	double ties;
	double win_percentage;
};