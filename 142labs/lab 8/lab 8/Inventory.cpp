/*
Test 1
Input: 3, Camaro, Purple, 2000, 1, 2
Expected output: 
Camaro
Purple 
$2000

$8000

Input: 4, Camaro, 1, 2
Expected output
Nothing (nothing in inventory)
$10000

Test 2
Input 6, cars1.txt, 1, 2
Expected output
Prompt for name of file
Return to menu
Contents of cars1.txt
balance plus balance of cars1.txt

Test 3
Input 6, cars2.txt, 7, whichever
expected output
Prompt for name of file
Return to menu
Prompt for name of file
//Create file whichever.txt
*/




#include <iostream>
#include "Car.h"
#include <fstream>
#include <vector>
#include <string>
#include <iomanip>

using namespace std;

int car_specs (string& name, string& color, int price)
{
	cout << "Please input name, color, and price of car:" << endl;
	cin >> name; cin >> color; cin >> price;
	return price;
}

int search_Inventory (vector<Car>& Inventory, string& name)
{
	int position = -1;
	
	bool not_found = true;
	for (int i = 0; i < Inventory.size(); i++)
	{
		if (Inventory[i].getName() == name)
		{
			position = i;
			return position;
			not_found = false;
		}
	}
	if (not_found)
	{
			return position;
	}
}
void  delete_car(vector<Car>& Inventory, int position)
{	
	int end = (Inventory.size()-1);
	Inventory[position] = Inventory[end];
	Inventory.pop_back();
	
	cout << "This is size of inventory after " << Inventory.size() << endl;
}

int main()
{
	double balance = 10000.00;
	bool run = true;
	bool present = false;
	int option = 0;
	double position = -1;
	vector<Car> Inventory;
	string name;
	string color;
	double price = 0.0;

	while (run)
	{
		cout << "Select option. \n(1) to show inventory \n(2) to show cash balance \n(3) to buy a car \n(4) to sell a car \n(5) to paint a car \n(6) to load a file \n(7) to save to a file \n(8) to quit program" << endl;
		
		cin >> option;

		if (option == 1)//prints name, color, dollar value of each car in Inventory
		{
			for (int i = 0; i < Inventory.size(); i++)
			{
					cout << Inventory[i].toString() << endl;
			}
		}
		else if (option == 2)//prints dollar balance
		{
			cout <<setprecision(2) << fixed << "$" << balance << endl;
		}
		else if (option == 3)//adds car to Inventory, subtracts value of car from balance
		{
			
			cout << "Enter name, color, and price of car." << endl;
			cin >> name; cin >> color; cin >> price;
			//check balance
			
			if (balance >= price)
			{				
				position = search_Inventory(Inventory, name); // int position is unecessary here but simplifies code later; bool present is updated by reference
				
				if (position >= 0) 
				{present = true;}				

				if (present == true)
				{
					cout << "You already entered that vehicle." << endl;
					present = false;
				}
				else//create car, add to Inventory
				{
					Car NewCar = Car(name, color, price);
					Inventory.push_back(NewCar);
					balance -= price;
				}				
			}
			
			else 
			{
				cout << "Insufficient funds!" <<endl;
			}
			position = -1;
		}
		else if (option == 4)//removes car from Inventory, adds value of car to balance
		{
			cout << "Enter name of car." << endl;
			cin >> name;
			if (Inventory.size() !=0)
			{
				position = search_Inventory(Inventory, name);
				if (position >= 0) { present = true;}

				if (present == false)
				{
					cout << "Car not found." << endl;
				}
				else //call delete_car function to remove car from inventory
				{
					price = Inventory[position].getPrice();
					balance += price;
					delete_car(Inventory, position);
				}
			}
			else 
			{
				cout << "There are no cars in inventory." << endl;
			}
		}
		else if (option == 5)//paints car, increase value of car
		{
			cout << "Select car to paint." << endl;
			cin >> name;
			position = search_Inventory(Inventory, name);
			if (position >= 0) 
			{
				string new_color;
				cout << "Enter new color." << endl;
				cin >> new_color;
				Inventory[position].paint(new_color);
			}
			else 
			{
				cout << "That car does not exist" << endl;
			}

		}
		else if (option == 6)//adds balance of a file and cars/beans (plus their colors) in that file to inventory
		{
			double new_bal;
			string file;
			ifstream NewFile;
			cout << "What file would you like to open?" << endl;
			cin >> file;

			NewFile.open(file);			
			NewFile >> new_bal;
			balance += new_bal;
			
			while (!NewFile.eof())
			{
				string name_reader;
				string color_reader;
				double value;
				NewFile >> name_reader;
				NewFile >> color_reader;
				NewFile >> value;
				Car NewCar = Car(name_reader, color_reader, value);
				Inventory.push_back(NewCar);
			}
			NewFile.close();
		}
		else if (option == 7)//saves Inventory to a file
		{
			cout << "To which file would you like to save?  \n";
			string file;
			cin >> file;

			ofstream toSave;
			toSave.open(file);
			toSave << balance <<endl;
			for (int i =0; i< Inventory.size(); i ++)
			{
				toSave << Inventory[i].getName() << "\t";
				toSave << Inventory[i].getColor() << "\t" ;
				toSave << Inventory[i].getPrice() << "\n";
			}
			toSave.close();
		}
		else if (option == 8) //exits program
		{
			run = false;
		}


	}
//system ("pause");
return 0;
}