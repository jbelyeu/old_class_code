package spell;

import java.io.IOException;
import java.util.Scanner;

public class Corrector implements SpellCorrector 
{
//no overidden contructor yet. Necessary?
	
	@Override
	public void useDictionary( String dictionaryFileName) throws IOException 
	{
		// this function should create the dictionary and load it
		Scanner scanner = new Scanner(dictionaryFileName);
		Trie dictionary = new Dictionary();
		
		while (scanner.hasNext())
		{
			dictionary.add(scanner.next());
		}

	}

	@Override
	public String suggestSimilarWord ( String inputWord ) throws NoSimilarWordFoundException 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
