/* This is CS 142 Winter 2014 Midterm 2
	Chien-Wei Chao , 001								*/

#include <iostream>     
#include <cmath>
#include <sstream>
#include<vector>
#include <iomanip>
#include <string>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include <stdio.h>
using namespace std;
#include "printScore.h"
//#include "printScore.cpp"
void check_highest_score(vector<int> &score_each_game, int &highest_score)
{
	//open file 
	ifstream in;
	int highest_score1;
	in.open("highest.txt");
	if (!in)
	{
		cout << "open input file failed!" << endl;
	}
	else
	{
		
		in >> highest_score1;
	}
	for (int i = 0; i <score_each_game.size(); i++)
	{
		if (score_each_game[i]> highest_score1)//strike
		{
			ofstream out_file;
			out_file.open("highest.txt");
			out_file << score_each_game[i] << endl;
			cout << endl;
			cout << "Congradulation!, you got highest score = " << score_each_game[i] << endl;
			cout << endl;
			highest_score = score_each_game[i];
		}
	
	}
	

	
}
string number_to_string(int Number)
{

	string Result;          // string which will contain the result

	ostringstream convert;   // stream used for the conversion

	convert << Number;      // insert the textual representation of 'Number' in the characters in the stream

	Result = convert.str(); // set 'Result' to the contents of the stream
	return Result;
}
void integer_check(bool &integer, double number)
{

	int number1=number;

	if (number1 == number)
	{
		integer = true;
	}
	else if (number1 != number)
	{
		integer = false;
	}

}
int number_games_needed(string playerName)
{
	string frames;
	int games;
	int stop2 = 0;



	//set up looping for users if the number of games is less than 1.
	while (stop2 != 6)
	{
		cout << endl;
		cout << "How many frames would you like to play ?" << endl;
		getline(cin, frames);
		
		double frames_value;          //number which will contain the result
		istringstream convert(frames); // stringstream used for the conversion constructed with the contents of 'frames_value' 
		// ie: the stream will start containing the characters of 'frames_value'

		if (!(convert >> frames_value)) //give the value to 'frames_value' using the characters in the stream
			frames_value = 0;             //if that fails set 'frames_value' to 0
		

		bool integer=true;
		// check if the number of frames is integer or not
		integer_check(integer, frames_value);

		games = frames_value / 10;

		if (integer == false)
		{
			cout << "Please type integer number of frames" << endl;
		}
		else if (integer == true)
		{

			stop2 = 6;
			if (frames_value < 10)
			{
				cout << endl;
				cout << "frames should be at least 10, Please retype the number of frames you need" << endl;
				cout << endl;
				stop2 = 0;
			}
			else
			{
				cout << playerName << "  will play " << games << " games." << endl;
			}

		}

		

	
	}

	return games;
}
void  awards_statistics(vector<int> &score_each_game, int number_games, int strike_count, int spare_count, int getter_count, int highest_score, bool &turkey, bool &keep_your_day_job)
{
	cout << "STATISTICS / AWARDS: " << endl;
	double total_ = 0;
	for (int i = 0; i < number_games; i++)
	{
		cout << "Score for Game " << i+1 << " :" << score_each_game[i] << endl;
		total_ = total_ + score_each_game[i];
	}

	cout << "Average score for " << number_games << " : " << total_ / number_games << endl;

	cout << "Total number of strikes : " << strike_count << endl;
	cout << "Total number of spares : " << spare_count << endl;
	cout << "Total number of gutter balls : " << getter_count << endl;
	
	for (int i = 0; i < number_games; i++)
	{
		if (score_each_game[i] >= 200)
		{
			cout << "Congratulations, you won the " << 200 << " Club award!" << endl;
		}
		if (score_each_game[i] == 300)
		{
			cout << "Congratulations, you won a perfect game " << endl;
		}
		if (turkey ==true)
		{
			cout << "Congratulations, you won the Turkey award!"<<endl;
		}
		if (keep_your_day_job == true)
		{
			cout << "Congratulations, you need to practice more!" << endl;
		}
		if (score_each_game[i] ==  highest_score)
		{
			cout << "Congratulations, you won the highest score " << endl;
		}
	}
}

void frame_score(vector<string>  &print_score, string score1, int &stop, double roll_score1, string playerName, int &pin_remaining, int &sum, bool &strike, bool &strikeframe, bool &spare, bool &spareframe, int &frames, int type, string score2, int a[][11], int &continue_strikes, bool &turkey, bool &keep_your_day_job)
{
	frames = frames + 1;
	double roll_score2;
	
	if (score1 == "X")// if strike
	{
		if (type == 2)
		{
		cout << "Roll " << 2 << " of frame " << 1 << " : " << score2 << endl;
		}
		else if (type == 3)
		{
		cout << "Roll " << 2 << " of frame " << 1 << " : " << score2<< endl;
		}
		else if (type == 1)
		{
			score2 = "-";
		}
		strike = true;
		roll_score1 = 10;
		roll_score2 = 0;
		stop = 6;

		pin_remaining = 0;
	}
	else
	{
		strike = false;
			if (type == 1)
			{
				int stop2 = 0;
				while (stop2 != 6)
				{
					cout << "Roll " << 2 << " of frame " << frames << ":" << endl;
					getline(cin, score2);
					//.........................................................................................					

					istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
					// ie: the stream will start containing the characters of 'frames_value'
					if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
						roll_score2 = 0;             //if that fails set 'frames_value' to 0

					bool integer = true;
					// check if the number of frames is integer or not
					integer_check(integer, roll_score2);
					//.....................................................................................................
					if (integer == false)
					{
						cout << "Please type integer number of roll score" << endl;
					}
					else if (integer == true)
					{
						if ((roll_score2 <= 0 || roll_score2 >9) && ((score2 != "G" && score2 != "F" && score2 != "/")))
						{
							cout << "Please type number of roll score between 1 to 9 or right syntax" << endl;
						}
						else if (roll_score1 + roll_score2 >= 10)
						{
							cout << "the number of remaining pins is negative or zero(if so, please type /))" << endl;
						}
						else
						{
							stop2 = 6;
							stop = 6;
						}
					}
				}
			}
			else if (type == 2)
			{
				cout << "Roll " << 2 << " of frame " << 1 << " : " << score2 << endl;
				istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
					roll_score2 = 0;             //if that fails set 'frames_value' to 0
			}
			else if (type == 3)
			{
				cout << "Roll " << 2 << " of frame " << 1 << " : " << score2 << endl;
				istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
					roll_score2 = 0;             //if that fails set 'frames_value' to 0
			}
	}

	if (score2 == "/")  // spare happen 
	{
		spare = true;
		roll_score2 = 10 - roll_score1;
	}
	else
	{
		spare = false;
	}

	
		if (continue_strikes >= 2) // when strike happen over twice continually
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				turkey = true;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 2] = 20 + a[1][frames] + sum;
				sum = a[3][frames - 2];

				string result = number_to_string(a[3][frames - 2]);
				print_score[(frames - 2) * 3 - 1] = result;
			}
			else if (spare == true)
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 2] = 20 + a[1][frames] + sum;

				string result1 = number_to_string(a[3][frames - 2]);
				print_score[(frames - 2) * 3 - 1] = result1;

				a[3][frames - 1] = 10 + a[1][frames] + a[2][frames] + a[3][frames - 2];
				sum = a[3][frames - 1];

				string result2 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result2;
			}
			else
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 2] = 20 + a[1][frames] + sum;

				string result1 = number_to_string(a[3][frames - 2]);
				print_score[(frames - 2) * 3 - 1] = result1;

				a[3][frames - 1] = 10 + a[1][frames] + a[2][frames] + a[3][frames - 2];

				string result2 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result2;

				a[3][frames] = a[3][frames - 1] + a[1][frames] + a[2][frames];
				sum = a[3][frames];
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
			}
		}
		else if (spareframe == true)
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + sum;
				sum = a[3][frames - 1];
				string result = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result;
			}
			else if (spare == true)
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + sum;
				sum = a[3][frames - 1];

				string result = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result;
			}
			else
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + sum;

				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;

				a[3][frames] = a[3][frames - 1] + a[1][frames] + a[2][frames];
				sum = a[3][frames];
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
			}
		}
		else if (strikeframe == true)
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
			}
			else if (spare == true)
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + a[2][frames] + sum;
				sum = a[3][frames - 1];
				string result = number_to_string(sum);
				print_score[(frames - 1) * 3 - 1] = result;

			}
			else
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + a[2][frames] + sum;
				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;
				a[3][frames] = a[3][frames - 1] + a[1][frames] + a[2][frames];
				sum = a[3][frames];
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
			}
		}
		else
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
			}
			else if (spare == true)
			{
				continue_strikes = 0;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				print_score.push_back(score1);
				print_score.push_back(score2);
				print_score.push_back("#");
			}
			else
			{
				continue_strikes = 0;
				print_score.push_back(score1);
				print_score.push_back(score2);
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames] = a[3][frames - 1] + a[1][frames] + a[2][frames];
				sum = a[3][frames];
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
			}
		}

	
	if (frames == 10&&type==1)
	{
		if (strikeframe == true)
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				a[1][frames] = roll_score1;
				

				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;
				print_score.push_back(score1);

				cout << "Roll " << 2 << " of frame " << 10 << ":" << endl;
				getline(cin, score2);
				istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
					roll_score2 = 0;             //if that fails set 'frames_value' to 0
				if (score2 == "X")
				{
					roll_score2 = 10;
				}
				a[2][frames] = roll_score2;
				print_score.push_back(score2);

				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				//istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}


				a[3][frames - 1] = 10 + a[1][frames] + a[2][frames]+sum;
				sum = a[3][frames - 1];

				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
				a[4][frames] = a[1][frames] + a[2][frames] + a[3][frames] + sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];

			}
			else if (spare == true)
			{
				continue_strikes = 0;
				continue_strikes = 0;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 20 + sum;
				sum = a[3][frames - 1];

				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;
				print_score.push_back(score1);
				print_score.push_back(score2);


				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}
				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);

				a[4][frames] = a[3][frames] + a[1][frames] + a[2][frames] + sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];
			}


		}
		else if (spareframe==true)
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				a[3][frames - 1] = 10 + a[1][frames] + sum;
				sum = a[3][frames - 1];

				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;
				print_score.push_back(score1);

				cout << "Roll " << 2 << " of frame " << 10 << ":" << endl;
				getline(cin, score2);
				istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
					roll_score2 = 0;             //if that fails set 'frames_value' to 0
				if (score2 == "X")
				{
					roll_score2 = 10;
				}
				a[2][frames] = roll_score2;
				print_score.push_back(score2);

				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				//istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}
				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
				a[4][frames] = a[1][frames] + a[2][frames] + a[3][frames] + sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];
			}
			else if (spare==true)
			{
				continue_strikes = 0;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;

				a[3][frames - 1] = 10 + a[1][frames] + sum;
				sum = a[3][frames - 1];

				string result1 = number_to_string(a[3][frames - 1]);
				print_score[(frames - 1) * 3 - 1] = result1;
				print_score.push_back(score1);
				print_score.push_back(score2);
			


				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}
				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);

				a[4][frames] = a[3][frames] + a[1][frames] + a[2][frames] + sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];
			}
		}
		else
		{
			if (strike == true)
			{
				continue_strikes = continue_strikes + 1;
				print_score.push_back(score1);
				a[1][frames] = roll_score1;
				cout << "Roll " << 2 << " of frame " << 10 << ":" << endl;
				getline(cin, score2);
				istringstream convert(score2); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score2)) //give the value to 'frames_value' using the characters in the stream
					roll_score2 = 0;             //if that fails set 'frames_value' to 0
				if (score2 == "X")
				{
					roll_score2 = 10;
				}
				a[2][frames] = roll_score2;
				print_score.push_back(score2);

				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				//istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}
				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
				a[4][frames] = a[1][frames] + a[2][frames] + a[3][frames] + sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];


			}
			else if (spare == true)
			{
				continue_strikes = 0;
				a[1][frames] = roll_score1;
				a[2][frames] = roll_score2;
				print_score.push_back(score1);
				print_score.push_back(score2);
				
				string score3;
				double roll_score3;
				cout << "Roll " << 3 << " of frame " << 10 << ":" << endl;
				getline(cin, score3);
				istringstream convert(score3); // stringstream used for the conversion constructed with the contents of 'frames_value' 
				// ie: the stream will start containing the characters of 'frames_value'
				if (!(convert >> roll_score3)) //give the value to 'frames_value' using the characters in the stream
					roll_score3 = 0;             //if that fails set 'frames_value' to 0
				if (score3 == "X")
				{
					roll_score3 = 10;
				}
				a[3][frames] = roll_score3;
				string result = number_to_string(a[3][frames]);
				print_score.push_back(result);
				a[4][frames] = a[1][frames] + a[2][frames] + a[3][frames]+sum;
				result = number_to_string(a[4][frames]);
				print_score.push_back(result);
				sum = a[4][frames];
			}
		}
	}
	




	if (spare == true)
	{
	spareframe = true;
	}
	else if (strike == true)
	{
	strikeframe = true;
	}
	else
	{
		spareframe = false;
		strikeframe = false;
	}	


}

void input_user(string input_options, string playerName, int type, vector<int> &score_each_game, int &strike_count, int &spare_count, int &getter_count, bool &turkey, bool &keep_your_day_job)
{
	cout << "Accepting input from user..." << endl;
	string score;
	string score2;
	vector<string>  print_score;
	int a[6][11] = { 0 };
	int continue_strikes=0;
	int stop;
	int sum=0;
	bool strike = false;
	bool strikeframe = false;
	bool spare=false;// = false;
	bool spareframe=false;// = false;
	int frames = 0;
	for (int i = 1; i <= 10; i++)
		{
			int pin_remaining = 10;
			
				stop = 0;
				while (stop != 6)
				{
					cout << "Roll " << 1<< " of frame " << i << ":" << endl;
					getline(cin, score);

//.........................................................................................					
					double roll_score;          //number which will contain the result
					istringstream convert(score); // stringstream used for the conversion constructed with the contents of 'frames_value' 
					// ie: the stream will start containing the characters of 'frames_value'
					if (!(convert >> roll_score)) //give the value to 'frames_value' using the characters in the stream
						roll_score = 0;             //if that fails set 'frames_value' to 0

					bool integer = true;
					// check if the number of frames is integer or not
					integer_check(integer, roll_score);
//.....................................................................................................
					if (integer == false )
					{
						cout << "Please type integer number of roll score" << endl;
					}
					else if (integer == true)
					{
						if ((roll_score <= 0 || roll_score >9 ) && ((score != "X" && score != "G" && score != "F")))
						{
							cout << "Please type number of roll score between 1 to 9 or right syntax" << endl;
						}
						else
						{
							// input user typed roll score into frame_score function to print score.
								// only allow 1-9,G,F,X,/

							pin_remaining = pin_remaining - roll_score;
							frame_score(print_score, score, stop, roll_score, playerName, pin_remaining, sum, strike, strikeframe, spare, spareframe, frames, type, score2, a, continue_strikes, turkey, keep_your_day_job);
							printScore(print_score, playerName);

							cout << endl;
						}
					}
				}

		}
	score_each_game.push_back(sum);
	//strike_count, spare_count, getter_count
	for (int i = 0; i <print_score.size(); i++)
	{
		if (print_score[i] == "X")//strike
		{
			strike_count = strike_count + 1;
		
		}
		if (print_score[i] == "G")//getter
		{
			getter_count = getter_count + 1;
		}
		if (print_score[i] == "/")//spare
		{
			spare_count = spare_count + 1;
		}
		
	}
	
}

void input_file(string input_options, string playerName, int type, vector<int> &score_each_game, int &strike_count, int &spare_count, int &getter_count, bool &turkey, bool &keep_your_day_job)
{
	int stop = 0;
	string file_format;
	string rows[10][10];
	int a[6][11] = { 0 };
	int continue_strikes = 0;
	while (stop != 6)
	{
		int cc = 0;
		cout << "Please enter the filename to read from, (type a.txt for testing)" << endl;
		
		getline(cin, file_format);

		ifstream in;
		in.open(file_format);
		if (!in)
		{
			cout<< "open input file failed!" << endl;

		}
		else
		{
			cout << "Accepting input from file" << file_format << "............" << endl;
			
			for (int i = 0; i < 10;i++)
			{

				for (int j = 0; j < 2; j++)
				{
					in >> rows[i][j];
					//.........................................................................................					
					double roll_score;          //number which will contain the result
					istringstream convert(rows[i][j]); // stringstream used for the conversion constructed with the contents of 'frames_value' 
					// ie: the stream will start containing the characters of 'frames_value'
					if (!(convert >> roll_score)) //give the value to 'frames_value' using the characters in the stream
						roll_score = 0;             //if that fails set 'frames_value' to 0

					bool integer = true;
					// check if the number of frames is integer or not
					integer_check(integer, roll_score);
					//.....................................................................................................
					//.....................................................................................................
					if (integer == false)
					{
						cout << "Please write integer number of roll score in txt file " << endl;
						cc = cc + 1;
					}
					else if (integer == true)
					{
						if ((roll_score <= 0 || roll_score >9) && ((rows[i][j] != "X" && rows[i][j] != "G" && rows[i][j] != "F"&& rows[i][j] != "-"&& rows[i][j] != "/")))
						{
							cout << "Please write number of roll score between 1 to 9 or right syntax in txt file" << endl;	
							cc = cc + 1;
						}	
					}
				}
				if ((rows[i][0] == "X" &&rows[i][1] != "-") || rows[i][0] == "/"||rows[i][1] == "X")
				{

					cout << "Please write number of roll score between 1 to 9 or right syntax in txt file" << endl;
					cc = cc + 1;
					
				}
			}
			if (cc == 0)
			{
				stop = 6;
			}
		}
	}


	string score;
	string score2;
	vector<string>  print_score;
	//int stop;
	int sum = 0;
	bool strike = false;
	bool strikeframe = false;
	bool spare = false;// = false;
	bool spareframe = false;// = false;
	int n = 0;
	for (int i = 1; i <= 10; i++)
	{
		int pin_remaining = 10;
		score = rows[i - 1][0];
		score2 = rows[i - 1][1];
		double roll_score;          //number which will contain the result
		istringstream convert(score); // stringstream used for the conversion constructed with the contents of 'frames_value' 
		// ie: the stream will start containing the characters of 'frames_value'
		if (!(convert >> roll_score)) //give the value to 'frames_value' using the characters in the stream
			roll_score = 0;             //if that fails set 'frames_value' to 0

		cout << "Roll " << 1 << " of frame " << i << " : " << score<<endl;		
		pin_remaining = pin_remaining - roll_score;
		frame_score(print_score, score, stop, roll_score, playerName, pin_remaining, sum, strike, strikeframe, spare, spareframe, n, type, score2, a, continue_strikes, turkey, keep_your_day_job);
		printScore(print_score, playerName);
		

	}
	score_each_game.push_back(sum);
	//strike_count, spare_count, getter_count
	for (int i = 0; i <print_score.size(); i++)
	{
		if (print_score[i] == "X")//strike
		{
			strike_count = strike_count + 1;

		}
		if (print_score[i] == "G")//getter
		{
			getter_count = getter_count + 1;
		}
		if (print_score[i] == "/")//spare
		{
			spare_count = spare_count + 1;
		}

	}
}

string random_roll(int &pin_remaining,int row)
{
	string result;

	int r = rand() % (pin_remaining + 1); // 0 to 10 
	pin_remaining = pin_remaining - r;

	if (pin_remaining == 0&&row==1)//strike
	{
		result = "X";
	}
	else if (row == 2 && r == 0)
	{
		result = "-";
	}
	else if (pin_remaining == 0 && row == 2&&r!=0)
	{
		result = "/";
	}
	else if (r == 0 && pin_remaining != 0)
	{
		int r2 = rand() % 2; //create 0,1
		if (r2 == 1)
		{
			result = "G";
		}
		else
		{
			result = "F";
		}
	}
	else//1 to 9
	{
		ostringstream convert;   // stream used for the conversion
		convert << r;   // insert the textual representation of 'Number' in the characters in the stream
		result = convert.str(); // set 'result' to the contents of the stream
	}
	return result;
}
void input_autogen(string input_options, string playerName, int type, vector<int> &score_each_game, int &strike_count, int &spare_count, int &getter_count, bool &turkey, bool &keep_your_day_job)
{
	
	cout << "Accepting input from  autogenerator..." << endl;
	string score;
	string  score2;
	vector<string>  print_score;
	int a[6][11] = { 0 };
	int continue_strikes = 0;
	int stop;
	int sum = 0;
	bool strike = false;
	bool strikeframe = false;
	bool spare = false;// = false;
	bool spareframe = false;// = false;
	int n = 0;

	for (int i = 1; i <= 10; i++)
	{
		int row = 1;
		int pin_remaining = 10;
		stop = 0;

			score = random_roll(pin_remaining,row);
			row = row + 1;
			score2 = random_roll(pin_remaining,row);
			cout << "Roll " << 1 << " of frame " << i << " : " << score<<endl;
			

			//.........................................................................................					
			double roll_score;          //number which will contain the result
			istringstream convert(score); // stringstream used for the conversion constructed with the contents of 'frames_value' 
			// ie: the stream will start containing the characters of 'frames_value'
			if (!(convert >> roll_score)) //give the value to 'frames_value' using the characters in the stream
				roll_score = 0;             //if that fails set 'frames_value' to 0					
				
			frame_score(print_score, score, stop, roll_score, playerName, pin_remaining, sum, strike, strikeframe, spare, spareframe, n, type, score2, a, continue_strikes, turkey, keep_your_day_job);
			printScore(print_score, playerName);
	}
	score_each_game.push_back(sum);
	//strike_count, spare_count, getter_count
	for (int i = 0; i <print_score.size(); i++)
	{
		if (print_score[i] == "X")//strike
		{
			strike_count = strike_count + 1;

		}
		if (print_score[i] == "G")//getter
		{
			getter_count = getter_count + 1;
		}
		if (print_score[i] == "/")//spare
		{
			spare_count = spare_count + 1;
		}

	}
}

void begin_game(string playerName, int &number_games, vector<int> &score_each_game, int &strike_count, int &spare_count, int &getter_count, int &highest_score, bool &turkey, bool &keep_your_day_job)
{
	string input_options;
	//calculate the number of games required.
	number_games = number_games_needed(playerName);
	
	for (int i = 1; i<=number_games; i++)
	{
		int stop3 = 0;
		while (stop3 != 6)
		{
			cout << "** Beginning Game " << i << " of " << number_games << "* *" << endl;
			cout << "How will you provide input (1=USER, 2=FILE, 3=AUTOGEN):" << endl;
			getline(cin, input_options);
			
			if (input_options == "1")//USER
			{
				int type = 1;
				input_user(input_options, playerName, type, score_each_game, strike_count, spare_count, getter_count, turkey, keep_your_day_job);

				stop3 = 6;
			}

			else if (input_options == "2")//FILE
			{
				int type = 2;
				input_file(input_options, playerName, type, score_each_game, strike_count, spare_count, getter_count, turkey, keep_your_day_job);
				stop3 = 6;
			}

			else if (input_options == "3")//AUTOGEN
			{
				int type = 3;
				input_autogen(input_options, playerName, type, score_each_game, strike_count, spare_count, getter_count, turkey, keep_your_day_job);

				stop3 = 6;
			}
			else if (input_options != "1" || input_options != "2" || input_options != "3")
			{
				cout << "Type wrong number, Please retype it again." << endl;
				cout << endl;
			}
		}
		check_highest_score(score_each_game, highest_score);
	}

}

int main()
{
	// set up initial values.
	srand(time(0));
	vector<string> final_scores;
	vector<int> score_each_game;
	string playerName;
	string playagain;
	int strike_count=0;
	int spare_count=0;
	int getter_count=0;
	int number_games;
	int stop = 0;
	int highest_score;
	bool turkey = false;
	bool keep_your_day_job = false;
	// set up looping for users so that users can keep playing.
	while (stop != 6)
	{
		// list options to users
		cout << " Please enter player's name:" << endl;
		getline(cin, playerName);

		// now begin game
		begin_game(playerName, number_games, score_each_game, strike_count, spare_count, getter_count, highest_score, turkey,keep_your_day_job);


		//show statistics and awards
		awards_statistics(score_each_game, number_games, strike_count, spare_count, getter_count, highest_score, turkey, keep_your_day_job);

		// set up looping for users if they type wrong syntax.
		int stop1 = 0;
		while (stop1 != 6)// set up for looping
		{
			cout << " Would you like to play another series (Y=Yes, N=No)?" << endl;
			getline(cin, playagain);

			if (playagain == "Y") // set up type 6 to quit program
			{
				stop = 0;
				stop1 = 6;
			}

			else if (playagain == "N")//Display all restaurants
			{

				cout << "quit program........................" << endl;
				stop1 = 6;
				stop = 6;
			}
			else if (playagain != "Y" || playagain != "N")  // quit program
			{
				cout << "typing wrong syntax, Please retype again............." << endl;
			}
			cout << endl << endl;
		}// end of while loop	
	}// end of while loop	
	system("pause");
	return 0;
}