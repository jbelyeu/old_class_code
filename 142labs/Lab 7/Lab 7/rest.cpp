/*
Test 1:
Input 5
Output:
Choose your favorite restaurant from these choices:

OLIVE  GARDEN
BRICK OVEN
INPUT BRICK OVEN
OUTPUT
Choose your favorite restaurant from these choices:

GLORIA'S LITTLE ITALY
LA DOLCE VITA
INPUT LA DOLCE VITA
OUTPUT
Choose your favorite restaurant from these choices:

RUBY RIVER
OLD TOWNE GRILL
INPUT RUBY RIVER
OUTPUT
Choose your favorite restaurant from these choices:

MAGLEBY'S FRESH
COMMUNAL
INPUT COMMUNAL
OUTPUT
Choose your favorite restaurant from these choices:

T.G.I FRIDAY'S
LOTUS GARDEN RESTAURANT
INPUT T.G.I FRIDAY'S
OUTPUT
Choose your favorite restaurant from these choices:

BOMBAY HOUSE
ROMANO'S MACARONI GRILL
INPUT BOMBAY HOUSE
OUTPUT
Choose your favorite restaurant from these choices:

SIZZLER
EL AZTECA MEXICAN TACO SHOP
INPUT SIZZLER
OUTPUT
Choose your favorite restaurant from these choices:

RED LOBSTER
L&L HAWAIIAN BARBECUE
INPUT RED LOBSTER
OUTPUT
Choose your favorite restaurant from these choices:

BRICK OVEN
LA DOLCE VITA
INPUT BRICK OVEN
Choose your favorite restaurant from these choices:

RUBY RIVER
COMMUNAL
INPUT COMMUNAL
Choose your favorite restaurant from these choices:

T.G.I FRIDAY'S
BOMBAY HOUSE
INPUT BOMBAY HOUSE
Choose your favorite restaurant from these choices:

SIZZLER
RED LOBSTER
INPUT SIZZLER
Choose your favorite restaurant from these choices:

BRICK OVEN
COMMUNAL
INPUT COMMUNAL
OUTPUT
Choose your favorite restaurant from these choices:

BOMBAY HOUSE
SIZZLER
INPUT SIZZLER
OUTPUT 
Choose your favorite restaurant from these choices:

COMMUNAL
SIZZLER
INPUT SIZZLER
OUTPUT
The winner is SIZZLER!
(return to menu)

Test 2
Input 2
Output What restaurant would you like to add?
Input this one
Output this one is now in the list
Input 1
Output 
OLIVE GARDEN, BRICK OVEN, GLORIA'S LITTLE ITALY, LA DOLCE VITA, RUBY RIVER, OLD TOWNE GRILL, MAGLEBY'S FRESH, COMMUNAL, T.G.I FRIDAY'S, LOTUS GARDEN RESTAURANT, BOMBAY HOUSE, ROMANO'S MACARONI GRILL, SIZZLER, EL AZTECA MEXICAN TACO SHOP, RED LOBSTER, L&L HAWAIIAN BARBECUE, this one

Test 3
Input 3
Output What restaurant would you like to remove?
Input COMMUNAL
Output COMMUNAL has been removed from the list.
Input 1
Output OLIVE GARDEN, BRICK OVEN, GLORIA'S LITTLE ITALY, LA DOLCE VITA, RUBY RIVER, OLD TOWNE GRILL, MAGLEBY'S FRESH, T.G.I FRIDAY'S, LOTUS GARDEN RESTAURANT, BOMBAY HOUSE, ROMANO'S MACARONI GRILL, SIZZLER, EL AZTECA MEXICAN TACO SHOP, RED LOBSTER, L&L HAWAIIAN BARBECUE

*/

#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include <vector>
#include <cmath>

using namespace std;

void show_restaurants(vector<string> restaurants)
{
	//shows the list of restaurants
	int elements = restaurants.size();
	for (int i = 0; i < elements ; i++)
	{
		cout << restaurants[i];
		if (i < elements-1)
		{ 
			cout <<", ";
		}
	}
	cout << endl << endl;
}

vector<string> add_restaurant(vector<string> restaurants)
{
	int elements = restaurants.size();
	string name;
	string other_name;
	getline(cin, name);
	for (int i= 0; i < elements-1; i++)//adds restaurant to vector
	{
		if (restaurants[i] == name)
		{
			cout << "That restaurant is already listed" << endl;
			other_name = name;
			if (i == (elements - 1))
			{
				return restaurants;
			}
		}
	}
	if (other_name != name)
	{
	restaurants.push_back(name);
	cout << name << " is now in the list." << endl;
	return restaurants;
	}
	else 
	{return restaurants;}
}


vector<string> deleter(vector<string> restaurants, string name)
{
	int elements = restaurants.size();
	for (int i= 0; i < elements; i++)
	{
		if (restaurants[i] == name)
		{
			restaurants[i] = restaurants[elements-1];
			restaurants.pop_back();
			cout << name << " has been removed from the list." <<endl;
			return restaurants;
		}	
		else if (i== elements-1)
		{
		cout << "That restaurant is not in the list" << endl;
		return restaurants;
		}
	}
}

vector<string> battle_deleter(vector<string> restaurants, string name)
{
	vector<string> leftovers;
	int elements = restaurants.size();
	for (int i =0; i < elements; i++)
	{
		if (restaurants[i] != name)
		{
			leftovers.push_back(restaurants[i]);
		}

	}
	return leftovers;
}

vector<string> remove_restaurant(vector<string> restaurants)
{
	// removes restaurant from vector
	
	string name;
	getline(cin, name);	
	restaurants = deleter(restaurants, name);
	return restaurants;	
}
	
vector<string> shuffle(vector<string> restaurants)
{
	srand(time(0)); 
	int elements = restaurants.size();
	for (int i = 0; i < 1000; i++)
	{
		int first_element = rand() % elements;
		int second_element = rand() % elements;
		string temp;

		temp = restaurants[first_element];
		restaurants[first_element] = restaurants[second_element];
		restaurants[second_element] = temp;
	}	
	return restaurants;
}

int main()

{
	vector<string> restaurants;
	restaurants.push_back("OLIVE GARDEN");
	restaurants.push_back("BRICK OVEN");
	restaurants.push_back("GLORIA'S LITTLE ITALY");
	restaurants.push_back("LA DOLCE VITA");
	restaurants.push_back("RUBY RIVER");
	restaurants.push_back("OLD TOWNE GRILL");
	restaurants.push_back("MAGLEBY'S FRESH");
	restaurants.push_back("COMMUNAL");
	restaurants.push_back("T.G.I FRIDAY'S");
	restaurants.push_back("LOTUS GARDEN RESTAURANT");
	restaurants.push_back("BOMBAY HOUSE");
	restaurants.push_back("ROMANO'S MACARONI GRILL");
	restaurants.push_back("SIZZLER");
	restaurants.push_back("EL AZTECA MEXICAN TACO SHOP");
	restaurants.push_back("RED LOBSTER");
	restaurants.push_back("L&L HAWAIIAN BARBECUE");
	
	string option;
	bool proceed = true;
	
	while (proceed)
	{
		cout << "Welcome to LUCHA DE LOS LUGARES! What would you like to do? Would you like to: (1) See all restaurants, (2) Add a restaurant to the list, (3) Remove a restaurant from the list, (4) Shuffle restaurants, (5) Begin the tournament, or (6) leave program?" <<endl;
		getline(cin, option);
		if (option == "6") 
			{
			return 0;
			}
		
		else if (option == "1")
			{
				//display names of restaurants
				show_restaurants(restaurants);
			}
		else if (option == "2")
			{
				//add a restaurant with a function
				cout << "What restaurant would you like to add?" << endl;
				restaurants = add_restaurant(restaurants);
			}
		else if (option == "3")
			{
				//delete a restaurant with a function
				cout << "What restaurant would you like to remove?" << endl;
				restaurants = remove_restaurant(restaurants);
			}
		else if (option == "4")
			{
				// shuffle with a function
				restaurants = shuffle(restaurants);
				cout << "Restaurants are now shuffled." << endl;
			}
		else if (option == "5")
			{
			// begin tournament
				double elements = restaurants.size();
				while (elements > 2)
				{ 
					elements = elements/2;
				}
				if (elements == 2)
					{
						int i = 0;
						int j = 1;
						string name;
						string winner;
						int match = 1;
						int round = 1;
				
						int tot_matches = ((restaurants.size())/2);
						int tot_rounds =0;
						int rest_names = restaurants.size();
						while (rest_names >2)
						{								
							rest_names = rest_names/2;
							tot_rounds++;
						}

						cout << "Match " <<match << "/" <<  tot_matches << ", Round " << round << "/" << tot_rounds << endl;
						match ++;

						while (restaurants.size() >= 2)
						{
							bool next = true;
							cout << "Choose your favorite restaurant from these choices: \n";
							cout << endl<< restaurants[i] <<endl << restaurants[j] <<endl;
							string choice;	
							getline(cin, choice);
							
							if (choice == restaurants[i])
							{
								name = restaurants[j];
								winner = restaurants[i];
								restaurants = battle_deleter(restaurants, name);
								cout << endl << "Match " <<match << "/" <<  tot_matches << ", Round " << round << "/" << tot_rounds << endl;
							}
							else if (choice == restaurants[j])
							{
								name = restaurants[i];
								winner = restaurants[j];
								restaurants = battle_deleter(restaurants, name);
								cout << endl << "Match " <<match << "/" <<  tot_matches << ", Round " << round << "/" << tot_rounds << endl;
							}
							else 
							{
								cout << "That's not an option." <<endl;
								next = false;
							}							
								
							if (next == true && i >= (restaurants.size()-2))
							{
								i=0;
								j=1;
								round ++;
								match = 1;
								tot_matches= tot_matches/2;
								
							}
							else if (next == true && i < (restaurants.size()-2))
							{
								i++;
								j++;
								match++;
							}
							
							
						}
						cout << "The winner is " << winner <<"!" <<endl;
					}
					else 
				{
					cout <<"You have the wrong number of restaurants." << endl;
				}

			}	
	}
}