#pragma once
#include <string>
#include "Scheme.h"
#include <set>
#include "Tuple.h"
#include "Parameter.h"
using namespace std;

class Relation
{
public:
	Relation(string& nameIn, Scheme& columnTitles) :

		name(nameIn),
		columns(columnTitles),
		numberOfRows(0)
		{};

	Relation(){}

	~Relation(){}

	void addRow(Tuple& rowIn)
	{
		rows.insert(rowIn);
		numberOfRows++;
	}

	//map<int, string> constants
	Relation select(vector<pair<int, string>>& constants)
	{
		Relation response = Relation(this->name, this->columns);

		//loop through all rows in the set. Slow and inefficient
		for (auto rowIter = rows.begin(); rowIter != rows.end(); ++rowIter)
		{
			Tuple row = *rowIter;
			bool rowMatches = true;

			//loop through all pairs in the vector. Slow and inefficient
			for (unsigned i = 0; i < constants.size(); i++)
			{
				int pos = constants[i].first;
				string val = constants[i].second;

				if (row[pos] != val)
				{
					rowMatches = false;
					break;
				}
			}

			if (rowMatches)
			{
				response.addRow(row);
			}
		}

		//if failed query, returns an empty relation (no tuples)
		return response;
	}

	Relation select(int& pos1, int& pos2)
	{
		Relation response = Relation(this->name, this->columns);

		if ((!rows.empty()))
		{
			for (auto iter = rows.begin(); iter != rows.end(); ++iter)
			{
				Tuple row = *iter;
				if (row[pos1] == row[pos2])
				{
					response.addRow(row);
				}
			}
		}

		//if failed query, returns an empty relation (no tuples)
		return response;
	}

	//limit response columns to those with variables in the query
	Relation project(vector<int>& positions)
	{
		Scheme responseColumns;

		//create relation with empty scheme for name
		Relation response = Relation(this->name, responseColumns);
		
		//iterate through rows in this->rows to put only the elements wanted in the new relation
		for (auto iter = rows.begin(); iter != rows.end(); iter++)
		{
			Tuple responseRow;
			Tuple oldRow = *iter;

			for (unsigned j = 0; j < positions.size(); j++)
			{
				//push value from original row into new row
				responseRow.push_back(oldRow[positions[j]]);
			}

			response.addRow(responseRow);
		}

		return response;
	}

	Relation rename(vector<param>& newNames)
	{
		Scheme responseColumnNames;
		
		for (unsigned i = 0; i < newNames.size(); i++)
		{
			if (!newNames[i].isConstant())
			{			
				responseColumnNames.push_back(newNames[i].getName());
			}
		}
		
		this->columns = responseColumnNames;

		return *this;
	}

	string getName()
	{
		return name;
	}

	set<Tuple> getRows()
	{
		return rows;
	}

	int getNumOfRows()
	{
		return numberOfRows;
	}

private:
	string name;
	Scheme columns;
	set<Tuple> rows;
	int numberOfRows;
};
