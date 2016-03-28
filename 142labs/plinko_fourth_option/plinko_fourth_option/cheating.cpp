/*
Test case 1:
Input: 
1,0
Output: 
Slot: 0
Slot: 0.5
Slot: 1
Slot: 0.5
Slot: 0
Slot: 0.5
Slot: 0
Slot: 0.5
Slot: 0
Slot: 0.5
Slot: 1
Slot: 0.5
Slot: 0
Points 100

Test case 2:
Input:
2, 3, 4
Output:
Total points earned: 21000
Average points per chip: 7000

Test case 3:
Input:
-5
Output
//Outside parameters. 
Return to menu.

Test case 4:
Input:
3

Output:
//End program

Test case 5:
Input: 4, 3
Output:
Total points earned: 11500
Average points per chip: 3833.33

Total points earned: 1600
Average points per chip: 533.333

Total points earned: 500
Average points per chip: 166.667

Total points earned: 2500
Average points per chip: 833.333

Total points earned: 1500
Average points per chip: 500

Total points earned: 1500
Average points per chip: 500

Total points earned: 21000
Average points per chip: 7000

Total points earned: 1000
Average points per chip: 333.333

Total points earned: 2500
Average points per chip: 833.333

*/

#include <iostream>
#include <string>
#include <ctime>
#include <cstdlib>

using namespace std;

double point_calculator(double slot)
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

double falling(double slot, double sec_slot, bool print = true)
{
	if (print) {
		cout << "Slot: "<< slot << endl;
	}
	for (int row = 0; row < 12; row++)	{
					
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
		if (print)
		{
			cout << "Slot: " <<slot << endl;
		}
	}
	return slot;
}
double average_calculator(int chips, double sec_slot, double slot) {
	double divisor = chips;
	if (chips > 0)	{
		if (slot >=0 && slot <= 8) {
			double avg_points;
			double tot_points = 0;
			double points;
			while (chips > 0) {
				slot = sec_slot;
				slot= falling(slot, sec_slot, false);		
				points = point_calculator(slot);
				tot_points += points;
				// print average
				chips--;	
			}
			cout << "Total points earned: " << tot_points << endl;
			avg_points = (tot_points/divisor);
			cout << "Average points per chip: " << avg_points << endl <<endl;
		}
	}
	return 0;	
}

int main ()
{
	
	bool menu = true;

	while (menu){

		srand(time(0));
		double slot;
		int to_play;
		cout << "Menu:\nWelcome to this knockoff of Plinko! \nPlease select an option: Type \"1\" to play a single chip, \"2\" to play multiple chips, \"3\" to quit or \"4\" to cheat." << endl;
		cin >> to_play;
		if (to_play == 3)	{
			menu = false;
		}
		if (to_play == 1){
			//Prompt for slot selection
			cout << "Type your choice of a slot between 0 and 8 to begin" << endl;
			cin >> slot;

			if(slot <=8 && slot>=0)	{
				slot = falling(slot, slot, true);
				double points = point_calculator(slot);
				cout << "Points " << points <<endl;
			}
			else {
				cout << "No, dummy!" << endl << endl;
			}
		}
		if (to_play == 2) 	{
			//Prompt for number of chips to drop
			cout << "How many chips would you like to play?" << endl;
			int chips;
			cin >> chips;
			double divisor = chips;

			if (chips > 0)	{
				cout << "Which slot would you like to play?" << endl;
				double slot;
				double sec_slot;
				cin >> slot;
				sec_slot = slot;

				if (slot >=0 && slot <= 8) {
					average_calculator (chips, slot,sec_slot);


					double avg_points;
					double tot_points = 0;
					double points;					
				}
			}
		}
		if (to_play == 4) {
			cout << "Cheating is easy. Type a number of chips to drop into every slot (while no one is looking)." << endl;
			int chips;
			cin >> chips;
			for (slot = 0; slot < 9; slot ++) {
				average_calculator(chips, slot, slot);
			}
		}
	}	 
	return 0;
}