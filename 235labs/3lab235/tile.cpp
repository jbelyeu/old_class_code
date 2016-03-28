#include "tile.h"


tile::tile(string inchars)
{
	chars = inchars;
	used = false;
}

void tile::setUsed(bool inUsed)
{
	used = inUsed;
}

//don't actually get used. It sucks
bool tile::getUsed()
{
	return used;
}

string tile::getString()
{
	return chars;
}

