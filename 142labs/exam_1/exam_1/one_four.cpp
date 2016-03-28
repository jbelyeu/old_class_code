/*
Jon Belyeu
80-465-4409
CS 142 Fall Midterm 1 (section 2)
*/

/*
Test Cases:
//Input: 1, -1
 Output: Not a valid amount 
 <menu>

//Input: 1, 0
 Output: Not a valid amount 
 <menu>
 
//Input: 1, 85
 Output: Thank you. Here is your change
 Burton : 1
 Seppi : 1
 Clement : 1
 Child : 1
 Tee-A : 1

//Input: 2, 5
 Output: List:

 Here is your change for 5 dollar(s).
 Burton : 0
 Seppi : 0
 Clement :0 
 Child : 1
 Tee-A : 2

 Here is your change for 4 dollar(s).
 Burton : 0
 Seppi : 0
 Clement :0 
 Child : 1
 Tee-A : 1

 Here is your change for 3 dollar(s).
 Burton : 0
 Seppi : 0
 Clement :0 
 Child : 1
 Tee-A :0

 Here is your change for 2 dollar(s).
 Burton : 0
 Seppi : 0
 Clement :0 
 Child : 0
 Tee-A : 2

 Here is your change for 1 dollar(s).
 Burton : 0
 Seppi : 0
 Clement :0 
 Child : 0
 Tee-A : 1

//Input: 3
 <exit program>

//Input: 4, 85, burton
 Output: Thank you. Here is your change
 Burton : 1
 Seppi : 1
 Clement : 1
 Child : 1
 Tee-A : 1

//Input: 4, 2, clement
 Output: The Clement is worth more than this value in dollars
 <menu>

//Input: 4, 5, child
 Output: Thank you. Here is your change.
 Burton : 0
 Seppi : 0
 Clement : 0
 Child : 1
 Tee-A : 2

 */

#include <iostream>
#include <string>

using namespace std;

int main()
{
	int burton;
	int seppi;
	int clement;
	int child;
	int tee_a;
	double option;
	int usd;
	bool valid = true;

	while (valid)
	{
	
		cout << "Please choose one of the following options:" << endl << endl;
		cout << "1-Exchange US Dollars to C-MASMAS coins\n2-Show a list of equivalence\n3-Exit the program\n4-Exchange US Dollars to C-MASMAS coins in mostly one denomination of coin "<<endl;
		cin >> option;

	
		if (option == 1)
		{
			cout << "Enter the amount of money (U.S. Dollars) you would like to exchange for coins: " << endl;
			cin >> usd;
			if (usd >= 1)
			{
				//convert usd to C-MASMAS coins
				//coinname_c is conversion tool (ex. seppi_c)
				
				burton = usd/44;
				int seppi_c = usd%44;
				seppi = seppi_c/29;
				int clement_c = (seppi_c%29);
				clement = clement_c/8;
				int child_c = clement_c%8;
				child = child_c/3;
				int tee_a_c = child_c%3;
				tee_a = tee_a_c/1;
				
				cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
				//cout change in C-MASMAS coins
				cout << "Burton : " <<burton <<endl;
				cout << "Seppi : " << seppi << endl;
				cout << "Clement : " << clement << endl;
				cout << "Child : " << child << endl;
				cout << "Tee-A : " << tee_a << endl;

				
			}
			else 
			{
				cout << "ERROR: NOT A VALID AMOUNT" << endl;
			}
		}
		

		if (option == 2)
		{
			cout << "ENTER THE AMOUNT OF MONEY YOU WOULD LIKE TO START YOUR LIST WITH: " << endl;
			cin >> usd;
			if (usd > 0)
				{
					cout << "List:" <<endl << endl;
				}

			while (usd >0)
			{
				//Slightly modified operation from part 1
				burton = usd/44;
				int seppi_c = usd%44;
				seppi = seppi_c/29;
				int clement_c = (seppi_c%29);
				clement = clement_c/8;
				int child_c = clement_c%8;
				child = child_c/3;
				int tee_a_c = child_c%3;
				tee_a = tee_a_c/1;
				
				//cout values in descending order.
				cout << "HERE IS YOUR CHANGE FOR " << usd << " DOLLAR(S)." << endl;
				cout << "Burton : " <<burton <<endl;
				cout << "Seppi : " << seppi << endl;
				cout << "Clement : " << clement << endl;
				cout << "Child : " << child << endl;
				cout << "Tee-A : " << tee_a << endl << endl;

				//decrement usd and loop back around
				usd--;
			}
			if (usd > 0)
			{
				cout << "DONE!" <<endl << endl;
			}
			/*else 
			{
				cout << "Invalid" << endl;
			}*/
		}
			if (option == 3)
		{
			return 0;
		}
			if (option == 4)
		{
			string coin;
			cout << "Enter the amount of money (U.S. Dollars) you would like to exchange for coins: " << endl;
			cin >> usd;
			if (usd >= 1 )
			{
				cout << "What coin would you prefer most of?" << endl;
				cin >> coin; 
				if (coin == "burton")
				{
					//verify that denomination is appropriate
					//convert usd to C-MASMAS coins
					//coinname_c is conversion tool (ex. seppi_c)
					
					if (usd >= 44)
					{
						burton = usd/44;
						int seppi_c = usd%44;
						seppi = seppi_c/29;
						int clement_c = (seppi_c%29);
						clement = clement_c/8;
						int child_c = clement_c%8;
						child = child_c/3;
						int tee_a_c = child_c%3;
						tee_a = tee_a_c;
				
						cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
						//cout change in C-MASMAS coins
						cout << "Burton : " <<burton <<endl;
						cout << "Seppi : " << seppi << endl;
						cout << "Clement : " << clement << endl;
						cout << "Child : " << child << endl;
						cout << "Tee-A : " << tee_a << endl;
					}
					else 
					{
						cout << "The Burton is worth more than this value in dollars." << endl;
					}
				}
				if (coin == "seppi") 
				{		
					if (usd >= 29)
					{
						seppi = usd/29;
						int clement_c = (usd%29);
						clement = clement_c/8;
						int child_c = clement_c%8;
						child = child_c/3;
						int tee_a_c = child_c%3;
						tee_a = tee_a_c;
				
						cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
						//cout change in C-MASMAS coins
						cout << "Burton : 0" <<endl;
						cout << "Seppi : " << seppi << endl;
						cout << "Clement : " << clement << endl;
						cout << "Child : " << child << endl;
						cout << "Tee-A : " << tee_a << endl;
					}
					else 
					{
						cout << "The Seppi is worth more than this value in dollars." << endl;
					}

				}
				if (coin == "clement") 
				{
					if (usd >= 8)
					{
						clement = usd/8;
						int child_c = usd%8;
						child = child_c/3;
						int tee_a_c = child_c%3;
						tee_a = tee_a_c;
				
						cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
						//cout change in C-MASMAS coins
						cout << "Burton : 0" <<endl;
						cout << "Seppi : 0" << endl;
						cout << "Clement : " << clement << endl;
						cout << "Child : " << child << endl;
						cout << "Tee-A : " << tee_a << endl;
					}
					else 
					{
						cout << "The Clement is worth more than this value in dollars." << endl;
					}
				}
				
				if (coin == "child") 
				{
					if (usd >= 3)
					{
						child = usd/3;
						int tee_a_c = usd%3;
						tee_a = tee_a_c;
				
						cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
						//cout change in C-MASMAS coins
						cout << "Burton : 0" <<endl;
						cout << "Seppi : 0" << endl;
						cout << "Clement : 0" << endl;
						cout << "Child : " << child << endl;
						cout << "Tee-A : " << tee_a << endl;
					}
					else 
					{
						cout << "The Child is worth more than this value in dollars." << endl; //This looks and sounds very strange
					}
				}
			if (coin == "tee-a") 
				{
					if (usd >=1)
					{
						tee_a = usd;
				
						cout << "THANK YOU. HERE IS YOUR CHANGE." << endl;
						//cout change in C-MASMAS coins
						cout << "Burton : 0" <<endl;
						cout << "Seppi : 0"  << endl;
						cout << "Clement : 0" << endl;
						cout << "Child : 0" << endl;
						cout << "Tee-A : " << tee_a << endl;	
					}
					else 
					{
						cout << "The Tee-A is worth more than this value in dollars." << endl;
					}
				}
			if (coin != "burton" && coin != "seppi" && coin != "child" && coin != "tee-a" && coin != "clement")
				{
					cout << "ERROR: NOT VALID" << endl;
				}
			}
		}
		else if (option != 1 && option != 2 && option != 3)
		{
			cout << "NOT A VALID CHOICE" << endl;
			
		}
	}
		
	system ("pause");
	return 0;

}