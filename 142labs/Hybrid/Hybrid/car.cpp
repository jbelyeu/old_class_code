/*
// Test case 1:
Inputs
40 miles per year
3.5 dollars per gallon
50 dollars price of hybrid
60 mpg for hybrid
20 dollar resale for hybrid
30 dollar price for non-hybrid
20 mpg for non-hybrid
20 resale
Gas is main criteria

Results:
Hybrid
3.333 gallons
41.667 dollars total cost

non-hybrid
10 gallons
45 dollars total cost

// Test case 2
Inputs:
40000 miles per year
3.5 dollars per gallon
40000 dollars price of hybrid
60 mpg for hybrid
15000 dollar resale for hybrid
20000 dollar price for non-hybrid
30 mpg for non-hybrid
12000 resale
Gas is main criteria 

Results:
Hybrid 
3333.33 gallons
36666.67 dollar total cost

non-hybrid
6666.67 gallons
31333.3 dollar total cost

//Test case 3
20000 miles per year
2 dollars per gallon
50000 dollars price of hybrid
55 mpg for hybrid
20000 dollar resale for hybrid
12000 dollar price for non-hybrid
18 mpg for non-hybrid
4000 resale
Total is main criteria

Results
non-hybrid
19111.11 dollar total cost
5555.56 gallons


Hybrid
33636.4 dollars total cost
1818.18 gallons

*/

/*
//Extra credit
Hybrid car must have mpg of 166.66666, which took a long time to find.
*/

#include <iostream>
#include <string>

using namespace std;

int main()

{
	double miles_per_year;
	double price_gal_gas;
	double price_hybrid;
	double hybrid_gas_mileage;
	double five_year_resale_hybrid;
	double price_real_car;
	double real_car_gas_mileage;
	double five_year_real_car_resale;
	double num_gal_hybrid = 0;
	double num_gal_real_car = 0;
	double total_cost_hybrid;
	double total_cost_real_car;
	double gas_cost_hybrid;
	double gas_cost_real_car;
	double delta_hybrid_value;
	double delta_real_car_value;
	string criteria;

	cout << "How many miles will you drive per year?" << endl;
	cin >> miles_per_year;
	if (miles_per_year < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> miles_per_year;
		}

	cout << "What is the estimated average cost of gas during the five year period?" << endl;
	cin >> price_gal_gas;
	if (price_gal_gas < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> price_gal_gas;
		}

	cout << "What is the price of the hybrid car?" << endl;
	cin >> price_hybrid;
	if (price_hybrid < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> price_hybrid;
		}
	
	cout << "What is the gas mileage of the hybrid car?" << endl;
	cin >> hybrid_gas_mileage;
	if (hybrid_gas_mileage < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> hybrid_gas_mileage;
		}

	cout << "What is the estimated resale value of the hybrid car after five years?" << endl;
	cin >> five_year_resale_hybrid;
	if (five_year_resale_hybrid < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> five_year_resale_hybrid;
		}

	cout << "What is the price of the standard-fuel car?" << endl;
	cin >> price_real_car;
	if (price_real_car < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> price_real_car;
		}

	cout << "What is the gas mileage of the standard-fuel car?" << endl;
	cin >> real_car_gas_mileage;
	if (real_car_gas_mileage < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> real_car_gas_mileage;
		}

	cout << "What is the estimated resale value of the standard-fuel car after five years?" << endl;
	cin >> five_year_real_car_resale;
	if (five_year_real_car_resale < 0)
		{
			cout << "Only positive values are valid. Please try again." << endl;
			cin >> five_year_real_car_resale;
		}
	cout << "Is your priority gas consumption or total cost? Enter \"Gas\" or \"Total\"" << endl;
	cin >> criteria;

	int num_years = 5;
	num_gal_real_car = miles_per_year*num_years/real_car_gas_mileage;
	num_gal_hybrid = miles_per_year*num_years/hybrid_gas_mileage;

	gas_cost_hybrid = num_gal_hybrid*price_gal_gas;
	gas_cost_real_car = num_gal_real_car*price_gal_gas;

	delta_hybrid_value = price_hybrid - five_year_resale_hybrid;
	delta_real_car_value = price_real_car - five_year_real_car_resale;

	total_cost_real_car = delta_real_car_value + gas_cost_real_car;
	total_cost_hybrid = delta_hybrid_value + gas_cost_hybrid;
					
				
	if (criteria == "Gas")
	{		
		if (num_gal_hybrid < num_gal_real_car)
		{
			cout << "Hybrid" << endl;
			cout << num_gal_hybrid << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;

			cout << "Standard-fuel car" << endl;
			cout << num_gal_real_car << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;
		}

		else if (num_gal_hybrid > num_gal_real_car)
		{
			cout << "Standard-fuel car" << endl;
			cout << num_gal_real_car << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;

			cout << "Hybrid" << endl;
			cout << num_gal_hybrid << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;
		}
		else
		{
			cout << "Equal values" << endl;
			cout << "Standard-fuel car" << endl;
			cout << num_gal_real_car << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;

			cout << "Hybrid" << endl;
			cout << num_gal_hybrid << " Gallons used in five years." << endl;
			cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;
		}
	}

	else if (criteria == "Total")

	{
		if (total_cost_hybrid < total_cost_real_car)
			
			{
				cout << "Hybrid" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;
				cout << num_gal_hybrid << " Gallons used in five years." << endl;
		
				cout << "Standard-fuel car" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;
				cout << num_gal_real_car << " Gallons used in five years." << endl;
			}
		else if (total_cost_hybrid > total_cost_real_car)
			{
				cout << "Standard-fuel car" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;
				cout << num_gal_real_car << " Gallons used in five years." << endl;

				cout << "Hybrid" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;
				cout << num_gal_hybrid << " Gallons used in five years." << endl;
			}
		else 
			{
				cout << "Values are equal." << endl;
				cout << "Standard-fuel car" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_real_car << endl;
				cout << num_gal_real_car << " Gallons used in five years." << endl;

				cout << "Hybrid" << endl;
				cout << "Total cost of operating vehicle is: " << total_cost_hybrid << endl;
				cout << num_gal_hybrid << " Gallons used in five years.\n" << endl;
			}
	}


	system ("pause");
	return 0;
}