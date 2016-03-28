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

	int getWins();

	int getLosses();

	int getTies();

	double getWinPercentage();

	void loseMatch();

	void winMatch();

	void CatGotIt();

	string toString();

	string GetRPSThrow();

private:
	string name;
	int wins;
	int losses;
	int ties;
	double win_percentage;
};