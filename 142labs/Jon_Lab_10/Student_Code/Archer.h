#pragma once
#include "Fighter.h"

class Archer :	public Fighter
{
public:
	Archer(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in);
	~Archer(void);
	
	virtual int getDamage();	

	virtual void reset();

	//Use special ability: Dynamic Speed
	virtual bool useAbility();

	int OriginalSpeed;
};

