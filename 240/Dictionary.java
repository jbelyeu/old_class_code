package spell;

import java.util.Arrays;

//constructor
//member methods
//inner class declaration
//data fields

public class Dictionary implements Trie 
{
	Dictionary ()
	{
		root = new MyNode();
		wordCount = 0;
		nodeCount = 1;		
	}
		
	@Override
	public void add(String word) 
	{
		// TODO Auto-generated method stub
		int index = searchLetter(word, 0);
		
		if (root.children[index] == null)
		{
			nodeCount++;
			root.children[index] = new MyNode();
		}
		
		add(word, root.children[index], 0);
	}
	
	

	private void add(String word, MyNode node, int pos)
	{
		if ( pos == word.length() -1) //this means the end of the word was reached and it can be added
		{
			wordCount++;
			node.addWord();
		}
		else 
		{
			pos++;
			int index = searchLetter(word, pos);
			
			if (node.children[index] == null)
			{
				nodeCount++;
				node.children[index] = new MyNode();
			}
			
			add(word, node.children[index], pos);
		}
	}

	@Override
	public Node find(String word) 
	{
		int index = searchLetter(word, 0);
		
		if (root.children[index] == null)
		{
			nodeCount++;
			root.children[index] = new MyNode();
		}
		
		return find(word, root.children[index], 0);
	}
	
	private MyNode find(String word, MyNode node, int pos)
	{
		MyNode toReturn = new MyNode();
		
		if ( pos == word.length() -1) //this means the end of the word was reached and it can be added
		{
			toReturn = node;
		}
		else 
		{
			pos++;
			int index = searchLetter(word, pos);
			
			if (node.children[index] == null)
			{
				toReturn = null;
			}
			
			find(word, node.children[index], pos);
		}
		
		return toReturn;
	}

	@Override
	public int getWordCount() 
	{
		return wordCount;
	}

	@Override
	public int getNodeCount() 
	{
		return nodeCount;
	}
	
	private int searchLetter(String word, int index)
	{
		String letter = (word.substring( index, ( index +1 ))).toLowerCase();
		return letter.charAt(index);
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeCount;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + wordCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) 
		{
			return true;
		}
		
		if (obj == null) 
		{
			return false;
		}
		if ( getClass() != obj.getClass() ) 
		{
			return false;
		}
		Dictionary other = (Dictionary) obj;
		if (nodeCount != other.nodeCount) 
		{
			return false;
		}
		
		if (root == null) 
		{
			if (other.root != null) 
			{
				return false;
			}
		} 
		else if (! root.equals(other.root)) 
		{
			return false;
		}
		else if (! equalsHelper(this.root, other.root))
		{
			return false;
		}
		if (wordCount != other.wordCount) 
		{
			return false;
		}
		
		return true;
	}

	private boolean equalsHelper(MyNode thisNode, MyNode otherNode)
	{
		boolean retVal = true;
		if (thisNode.children.length == otherNode.children.length)
		{
			for (int i = 0; i < thisNode.children.length; ++i)
			{
				if (! thisNode.children[i].equals(otherNode.children[i]))
				{
					retVal = false;
				}
				else
				{
					retVal = equalsHelper(thisNode.children[i], otherNode.children[i]);
				}
			}
		}
		
		return retVal;
	}
	
	@Override
	public String toString() 
	{
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		StringBuilder retval = new StringBuilder();
		return toStringHelper(this.root.children[0], letters, 0, retval);
	}
	
	private String toStringHelper(MyNode node, String[] letters, int pos, StringBuilder retval)
	{
		retval.insert(retval.length(), letters[pos]);
		
		for (int i = 0; i < node.children.length; ++i)
		{
			if (node.children[i] != null)
			{
				retval.insert(retval.length(), toStringHelper(node.children[i], letters, i, retval));
			}
		}
		
		return retval.toString();
	}

	//constructor
	//member methods
	//data fields
	class MyNode implements Node
	{
		MyNode()
		{
			children[26] = new MyNode();
			wordFrequency = 0;
		}

		@Override
		public int getValue() 
		{
			return wordFrequency;
		}
		
		public void addWord()
		{
			wordFrequency++;
		}
		
		
		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(children);
			result = prime * result + wordFrequency;
			return result;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			MyNode other = (MyNode) obj;
			
			if (! getOuterType().equals(other.getOuterType()))
			{
				return false;
			}
			if (!Arrays.equals(children, other.children))
			{
				return false;
			}
			if (wordFrequency != other.wordFrequency)
			{
				return false;
			}
			return true;
		}


		@Override
		public String toString() 
		{
			return "MyNode [children=" + Arrays.toString(children)
					+ ", wordFrequency=" + wordFrequency + "]";
		}


		MyNode[] children;
		int wordFrequency;
		private Dictionary getOuterType() 
		{
			return Dictionary.this;
		}
	}
	
	private MyNode root;
	private int wordCount;
	private int nodeCount;

}
	