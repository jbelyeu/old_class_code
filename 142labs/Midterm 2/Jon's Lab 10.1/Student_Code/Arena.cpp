#include "Arena.h"
#include "Fighter.h"
#include "Robot.h"
#include "Archer.h"
#include "Cleric.h"
#include <sstream>




Arena::Arena(void){}

Arena::~Arena(){}

bool Arena::addFighter(string info)
{
	bool added = false;
	string name_in = " ";
	string type_in;
	int MXP_in;
	int strength_in;
	int speed_in;
	int magic_in;
	int error = -1000;

	stringstream myStream = stringstream(info);

	for (int i = 0; i <= 6; i ++)
	{
		if (i == 0) 
		{
			myStream >> name_in;
			if (name_in == " ") {return added;}
			for (int k = 0; k <line_up.size(); k++)
			{
				if (name_in == line_up[k]->getName())
				return added;
			}
		}
			else if (i == 1) 
			{
				myStream >> type_in;
				if (type_in != "C" && type_in != "R" && type_in != "A")	{ return added;}
			}
			
			else if (i == 2) {myStream >> MXP_in;}
			
			else if (i == 3) {myStream >> strength_in;}
			else if (i == 4) {myStream >> speed_in;}
			
			else if (i == 5) {myStream >> magic_in;}
			
			if (myStream.fail()) {return added;} //test for insufficient inputs

			else if (i == 6) {myStream >> error;} //test for too many inputs
			
			if (error != -1000) {return added;}					
			
	}		
	if (MXP_in <0 || strength_in < 0 || speed_in < 0 || magic_in <0)
	{
		return added;
	}
	FighterInterface* FighterPointer;
	if (type_in == "C")
	{
		FighterPointer = new Cleric(name_in, type_in, MXP_in, strength_in, speed_in, magic_in);	
	}
	else if (type_in == "R")
	{
		FighterPointer = new Robot(name_in, type_in, MXP_in, strength_in, speed_in, magic_in);
	}
	else if (type_in == "A")
	{
		FighterPointer = new Archer(name_in, type_in, MXP_in, strength_in, speed_in, magic_in);
	}

	Arena::line_up.push_back(FighterPointer);
	added = true;
	
	return added;
}

bool Arena ::removeFighter(string name) 
{
	bool removed = false;
	for (int i = 0; i < line_up.size(); i++)
	{
		if (name == line_up[i]->getName())
		{
			vector<FighterInterface*> copy;
			for (int j = 0; j < line_up.size(); j++)
			{					
				if (j!=i)
				{
					copy.push_back(line_up[j]);
				}
			}
			line_up = copy;
			removed = true;
			return removed;
		}
	}	
	return removed;
}

FighterInterface* Arena ::getFighter(string name) 
{
	for (int i = 0; i < line_up.size(); i++)
	{
		if (name == line_up[i]->getName())
		{
			FighterInterface* FPointer;
			FPointer = line_up[i];
			return FPointer;
		}
	}
	return NULL;	
}

int Arena ::getSize() 
{
	int size = line_up.size();
	return size;
}
