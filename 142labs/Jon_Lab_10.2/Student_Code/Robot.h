#pragma once
#include "Fighter.h"

class Robot :	public Fighter
{
public:
	Robot(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in);
	~Robot(void);
	
	virtual int getDamage();	

	virtual void reset();

	//Use special ability: Shockwave Punch
	virtual bool useAbility();

	double CurrentEnergy;
	double MaxEnergy;
	int BonusDamage;
};

