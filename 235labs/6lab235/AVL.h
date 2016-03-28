#pragma once
#include "List.h"
using namespace std;

template<typename ItemType>

class AVL
{
private:

	struct node{
		ItemType item;
		node* left;
		node* right;
		int height;

		node(const ItemType newItem) :

			item(newItem),
			left(NULL),
			right(NULL),
			height(0)
		{}

		~node()	{}
	};

	node* root;
	int size;

	int getHeight(node* localRoot)
	{
		if (localRoot != NULL)
		{
			return localRoot->height;
		}
		return 0;
	}

	node* rotateRight(node* localRoot)
	{
		node* temp = localRoot;
		localRoot = localRoot->left;
		temp->left = localRoot->right;
		localRoot->right = temp;
		setHeight(localRoot->right);
		temp = NULL;
	
		return localRoot;
	}

	node* rotateLeft(node* localRoot)
	{
		node* temp = localRoot;
		localRoot = localRoot->right;
		temp->right = localRoot->left;
		localRoot->left = temp;
		setHeight(localRoot->left);
		temp = NULL;

		return localRoot;
	}

	node* balance(node* localRoot)
	{
		if (getHeight(localRoot) == 0)
		{
			return localRoot;
		}
		int rightHeight = getHeight(localRoot->right);
		int leftHeight = getHeight(localRoot->left);

		if ((rightHeight - leftHeight) > 1)
		{
			int RLH = getHeight(localRoot->right->left);
			int RRH = getHeight(localRoot->right->right);

			if (RLH > RRH)
			{
				localRoot->right = rotateRight(localRoot->right);
			}
			localRoot = rotateLeft(localRoot);
			setHeight(localRoot);
		}
		else if ((leftHeight - rightHeight) > 1)
		{
			int LLH = getHeight(localRoot->left->left); //height of the left-left child of the local root
			int LRH = getHeight(localRoot->left->right); //height of the left-right child of the local root

			if (LRH > LLH)
			{
				localRoot->left = rotateLeft(localRoot->left);
			}
			localRoot = rotateRight(localRoot);
			setHeight(localRoot);
		}
		return localRoot;

	}

public:
	AVL() ://constructor

		root(NULL),
		size(0)
	{}

	//add will need to call a recursive function to place the new node. Will create node and pass it in
	void add(const ItemType& item)
	{
		node* temp = new node(item);
		size++; //decrements if the object is not added
		root = add(temp, root);
		temp = NULL;
		setHeight(root);
		root = balance(root);
		setHeight(root);
	}

	//recursive add function. Needs to return a node* in order to check height
	node* add(node* temp, node* localRoot)
	{
		if (localRoot == NULL)
		{
			localRoot = temp;
			localRoot->height = 1;
		}
		else if (temp->item < localRoot->item)
		{
			localRoot->left = add(temp, localRoot->left);
			setHeight(localRoot);
			localRoot = balance(localRoot);
		}
		else if (temp->item > localRoot->item)
		{
			localRoot->right = add(temp, localRoot->right);
			setHeight(localRoot);
			localRoot = balance(localRoot);
		}
		else
		{
			delete temp;
			size--;
		}
		temp = NULL;
		return localRoot;
	}

	void setHeight(node* localRoot)
	{
		if (localRoot == NULL)
		{
		}
		else if (localRoot->right != NULL && localRoot->left != NULL)
		{
			if (localRoot->right->height > localRoot->left->height)
			{
				localRoot->height = localRoot->right->height + 1;
			}
			else
			{
				localRoot->height = localRoot->left->height + 1;
			}
		}
		else if (localRoot->right != NULL)
		{
			localRoot->height = localRoot->right->height + 1;
		}
		else if (localRoot->left != NULL)
		{
			localRoot->height = localRoot->left->height + 1;
		}
		else
		{
			localRoot->height = 1;
		}
	}

	//remove will also call a recursive function
	void remove(const ItemType& item)
	{
		if (root != NULL)
		{
			root = remove(item, root);//creating an error here. Is setting the root to the root of the subtree, might be the problem.
		}
		setHeight(root);
		root = balance(root);
		setHeight(root);
	}

	//recursive remove function. May need to return a value
	node* remove(const ItemType item, node* localRoot)
	{
		if (localRoot == NULL) //will only get here if the item is not found (remove was called on a NULL pointer
		{
			//size++;
		}
		else if (item < localRoot->item) //just got an error here. It couldn't read the item in the localRoot node
		{
			localRoot->left = remove(item, localRoot->left);
			setHeight(localRoot);
			localRoot = balance(localRoot);
		}
		else if (item > localRoot->item)
		{
			localRoot->right = remove(item, localRoot->right);
			setHeight(localRoot);
			localRoot = balance(localRoot);
		}
		else if (localRoot->height > 1)//item found, must be deleted. 
		{
			//Theoretically, this should find the smallest item in the right subtree from the item to be removed, 
			//swap the values stored in the two nodes, and then recursively remove the item from the right subtree
			//until the item to remove is down to a base case (a leaf node).
			if (localRoot->right == NULL)
			{
				//This covers the case if the correct node has at most only a left child and no right child
				node* temp = localRoot;
				localRoot = localRoot->left;
				delete temp;
				temp = NULL;
				setHeight(localRoot);
				localRoot = balance(localRoot);
				size--;
				return localRoot;
			}

			// This covers the case if the correct node has a right child
			node* smallest = findMin(localRoot->right); //for now, assume findMin works
			ItemType tempItem = smallest->item;
			smallest->item = localRoot->item;
			localRoot->item = tempItem;
			localRoot->right = remove(item, localRoot->right);
			setHeight(localRoot);
			localRoot = balance(localRoot);
			smallest = NULL; //this may cause a memory leak because the delete is in the recursive function
		}
		else
		{
			delete localRoot;
			localRoot = NULL;
			size--;
		}
		return localRoot;
	}

	node* findMin(node* newRoot)//returns smallest value in the tree. 
	{
		if (newRoot->left == NULL)
		{
			return newRoot;
		}
		else
		{
			return findMin(newRoot->left);
		}
	}

	//find will also call a recursive function
	bool find(const ItemType& item)
	{
		bool found = false;
		if (root != NULL)
		{
			found = find(item, root);
		}


		return found;
	}
	//this is said recursive function
	bool find(const ItemType item, node* newRoot)
	{
		if (newRoot == NULL)
		{
			return false;
		}
		else if (item < newRoot->item)
		{
			return find(item, newRoot->left);
		}
		else if (item > newRoot->item)
		{
			return find(item, newRoot->right);
		}
		else
		{
			return true;
		}
	}

	//clear will have to iterate through the tree and delete. 
	void clear()
	{
		while (root != NULL)
		{
			remove(root->item);
		}
	}

	//print will iterate through the tree and output in level order, using the list from List.h
	void print(ofstream& outFile)
	{
		LinkList<node*> Q;
		if (root != NULL)
		{
			Q.push(root);
			int L = -1;

			while (Q.getSize() > 0)
			{
				outFile << "\nlevel " << ++L << ": ";
				int S = Q.getSize();
				for (int i = 1; i < S + 1; i++)
				{
					node* temp = Q.pop();
					if (temp->left != NULL)
					{
						Q.push(temp->left);
					}
					if (temp->right != NULL)
					{
						Q.push(temp->right);
					}
					if (i> 8 && i % 8 == 1)
					{
						outFile << "\nlevel " << L << ": ";
					}
					outFile << temp->item << "(" << temp->height << ") ";

				}
			}
			outFile << endl;
		}
		else
		{
			outFile << endl;
		}
	}

	//destructor will need to call clear
	~AVL()
	{
		clear();
	}

};
