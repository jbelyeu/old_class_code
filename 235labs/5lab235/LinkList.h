#pragma once
#include<string>
using namespace std;

template<typename ItemType>
class LinkList
{
private:
	//creates subclass node inside LinkList. Node is public within LinkList, private to LinkList
	struct node
	{
		ItemType item;
		node* next; //pointer to the next node
		node* prev; //pointer to the previous node

		~node()
		{
			next = NULL;
			prev = NULL;
		}
	};

	int size; //keeps track of number of items in list
	node* head; //keeps track of first node (index 0, or list.begin())
	node* tail; //keeps track of last node (list.end())

public:
	LinkList() : //serves as constructor

		size(0), //initializes count to zero
		head(NULL), //points head at nothing
		tail(NULL) // points tail at nothing
		
	{}
	// here belong the member methods
	
	node* findPointer(int index)
	{
		//loop through until pointer to item at index is found
		int i = 0;
		node* n;
		if (index <= (size - 1) / 2)//to start at the beginning
		{
			n = head;
			while (i < index)
			{
				n = n->next;
				i++;
			}
		}
		else
		{
			n = tail;
			i = size - 1;
			while (i > index)
			{
				n = n->prev;
				i--;
			}
		}
		return n;
	}

	void insert(int index, ItemType newItem)//not sure what type to pass in here
	{
		//insert item at index, increment counter
		node* newNode = new node();
		newNode->item = newItem;
		
		if (index > size) 
		{ 
			delete newNode;
		}//can't add past size, but acts like it can. Would the semicolon be better than the matched braces?
		else if (size == 0) //means index must also be zero, first item in list
		{
			head = newNode;
			tail = newNode;
			head->next = NULL;
			head->prev = NULL; 
			size++;		
		}
		else if (index == size)
		{
			newNode->prev = tail;
			newNode->next = NULL;
			tail->next = newNode;
			tail = newNode;
			size++;
		}
		else if (index == 0)
		{
			newNode->next = head;
			head->prev = newNode;
			head = newNode;
			size++;
		}
		else
		{
			node* oldNode = findPointer(index - 1);
			newNode->prev = oldNode;
			newNode->next = oldNode->next;
			oldNode->next = newNode;
			newNode->next->prev = newNode;
			size++;
		}
		newNode = NULL;
	}

	ItemType remove(int index)//removes item at index, returns it
	{
		//remove the item, fill in the space, decrement counter, reassign head or tail or both
		//call function fo get pointer right before index
		node* temp = NULL;
		ItemType itemT;
		if (size == 0  || (index > (size - 1) || index < 0)) {} //may need to be ;
		else if (size == 1) //to remove final object (one object) from list. May need to rewrite later
		{
			itemT = head->item;
			delete head; 
			tail = NULL;
			head = NULL;
			size--;
		}
		else if (index == (size - 1))
		{
			temp = tail;
			tail = tail->prev;
			tail->next = NULL;
			itemT = temp->item;
			delete temp;
			size--;
		}
		else if (index == 0)
		{
			temp = head;
			head = temp->next;
			head->prev = NULL; 
			itemT = temp->item;
			delete temp;
			size--;
		}
		else
		{
			temp = findPointer(index);
			temp->prev->next = temp->next;
			temp->next->prev = temp->prev;
			itemT = temp->item;
			delete temp;
			size--;
		}
		temp = NULL;
		return itemT;
	}

	int find(ItemType toFind)//find toFind linearly, return the index corresponding
	{
		//loop through the list until toFind is matched. Return index. If not present, return -1
		int index = -1;
		node* temp = head;

		if (temp != NULL)
		{	
			index = 0;
			while (temp->item != toFind)
			{
				if (temp->next == NULL && temp->item != toFind)
					return -1;

				temp = temp->next;

				index++;
				if (temp->next == NULL && temp->item != toFind)
				{
					return -1;
				}
			}
		}
		return index;
	}

	void print(ofstream &outFile)// Must print all of the items in all nodes
	{
		//print everything to file
		
		node* temp = head;
		for (int i = 0; i < (size); i++)
		{
			outFile << "node " << i << ": " << temp->item << endl;
			temp = temp->next;
		}
	}
	void clear() //for items in list, call remove function
	{
		
		
		while (size > 0)
		{
			remove(0);
		}
		if (tail != NULL)
		{
			delete tail;
			system("pause");
		}
			
		if (head != NULL)
		{
			delete head;
			system("pause");
		}

		tail = NULL;
		head = NULL;
		//size = 0;
		
	}

	~LinkList()
	{
		while (head != NULL)
		{
			node* temp = head;
			head = head->next;
			delete temp;
		}
		tail = NULL;
		size = 0;
	}
};