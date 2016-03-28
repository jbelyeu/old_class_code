#include "Interpreter.h"
#include <ctime>
using namespace std;

int main(int argc, char* argv[])
{
	clock_t time;
	time = clock();
	Interpreter lab3Interpreter = Interpreter();

	lab3Interpreter.create(argv);
	lab3Interpreter.loadSchemes();
	lab3Interpreter.loadFacts();
	lab3Interpreter.processQueries(argv);

	time = clock() - time;
	printf("It took me %d clicks (%f seconds).\n", time, ((float)time) / CLOCKS_PER_SEC);
	system("pause");
	return 0;
}