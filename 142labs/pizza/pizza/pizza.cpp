/* 
Test case 1: 
1 guest, 0% tip: 
0 large
0 medium
1 small
113.097 square inches of pizza
113.097 square inches per person
Total price: $7

Test case 2:
1000 guests, 2% tip:
142 large
2 medium
0 small
45012.7 square inches of pizza
45.0127 square inches per person
Total price: $2150

Test case 3:
37 guests, 73% tip:
5 large
0 medium
2 small
1796.99 square inches of pizza
48.5673 square inches per person
Total price: $152

*/

#include <iostream>
#include <cmath>

using namespace std;

int main ()
{
	int total_guests = 0;
	int lg_pizzas = 0;
	int med_pizzas = 0;
	int small_pizzas = 0;
	int mod_med = 0;
	int mod_small;

	cout << "Enter number of guests. Ex. 23" << endl;
	cin >> total_guests;
	lg_pizzas = total_guests/7;

	mod_med = total_guests%7;
	med_pizzas = mod_med/3;

	mod_small = mod_med%3;
	small_pizzas = mod_small/1;

	cout << "Large pizzas: " << lg_pizzas << endl;
	cout << "Medium pizzas: " << med_pizzas << endl;
	cout << "Small pizzas: "<< small_pizzas << endl;

	double lg_area = 0;
	double med_area = 0;
	double small_area = 0;
	double total_area = 0;
	double area_per_guest = 0;
	int tip_as_percent = 0;
	double price_pizzas = 0;
	double tip_as_decimal = 0.000;
	double total_price = 0;
	double total_tip = 0;
	int total_final_price = 0;

	lg_area = lg_pizzas*314.159;
	med_area = med_pizzas*201.06176;
	small_area = small_pizzas*113.09724;
	total_area = lg_area + med_area + small_area;

	area_per_guest  = total_area/total_guests;

	cout << "Total area of pizza is " << total_area << endl;
	cout << "Total area per guest is " << area_per_guest << endl;

	cout << "What percentage tip would you like to give? __%" << endl;
	cin >> tip_as_percent;
	tip_as_decimal = tip_as_percent*0.01;
	

	price_pizzas = (lg_pizzas*14.68) + (med_pizzas*11.48) + (small_pizzas*7.28);
	total_tip = price_pizzas*tip_as_decimal;
	total_price = price_pizzas + total_tip;
	total_final_price = total_price + 0.5;

	cout << "Total price will be: $" << total_final_price;
	system ("pause");
	return 0;

}