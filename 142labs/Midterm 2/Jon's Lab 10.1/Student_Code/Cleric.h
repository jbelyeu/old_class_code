#pragma once
#include "Fighter.h"

class Cleric :	public Fighter
{
public:
	Cleric(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in);
	~Cleric(void);
	
	virtual int getDamage();	

	virtual void regenerate();

	virtual void reset();

	//Use special ability: Healing light
	virtual bool useAbility();

	int getMana();

	int MaxMana;
	int CurrentMana;
};

