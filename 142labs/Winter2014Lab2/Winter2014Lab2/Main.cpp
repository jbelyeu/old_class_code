#include <iostream>

using namespace std;

int main()
{
	
	cout << "How many guests will there be?"<<endl;
	int guests = 0;
	cin >> guests;

	int larges = guests/7;
	int remainder = guests%7;

	int mediums = remainder/3;
	remainder = (remainder%3);

	int smalls = remainder;

	cout << "You should order " << larges <<" large pizza(s), " << mediums <<" medium pizza(s), and " << smalls <<" small pizza(s)" <<endl;
	
	double totalArea = larges*100*3.14159 + mediums*64*3.14159 + smalls*36*3.14159;

	double PerPerson = totalArea/guests;
	
	cout << "The total area of pizza purchased will be " <<totalArea << ", while the area per person will be " << PerPerson <<"."<<endl;

	cout << "What percent tip would you like to give? Will you act more like a college student or more like a decent human being?" <<endl;
	int tip = 0;
	cin >> tip;

	double price = ((larges*14.68) + (mediums*11.48) + (smalls*7.28));
	double totCost = price + price*tip/100;

	int finalCost = totCost+0.5;

	cout<< "Total cost of all pizzas will be $" <<finalCost <<"."<<endl;














	system ("pause");
	return 0;
}
