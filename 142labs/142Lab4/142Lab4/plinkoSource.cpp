#include <iostream>

using namespace std;

int main()
{
	int option;
	int slot;
	int chips;
	bool play = true;
	bool start = true;

	while (play == true)
	{
		cout <<"Welcome to this knockoff of Plinko. If you're ready to win big, please select an option!" <<endl;
		cout <<"Type \"1\" to drop one chip\n Type \"2\" to drop multiple chips \nType \"3\" to quit the program" <<endl;
		cin >> option;

		

		if (option == 3) {play = false;}
		if (option == 1)
		{
			cout << "Choose a slot" <<endl;
			cin >> slot;
			if (slot < 0 || slot > 8)
			{
				cout << "Invalid input" <<endl;
				play == false;
		}
		if (option == 2) 
		{
			cout << "How many chips would you like to drop?" <<endl;
			cin >> chips;
			cout << "Choose a slot" <<endl;

		}