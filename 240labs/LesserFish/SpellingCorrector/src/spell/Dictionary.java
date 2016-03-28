package spell;

import java.util.Arrays;

//constructor
//member methods
//inner class declaration
//data fields

public class Dictionary implements Trie 
{		
	public Dictionary() 
	{
		super();
		this.root = new MyNode(true);
		this.wordCount = 0;
		this.nodeCount = 1;
	}

	@Override
	public void add(String word) 
	{
		if (word.length() > 0)
		{	int index = searchLetter(word, 0);
			
			if (root.children[index] == null)
			{
				this.nodeCount++;
				root.children[index] = new MyNode(true);
			}
			
			add(word, root.children[index], 0); //0 is the position in the word
		}
	}
	
	

	private void add(String word, MyNode node, int pos)
	{
		int index = searchLetter(word, pos); //the letter's index in the array of children
		if ( pos == word.length() -1) //this means the end of the word was reached and it can be added
		{
			this.wordCount++;
			node.addWord(index);
		}
		else //get the next letter and add it to the correct node
		{
			pos++;	
			index = searchLetter(word, pos);
			if (node.children[index] == null)
			{
				this.nodeCount++;
				node.children[index] = new MyNode(true);
			}
			add(word, node.children[index], pos);
		}
	}

	@Override
	public Node find(String word) 
	{
		if (word.length() > 0)
		{
			int index = searchLetter(word, 0);
			
			if (root.children[index] == null)
			{
				return null;
			}
			return find(word, root.children[index], 0);
		}
		else
		{
			return null;
		}		
	}
	
	private MyNode find(String word, MyNode node, int pos)
	{
		MyNode toReturn = null;
		
		if ( pos == word.length() -1) //this means the end of the word was reached and it can be returned
		{			
			toReturn = node;
		}
		else 
		{
			pos++;
			int index = searchLetter(word, pos);
			
			if (node.children[index] == null)
			{
				return null;
			}
			
			toReturn = find(word, node.children[index], pos);
		}
		
		if (toReturn != null && toReturn.getValue() > 0)
		{
			return toReturn;
		}
		else
		{
			return null;
		}
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
		int retval = (letter.charAt(0) - 'a');
		return retval;
	}
	
	@Override
	public int hashCode() 
	{		
		return 51 * (root.hashCode() + wordCount + nodeCount);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) //compares address
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
		
		return ( ( this.nodeCount == other.getNodeCount() ) && 
				( this.wordCount == other.getWordCount() ) );
	}
	
	@Override
	public String toString() 
	{
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", ""};
		StringBuilder retval = new StringBuilder();
		StringBuilder currWord = new StringBuilder();
		return toStringHelper(this.root, letters, 26, retval, currWord);
	}
	
	private String toStringHelper(MyNode node, String[] letters, int pos, StringBuilder retval, StringBuilder currWord)
	{
		currWord.insert(currWord.length(), letters[pos]);
		if (node.getValue() > 0)
		{
			retval.insert(retval.length(), currWord.toString() + " " + node.getValue() + "\n");
		}
		
		for (int i = 0; i < node.children.length; ++i)
		{
			if (node.children[i] != null)
			{				
				toStringHelper(node.children[i], letters, i, retval, currWord);
			}
			if (i == node.children.length -1 && currWord.length() > 0 )
			{
				currWord.deleteCharAt(currWord.length() -1);
			}
		}		

		return retval.toString();
	}

	//constructor
	//member methods
	//data fields
	class MyNode implements Trie.Node
	{
		public MyNode() 
		{
			super();
			this.children = null;
			this.wordFrequency = 0;
		}
		
		public MyNode(boolean nuts) 
		{
			super();
			this.children = new MyNode[26];
			this.wordFrequency = 0;
		}

		@Override
		public int getValue() 
		{
			return wordFrequency;
		}
		
		public void addWord(int index)
		{
			this.wordFrequency++;
		}
		
		
		@Override
		public int hashCode() 
		{
			return 57 * wordFrequency;
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
			
			if ( ! Arrays.equals(children, other.children))
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
			return "MyNode [children=" + Arrays.toString(children) + ", wordFrequency=" + wordFrequency + "]";
		}


		MyNode children[];
		int wordFrequency;
	}
	
	private MyNode root;
	private int wordCount;
	private int nodeCount;

}
	