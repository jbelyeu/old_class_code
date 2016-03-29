#include "Fighter.h"
#include <sstream>


Fighter::Fighter(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in)
{
	name = name_in;
	type = type_in;
	MXP = MXP_in;
	CurrentHP = MXP_in;
	strength = strength_in;
	speed = speed_in;
	magic = magic_in;
	damage = 0;

	/* stringstream ss;
	 ss << "Player: " << name << endl;
	 ss << "type: " << type << endl;
	 ss << "MXP: " << MXP << endl;
	 ss << "strength: " << strength << endl;
	 ss << "speed: " << speed << endl;
	 ss << "magic: " << magic << endl;
	cout << ss.str(); */
}


Fighter::~Fighter(void){}

string Fighter :: getName()
{
	return name;
}

int Fighter :: getMaximumHP()
{
	return MXP;
}

int Fighter :: getCurrentHP() 
{
	return CurrentHP;
}

int Fighter ::getStrength() 
{
	return strength;
}

int Fighter ::getSpeed() 
{
	return speed;
}

int Fighter ::getMagic() 
{
	return magic;
}

int Fighter ::getDamage() 
{
	return damage;
}

void Fighter :: takeDamage(int damage) 
{
	if ((damage -(speed/4)) >= 1) 
	{
		CurrentHP -= (damage - (speed/4));
	}
	else 
	{
		CurrentHP -= 1;
	}
}

void Fighter :: reset()
{
	CurrentHP = MXP;
}

void Fighter :: regenerate() 
{
	if (strength/6 > 1)
	{
	CurrentHP += (strength/6);
	}
	else 
	{
		CurrentHP++;
	}
	if (CurrentHP > MXP) {CurrentHP =MXP;}
}

bool Fighter ::useAbility() 
{
	bool ability_used = false;
	return ability_used;
}