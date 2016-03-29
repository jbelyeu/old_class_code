# include <iostream>
#include <vector>
#include <string>
#include "Arena.h"
#include "Fighter.h"
#include "Battle.h"


using namespace std;

int main()
{
	cout << "(name) (type) (maximum hit points) (strength) (speed) (magic)" << endl;
	string info = "fred R 2 3 4 5";
	string moreinfo = "jimmy A 6 7 8 9";	
	//test addFighter method
	Arena* apointer = new Arena();
	bool added = (apointer ->addFighter(info));
	added = (apointer ->addFighter(moreinfo));
	if (added == true)
	{
		cout << "yups" << endl;

	}
	if (added == false)
	{
		cout << "Nopes" << endl;
	}
	 

	/*added = (apointer ->addFighter(info));
	if (added == true)
	{
		cout << "yups" << endl;

	}
	if (added == false)
	{
		cout << "Nopes" << endl;
	}*/

	/*
	//test removeFighter
	string name = "fred";
	bool removed = (apointer ->removeFighter(name));

	if (removed == true)
	{
		cout << "Yesir" << endl;
	}

	if (removed == false)
	{
		cout << "Nope" <<endl;
	}*/

	//test getFighter

/*	string name = "fred";
	FighterInterface* FPointer = (apointer->getFighter(name));
	cout << FPointer->getName() << endl;
*/
/*	int size = (apointer->getSize());
	cout << endl << endl << size <<endl;
*/
/*	string name = "fred";
	FighterInterface* FPointer = (apointer->getFighter(name));
*/
/*

	int maximum = (FPointer->getMaximumHP());
	cout << endl << endl << maximum << endl <<endl;


	int current = (FPointer ->getCurrentHP());
	cout << endl << endl << current << endl <<endl;

	int strength (FPointer ->getStrength());
	cout << endl << endl << strength << endl <<endl;

	int speed (FPointer ->getSpeed());
	cout << endl << endl << speed << endl <<endl;

	int magic (FPointer ->getMagic());
	cout << endl << endl << magic << endl <<endl;

	int damage (FPointer ->getDamage());
	cout << endl << endl << damage << endl <<endl;
*/



	//cout << FPointer->getDamage();
/*	int damage = 4;
	cout << FPointer ->getCurrentHP() << endl << endl;
	FPointer ->takeDamage(damage);
	cout << FPointer ->getCurrentHP() << endl << endl;
	FPointer ->useAbility();
	cout << FPointer ->getCurrentHP() << endl << endl;
*/



	system ("pause");
	return 0;

}