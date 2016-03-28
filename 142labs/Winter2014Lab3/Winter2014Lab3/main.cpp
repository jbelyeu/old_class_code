#include <iostream>
#include <string>
using namespace std;

int main()
{
	cout << "Welcome to this very useful program! Please enter the number of miles you expect to drive per year in order to begin." <<endl;
	int mileage;
	cin >> mileage;
	if (mileage <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> mileage;
	}

			
	cout << "Estimate the cost of gas during that year." <<endl;
	double gasCost;
	cin >> gasCost;
	if (gasCost <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> gasCost;
	}

	cout << "Enter Purchase price of Hybrid car." <<endl;
	int hybridPrice;
	cin >> hybridPrice;
	if (hybridPrice <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> hybridPrice;
	}

	cout << "Enter gas mileage of the hybrid car." <<endl;
	int hybridGasMileage;
	cin >> hybridGasMileage;
	if (hybridGasMileage <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> hybridGasMileage;
	}

	cout << "Enter 5-year resale value of the hybrid car." <<endl;
	int resaleHybrid;
	cin >> resaleHybrid;
	if (resaleHybrid <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> resaleHybrid;
	}

	cout <<"Now enter the purchase price of gas car." <<endl;
	int carPrice;
	cin >> carPrice;
	if (carPrice <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> carPrice;
	}

	cout << "Enter gas mileage of the gas car." <<endl;
	int carGasMileage;
	cin >> carGasMileage;
	if (carGasMileage <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> carGasMileage;
	}

	cout << "Enter 5-year resale value of the car." << endl;
	int carResale;
	cin >> carResale;
	if (carResale <= 0)
	{
		cout << "Invalid entry. Please re-enter." <<endl;
		cin >> carResale;
	}

	cout << "State preference: economy of gas or price?" <<endl;
	string preference;
	cin >> preference;
	int hybridGas = (mileage/hybridGasMileage);
	cout << mileage <<"/"<<hybridGasMileage<<"="<<hybridGas;
	int carGas = (mileage/carGasMileage);
	cout << endl << carGas;

	if (preference == "gas")
	{
		
		if (hybridGas < carGas)
		{
			cout << "Hybrid wins, with gas consumption of " << hybridGas << " gallons." <<endl;
			cout << "Total cost of car is " << ((hybridGas*gasCost)+(hybridPrice-resaleHybrid));
			cout << "Gas car consumes " <<carGas <<"gallons and costs a total of $" << ((carGas*gasCost)+(carPrice-carResale)) <<endl;
		}
		else if (carGas <hybridGas)
		{
			cout << "Gas car wins, with gas consumption of " << carGas << " gallons" <<endl;
			cout << "Total cost of car is " << ((carGas*gasCost)+(carPrice-carResale));
			cout << "Hybrid consumes " <<hybridGas <<"gallons and costs a total of $" << ((hybridGas*gasCost)+(hybridPrice-resaleHybrid)) <<endl;
		}
		else 
		{
			cout << "Ze are ze same! Both consume " <<hybridGas <<" gallons." <<endl;
			cout << "Total cost of gas car is " << ((carGas*gasCost)+(carPrice-carResale));
			cout << "Total cost of hybrid is " << ((hybridGas*gasCost)+(hybridPrice-resaleHybrid));
		}


	}
	else if (preference == "price")
	{
		int hybridTotCost = ((hybridGas*gasCost)+(hybridPrice-resaleHybrid));
		int carTotPrice = ((carGas*gasCost)+(carPrice-carResale));

		if (hybridTotCost > carTotPrice)
		{
			cout << "Gas car wins, with a total cost of $" <<carTotPrice << " and consumes " << carGas << " gallons" <<endl;
			cout << "Hybrid costs a total of $" << hybridTotCost << " and consumes " << hybridGas <<" gallons." <<endl;
		}
		else if (hybridTotCost < carTotPrice)
		{
			cout << "Hybrid wins, with a total cost of $" << hybridTotCost <<" and consumes " << hybridGas << " gallons" << endl;
			cout << "Gas car costs a total of $" << carTotPrice << " and consumes " << carGas <<" gallons." <<endl;
		}
		else 
		{
			cout << "'E lo mimo! Both cost $" << hybridTotCost <<endl;
			cout << "Hybrid consumes " <<hybridGas << " gallons, and the gas car consumes " << carGas << "gallons" <<endl;
		}
	}

	else
	{
		cout << "Error. that was not an option. Please retry." <<endl;
		cin >> preference;
	}




	system ("pause");
	return 0;
}


