#include "Robot.h"
#include <sstream>
#include <math.h>




Robot::Robot(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in)
		:Fighter(name_in, type_in, MXP_in, strength_in, speed_in, magic_in)
{
	MaxEnergy = (2*magic);
	reset();

	cout << "Robot created " << endl << endl;
	stringstream ss;
	 ss << "Player: " << name << endl;
	 ss << "type: " << type << endl;
	 ss << "MXP: " << MXP << endl;
	 ss << "strength: " << strength << endl;
	 ss << "speed: " << speed << endl;
	 ss << "magic: " << magic << endl;
	 ss << "MaxEnergy: " << MaxEnergy << endl;
	 ss << "CurrentEnergy: " << CurrentEnergy << endl;
	cout << ss.str();
}

Robot::~Robot(void){}

int Robot :: getDamage()
{
	damage = strength + BonusDamage;
	BonusDamage = 0;
	return damage;

}


void Robot :: reset()
{
	Fighter ::reset();
	CurrentEnergy = MaxEnergy;
	BonusDamage = 0;
}


bool Robot :: useAbility()
{
	bool used = false;
	if (CurrentEnergy >= ROBOT_ABILITY_COST)
	{
		const double four = 4;
		BonusDamage = strength*(pow((CurrentEnergy/MaxEnergy), four));
		CurrentEnergy -= ROBOT_ABILITY_COST;
		used = true;
		cout << endl <<endl <<BonusDamage <<endl<<endl;
	}

	return used;

}


