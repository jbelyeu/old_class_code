#pragma once
#include <string>
#include <set>
using namespace std;

class Node
{
public:
	Node():
		visited(false),
		postOrder(-1)
	{}

	bool alreadyVisited()
	{
		return visited;
	}

	void visit()
	{
		visited = true;
	}

	set<string> getEdges()
	{
		return adjacents;
	}

	void addDependency(string ident)
	{
		adjacents.insert(ident);
	}

	void setPostOrder(int num)
	{
		postOrder = num;
	}

	int getPostOrder()
	{
		return postOrder;
	}

	void reset()
	{
		visited = false;
		postOrder = 0;
	}

	~Node(){}

private:
	set<string> adjacents;
	bool visited;
	int postOrder;
	
};