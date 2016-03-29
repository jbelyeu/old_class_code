#include "Archer.h"
#include <sstream>



Archer::Archer(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in)
		:Fighter(name_in, type_in, MXP_in, strength_in, speed_in, magic_in)
{
	
	OriginalSpeed = speed_in; 
	cout << "Archer created" <<endl <<endl;

	stringstream ss;
	 ss << "Player: " << name << endl;
	 ss << "type: " << type << endl;
	 ss << "MXP: " << MXP << endl;
	 ss << "strength: " << strength << endl;
	 ss << "speed: " << speed << endl;
	 ss << "magic: " << magic << endl;
	 ss << "Oiginal speed: " << OriginalSpeed <<endl <<endl;
	cout << ss.str();
}

Archer::~Archer(void){}

int Archer :: getDamage()
{
	damage = speed;
	return damage;
}


void Archer :: reset()
{
	Fighter ::reset();
	speed = OriginalSpeed;	
}

bool Archer :: useAbility()
{
	bool used = true;
	speed++;	
	return used;
}


