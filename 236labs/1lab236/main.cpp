#include "scanexenizer.h"
#include <string>
#include <iostream>

using namespace std;

int main(int argc, char* argv[])
{
	Tokenizer scanner = Tokenizer();
	if (!scanner.scan(argv)) {scanner.print(argv);}
	return 0;
}