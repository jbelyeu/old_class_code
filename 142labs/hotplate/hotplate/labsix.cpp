#include <iostream>
#include <iomanip>
#include <fstream>

using namespace std;

const int COLUMN = 20;
const int ROW = 20;

//receives as input the values for positions in the array, averages the ones in the middle with the four closest cells.
double set_average(double array[][COLUMN])
	{
		double max_dif = 0;

		for (int i= 0; i<ROW; i++)
		{ 
			for (int j = 0; j< COLUMN; j++)
			{ 
				double cell = 0;
				//if i is not first row
				if ((i > 0 && i< (ROW-1)) && (j > 0 && j < (COLUMN -1)))
				{
					cell = ((array[i-1][j] + array[i][j-1] + array[i][j+1] + array[i+1][j])/4.0);

					
					if (max_dif < cell - array[i][j])
					{
					 	max_dif = cell-array[i][j];

					}
					array[i][j] = cell;
				}
			
			}
		}
		
		return max_dif;
	}
void to_file (double array[][COLUMN])
{
	ofstream my_output;
	my_output.open("output_lab_six.csv");
	
	for (int i= 0; i<ROW; i++)
		{ 
			for (int j = 0; j< COLUMN; j++)
			{
				my_output << array[i][j] << ", ";
			}
			my_output << endl;
		}

}

int main()
{
	double array[ROW][COLUMN];
	for (int i= 0; i<ROW; i++)
	{ 
		for (int j = 0; j< COLUMN; j++)
			{
				if  ((i == 0 || i == (ROW-1)) && (j != 0 && j != (COLUMN -1)))
				{ 
					array[i][j] = 100;
				}
				else
				{
					array[i][j] = 0;
				}
			}
	}
	
	while (set_average(array) > 0.1)
	{		

	}

	/*for (int i=0; i<ROW; i++)
	{
		for (int j = 0; j < COLUMN; j++)
		{
			cout << setw(10) <<array[i][j];
		}		
		cout << endl;
	}*/

	to_file(array);

	//system ("pause");
	return 0;
}