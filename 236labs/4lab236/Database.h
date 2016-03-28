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
	Database(DatalogProgram& parserIn) :

		parser(parserIn)
	{};

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

	void Union(string name, Relation& toAdd)
	{
		//call on larger of relations, pass in smaller. This is faster
		if (tables[name].getNumOfRows() > toAdd.getNumOfRows())
		{
			tables[name].Union(toAdd);
		}
		else
		{
			toAdd.Union(tables[name]);
			tables[name] = toAdd;
		}
	}

	Relation getTable(string& name)
	{
		return tables[name];
	}

	int getTotalRowCount()
	{
		int rowCount = 0;

		for (auto iter = tables.begin(); iter != tables.end(); ++iter)
		{
			rowCount += iter->second.getNumOfRows();
		}

		return rowCount;
	}

	int getNumTables()
	{
		return tables.size();
	}

private:
	map<string, Relation> tables;
	DatalogProgram parser;
};