#include <iostream>
#include <string>
#include <ctime>
#include <cstdlib>

using namespace std;


double counter(double slot)
{
	double points;
	if (slot == 0)	{ points = 100;}
	else if (slot == 1) { points = 500;}
	else if (slot == 2) { points = 1000;}
	else if (slot == 3) { points = 0;}
	else if (slot == 4) { points = 10000;}
	else if (slot == 5) { points = 0;}
	else if (slot == 6) { points = 1000;}
	else if (slot == 7) { points = 500;}
	else				{ points = 100;}
	return points;
}

double one_chip(double to_play) {
	//Prompt for slot selection
	double slot;
	cout << "Type your choice of a slot between 0 and 8 to begin" << endl;
	cin >> slot;
	if(slot <=8 && slot>=0)	{
		cout << "Slot: "<< slot << endl;
		for (int row = 0; row < 12; row++)	{
			double shift = rand()%2;
			if (shift == 0)  {slot -= 0.5;}
			else {slot += 0.5;}
			if (slot >8) {slot--;}
			if (slot < 0) {slot++;}
			cout << "Slot: " <<slot << endl;
		}
		double point_value = counter (slot);
		cout << point_value << endl;
	}
		else {
			cout << "No, dummy!" << endl << endl;
		}
return 0;
}

double cheater(double chips) {
	if (chips >= 1) {
		while (chips >= 1) {}
	}
	return 0;
}






int main ()
{
	
	bool menu = true;

	while (menu){

		srand(time(0));
		double slot = 0;
		int to_play = 0;
		cout << "Menu:\nWelcome to this knockoff of Plinko! \nPlease select an option: Type \"1\" to play a single chip, \"2\" to play multiple chips, \"3\" to quit, or \"4\" to cheat." << endl;
		cin >> to_play;
		if (to_play == 3)	{
			menu = false;
		}
		if (to_play == 1)	{
			one_chip(to_play);
		}
		if (to_play == 2) 	{
			//Prompt for number of chips to drop
			cout << "How many chips would you like to play?" << endl;
			int chips;
			cin >> chips;
			double divisor =chips;

			if (chips > 0)	{
				cout << "Which slot would you like to play?" << endl;

				double slot;
				double sec_slot;
				cin >> slot;
				sec_slot = slot;
				if (slot >=0 && slot <= 8) {
					double avg_points;
					double tot_points = 0;
					double points;
					while (chips > 0) {
						//	cout << "Slot: " << slot << endl;
						for (double row = 0; row < 12; row++)	{
					
							double shift = rand()%2;
								if (shift == 0)  {
								slot -= 0.5;
							}
							else {
								slot += 0.5;
							}
							if (slot >8) {
									slot--;
							}
							if (slot < 0) {
									slot++;
							}
						//	cout << "Slot: " << slot << endl <<endl;
						}
						double point_value = counter (slot);
						cout << "Points " << point_value <<endl;
						tot_points += point_value;
						slot = sec_slot;
						// print average
						chips--;	
					}
				
					cout << "Total points earned: " << tot_points << endl;
					//double avg_points;
					avg_points = (tot_points/divisor);
					cout << endl;
					cout << "Average points per chip: " << avg_points << endl;
				
				}
			}
		}
	
		if (to_play == 4){
			cout << "Select number of chips. You may drop this number into each slot if no one is looking." << endl;
			//call function "cheater"
			double chips;
			cin >> chips;
			cheater(chips);
		}
	}	 
	return 0;
}