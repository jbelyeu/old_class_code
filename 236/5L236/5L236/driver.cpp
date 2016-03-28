#include "Interpreter.h"
using namespace std;

int main(int argc, char* argv[])
{
	Interpreter lab3Interpreter = Interpreter();

	lab3Interpreter.create(argv);
	lab3Interpreter.loadSchemes();
	lab3Interpreter.loadFacts();
	lab3Interpreter.createGraph();
	lab3Interpreter.processQueries(argv);
	return 0;
}