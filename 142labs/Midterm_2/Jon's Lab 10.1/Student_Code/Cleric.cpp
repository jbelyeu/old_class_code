#include "Cleric.h"
#include <sstream>



Cleric::Cleric(string name_in, string type_in, double MXP_in, double strength_in, double speed_in, double magic_in)
		:Fighter(name_in, type_in, MXP_in, strength_in, speed_in, magic_in)
{
	MaxMana = 5*magic_in;
	CurrentMana = MaxMana;

	/*stringstream ss;
	 ss << "Player: " << name << endl;
	 ss << "type: " << type << endl;
	 ss << "MXP: " << MXP << endl;
	 ss << "strength: " << strength << endl;
	 ss << "speed: " << speed << endl;
	 ss << "magic: " << magic << endl;
	 ss << "MaxMana: " << MaxMana << endl;
	 ss << "Current Mana: " << CurrentMana << endl;
	cout << ss.str();*/
}

Cleric::~Cleric(void){}

int Cleric :: getDamage()
{
	damage = magic;
	return damage;
}


void Cleric :: reset()
{
	Fighter::reset();
	CurrentMana = MaxMana;
}

void Cleric :: regenerate()
{
	Fighter::regenerate();

	if (magic/5 >1)
	{
	CurrentMana = (magic/5);
	}
	else
	{
		CurrentMana++;
	}
	if (CurrentMana > MaxMana) { CurrentMana = MaxMana;}
}

bool Cleric :: useAbility()
{
	bool used = false;

	if (CurrentMana >= CLERIC_ABILITY_COST)
	{
		if ((magic/3) >= 1)
		{
			CurrentHP += (magic/3);
		}
		else {CurrentHP++;}

		CurrentMana -= CLERIC_ABILITY_COST;
		used = true;
	}
	if (CurrentHP > MXP) 
	{
		CurrentHP = MXP;
	}
	cout << endl <<endl <<CurrentMana <<endl <<endl;
	return used;

}

int Cleric :: getMana()
{
	return CurrentMana;
}
