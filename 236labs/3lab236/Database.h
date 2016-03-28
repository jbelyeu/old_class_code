#pragma once
#include <string>
#include "Relation.h"
#include "DatalogProgram.h"
#include "Tuple.h"
#include <map>
using namespace std;

class Database
{
public:
	Database(DatalogProgram& parserIn)
	{
		parser = parserIn;
	}

	Database()	{}

	~Database()	{}

	void addTable (Relation& newTable)
	{
		tables[newTable.getName()] = newTable;
	}

	void addFact(string& name, Tuple& data)
	{
		tables[name].addRow(data);
	}

	Relation getTable(string& name)
	{
		return tables[name];
	}

private:
	map<string, Relation> tables;
	DatalogProgram parser;
};