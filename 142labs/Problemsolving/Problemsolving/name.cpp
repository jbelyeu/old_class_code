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
	for (int i= 0; i < elements-1; i++)
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
		}
	}
}

vector<string> battle_deleter(vector<string> restaurants, string name)
{
	vector<string> leftovers;
	int elements = restaurants.size();
	for (int i =0; i < elements-1; i++)
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
	restaurants.push_back("COMMUNAL");
	restaurants.push_back("SIZZLER");

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
					//Begin
					int i = 0;
					int j = 1;
						while (restaurants.size() >= 2)
						{
							cout << "Choose your favorite restaurant from these choices: \n";
							cout << endl<< restaurants[i] <<endl << restaurants[j] <<endl;
							string choice;	
							getline(cin, choice);

							if (choice == restaurants[i])
							{
								string name = restaurants[j];
								restaurants = battle_deleter(restaurants, name);
							}
							else if (choice == restaurants[j])
							{
								string name = restaurants[i];
								restaurants = battle_deleter(restaurants, name);
							}
							else 
							{
								cout << "That's not an option." <<endl;
							}
							
							
							if (i == (restaurants.size()-2))
							{
								i=0;
							}
							else
							{
								i++;
							}
							if (j == (restaurants.size()-1))
							{
								j=1;
							}
							else
							{
								j++;
							}
						}
					}
					else 
				{
					cout <<"You have the wrong number of restaurants." << endl;
				}

			}			
	}
}