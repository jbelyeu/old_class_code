#pragma once
#include <string>
#include "Scheme.h"
#include <set>
#include "Tuple.h"
#include "Parameter.h"
#include <map>
#include "Predicate.h"
using namespace std;

class Relation
{
public:
	Relation(string& nameIn, Scheme& columnTitles) :

		name(nameIn),
		columnNames(columnTitles),
		numberOfRows(0)
	{};

	Relation(){}

	~Relation(){}

	void addRow(Tuple& rowIn)
	{
		if (rows.find(rowIn) == rows.end())
		{
			rows.insert(rowIn);
			numberOfRows++;
		}
	}

	//map<int, string> constants
	Relation select(vector<pair<int, string>>& constants)
	{
		Relation response = Relation(this->name, this->columnNames);

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
		Relation response = Relation(this->name, this->columnNames);

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

		/*The project operation from the last project may need to be improved to support evaluating rules.
		The project operation needs to be able to change the order of the columns in a relation to support evaluating rules.*/

		return response;
	}

	//overload for processing rules.
	Relation project(predicate& head)
	{
		Scheme newScheme;
		//newScheme passed by reference, will end up with names of new attributes
		Relation toProject = createForProject(head, newScheme);
		

		for (auto iter = rows.begin(); iter != rows.end(); ++iter)
		{
			Tuple newRow;
			for (unsigned i = 0; i < newScheme.size(); i++)
			{
				for (unsigned j = 0; j < (*iter).size(); j++)
				{
					if (newScheme[i] == columnNames[j])
					{
						newRow.push_back((*iter)[j]);
						break;
					}
				}
			}
			toProject.addRow(newRow);
		}
		
		return toProject;
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
		
		this->columnNames = responseColumnNames;

		return *this;
	}

	//backbone function for project 4. Called on a relation and passed a second one. End result is one only.
	Relation Join(Relation toJoin)
	{
		Scheme joinedScheme;
		vector<pair<unsigned, unsigned>> matchingNameIndeces = joinSchemes(toJoin.getScheme(), joinedScheme);
		Relation joinedRelation = Relation(this->name, joinedScheme);
		set<Tuple> toJoinRows = toJoin.getRows();

		for (auto i = this->rows.begin(); i != this->rows.end(); ++i)
		{
			for (auto j = toJoinRows.begin(); j != toJoinRows.end(); ++j)
			{
				//call function to check whether or not the two tuples can join. if so, join them and add to relation

				Tuple leftTuple = *i;
				Tuple rightTuple = *j;

				if (matchingNameIndeces.size() != 0) //if there are no indeces with matching names, the tuples can be joined, by definition
				{
					if (leftTuple.canJoin(rightTuple, matchingNameIndeces)) //call on this tuple, pass in the other and the vector of matching indeces
					{
						Tuple newTuple = leftTuple.Join(rightTuple, matchingNameIndeces);
						joinedRelation.addRow(newTuple);
					}
				}
				else
				{
					leftTuple.insert(leftTuple.end(), rightTuple.begin(), rightTuple.end());
					joinedRelation.addRow(leftTuple);
				}
			}
		}

		return joinedRelation;
	}

	//backbone function for project 4
	Relation Union(Relation& toAdd)
	{
		set<Tuple> rowsToAdd = toAdd.getRows();
		//loop through rows in relation to add. If not already present, add to this.rows
		for (auto iter = rowsToAdd.begin(); iter != rowsToAdd.end(); ++iter)
		{
			if (rows.find(*iter) == rows.end())
			{
				rows.insert(*iter);
				numberOfRows++;
			}
		}
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

	Scheme getScheme()
	{
		return columnNames;
	}

private:
	string name;
	Scheme columnNames;
	set<Tuple> rows;
	int numberOfRows;

	vector<pair<unsigned, unsigned>> joinSchemes(Scheme toJoin, Scheme& newScheme)
	{
		newScheme = this->columnNames;
		map<string, unsigned> thisNamesAndIndeces;
		vector<pair<unsigned, unsigned>> matchingNames; //left side is the "this" indeces of same-named variable, right is the new one

		//this is suckishly inefficient
		for (unsigned i = 0; i < this->columnNames.size(); i++)
		{
			thisNamesAndIndeces[this->columnNames[i]] = i;
		}

		for (unsigned i = 0; i < toJoin.size(); i++)
		{
			if (thisNamesAndIndeces.find(toJoin[i]) != thisNamesAndIndeces.end())
			{
				//gives me the index where the name was found in the other scheme and the one where it was found in this scheme
				pair<unsigned, unsigned> newPair(thisNamesAndIndeces[toJoin[i]], i); 
				matchingNames.push_back(newPair);
			}
			else
			{
				newScheme.push_back(toJoin[i]);
			}
		}
		return matchingNames;
	}

	Relation createForProject(predicate& head, Scheme& newScheme)
	{
		vector<param> oldParams = head.getParams();

		for (unsigned i = 0; i < oldParams.size(); i++)
		{
			newScheme.push_back(oldParams[i].getName());
		}

		string name = head.getName();

		return Relation(name, newScheme);
	}
};