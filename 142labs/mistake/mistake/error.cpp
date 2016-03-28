#include <iostream>
#include <string>

using namespace std;

int main ()

{
	string start;
	cout << "Menu:\nWelcome to the knockoff of Plinko! You may choose to play a single chip (type \"single\"), play multiple chips (type \"multiple\"), or leave the game (type \"exit\")" << endl;
	cin >> start;

	if (start == "exit")
	{
		cout << "Goodbye";
		system ("pause");
		return 0;

	}
}