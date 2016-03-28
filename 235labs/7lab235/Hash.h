#pragma once
#include "List.h"
#include <string>
#include "hashFun.h"

using namespace std;

template<typename ItemType>

class Table
{
private:
	LinkList<ItemType>* table;
	int capacity;
	int size;

public:
	Table() :

		capacity(1),
		size(0)
	{
		table = new LinkList<ItemType>[1];
		table[0] = LinkList<ItemType>();
	}

	void add(const ItemType item)
	{
		if (find(item) == true)
		{
			return;//pretend to do something, but don't really
		}
		if (size >= capacity)
		{
			reHash((size * 2) + 1);
		}
		unsigned hashCode = hashing(item, capacity);
		table[hashCode].push_back(item);
		size++;
	}

	void reHash(int newCap)
	{
		size = 0;
		int oldCap = capacity;
		capacity = newCap;
		LinkList<ItemType>* temp = table;
		table = new LinkList<ItemType>[newCap];
		for (int i = 0; i < newCap; i++)
			table[i] = LinkList<ItemType>();

		for (int i = 0; i < oldCap; i++) 
		{
			if (temp[i].Size() > 0) 
			{
				int listSize = temp[i].Size();
				for (int j = 0; j < listSize; j++)
				{
					ItemType item = temp[i].front();
					
					add(item);
				}
			}
		}
		
		delete[] temp;
		temp = NULL;
	}

	void remove(ItemType item)
	{
		if (find(item) == true)
		{
			//remove the item, check the load factor. If it's below 0.5, shrink the array to half size (int division, to truncate) and rehash
			int hashCode = hashing(item, capacity);
			for (int i = 0; i < table[hashCode].Size(); i++)
			{
				if (table[hashCode].findPointer(i)->item == item)
				{
					ItemType tempitem = table[hashCode].remove(i);
					size--;
				}
			}
			if ((size * 2) < capacity)
			{
				reHash(capacity / 2);
			}
		}
	}

	bool find(ItemType item)
	{
		if (size == 0) { return false; }
		unsigned hashCode = hashing(item, capacity);
	
		if (table[hashCode].find(item) != -1)
		{
			return true;
		}

		return false;
	}

	void print(ofstream& outFile)
	{
		if (size > 0)
		{
		
			for (int i = 0; i < capacity; i++)
			{
				outFile  << "\nhash " << i << ":";
				for (int j = 1; j < table[i].Size()+1; j++)
				{
					if (j> 8 && j % 8 == 1)
					{
						outFile << "\nhash " << i << ":";
					}
					outFile << " " << table[i].findPointer(j - 1)->item;
				}
			}
			outFile << endl;
		}
		else
		{
			outFile << endl;
		}
	}

	void clear()
	{
		LinkList<ItemType>* temp = table;
		table = new LinkList<ItemType>[1];
		table[0] = LinkList<ItemType>();
		size = 0;
		capacity = 1;
		delete[] temp;
		temp = NULL;
	}

	~Table()
	{
		delete[] table;
	}
};