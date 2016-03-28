#pragma once
#include "Node.h"
#include <map>
#include <set>
using namespace std;

class DependencyGraph: public map<string, Node>
{
public:
	DependencyGraph() :
		map<string, Node>(),
		startPostNums(0)
	{}
	
	

	//unlicensed, untested
	//recursive, to actually perform search
	void depthFirstSearch(string entryPoint)
	{
		
		(*this)[entryPoint].visit();

		set<string> edges = (*this)[entryPoint].getEdges();
		for (auto siter : edges)
		{
			if (!(*this)[siter].alreadyVisited())
			{
				depthFirstSearch(siter);
			}
		}
		startPostNums++;
		(*this)[entryPoint].setPostOrder(startPostNums);
	}

	//untested
	map<string, int> getPostOrder()
	{
		//loop through the graph (after depthFirstSeartch()) and store all of the numbers and IDs in a map, 
		//with numbers as the keys. Map should be indexed backwards to give largest numbers first when outputting
		map<string, int> postOrders;
	
		for (auto giter : (*this))
		{
			int num = giter.second.getPostOrder();
			if (num > 0)
			{
				postOrders[giter.first] = num;
			}	
		}
		//this->resetGraph();
		return postOrders;
	}

	//untested
	void resetGraph()
	{
		//loop through graph and set all post order numbers back to 0 (after getPostOrder())
		for (auto giter : (*this))
		{
			(*this)[giter.first].reset();
		}
		startPostNums = 0;
	}

	//unlicensed, untested
	string findCycles(map<string, int>& evalOrder)
	{
		const char RULE = 'R';
		string backEdges = "\n\n  Backward Edges";
		for (auto viter : evalOrder)
		{
			if (viter.first[0] == RULE)
			{
				set<string> dependencies = (*this)[viter.first].getEdges();
				set<string> edges;

				for (auto siter : dependencies)
				{
					if ((*this)[siter].getPostOrder() >= viter.second)
					{
						edges.insert(siter);
					}
				}
				if (edges.size() > 0)
				{
					backEdges += "\n    " + viter.first + ":";
					for (auto iter : edges)
					{
						backEdges += " " + iter;
					}
				}
			}
		}
		this->resetGraph();
		return backEdges;
	}

	~DependencyGraph(){}
	
private:
	int startPostNums;
};