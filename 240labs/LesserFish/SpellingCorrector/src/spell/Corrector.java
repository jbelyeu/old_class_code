package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import spell.Dictionary.MyNode;

public class Corrector implements SpellCorrector 
{
//no overridden constructor yet. Necessary?
	
	@Override
	public void useDictionary( String dictionaryFileName) throws IOException 
	{
		// this function creates the dictionary and loads it
		File file = new File(dictionaryFileName);
		Scanner scanner = new Scanner(file);
		dictionary = new Dictionary();
		while (scanner.hasNext())
		{
			dictionary.add(scanner.next());
		}
		scanner.close();
	}

	@Override
	public String suggestSimilarWord ( String inputWord ) throws NoSimilarWordFoundException 
	{
		MyNode returnNode = null;
		
		//first, search for word
		returnNode = (MyNode) dictionary.find(inputWord);
		if (returnNode != null)
		{
			return inputWord;
		}
		else //then, if not found, call functions to generate the ED1 words to search for
		{
			String[] deletedVars = deleteVariants(inputWord);			
			String[] transposedVariants = transposeVariants(inputWord);
			String[] alteredVariants = alterVariants(inputWord);
			String[] insertedVariants = insertVariants(inputWord);
			
			Map<String, Integer> foundWords = findcaller(deletedVars);
			foundWords.putAll(findcaller(transposedVariants));
			foundWords.putAll(findcaller(alteredVariants));
			foundWords.putAll(findcaller(insertedVariants));
			
			//then, if not found, loop through the edit distance 1 words to generate the ED2 words to search for
			if (foundWords.isEmpty()) 
			{
				foundWords.putAll(generateVarWords(deletedVars));
				foundWords.putAll(generateVarWords(transposedVariants));
				foundWords.putAll(generateVarWords(alteredVariants));
				foundWords.putAll(generateVarWords(insertedVariants));
			}

			String suggestion = chooseSuggestion(foundWords); 
			if (suggestion != null)
			{
				return suggestion;	
			}
			else
			{
				throw new NoSimilarWordFoundException();
			}
		}
	}
	
	private String chooseSuggestion (Map<String, Integer> words)
	{
		Map.Entry<String, Integer> maxFreq = null;
		
		//finds greatest frequency of word. Very inefficient
		for (Map.Entry<String, Integer> entry : words.entrySet())
		{
			if (maxFreq == null || maxFreq.getValue() < entry.getValue())
			{
				maxFreq = entry;
			}
		}
		//makes set of word of same frequency from map
		
		HashSet<String> finalOptions = new HashSet<String>();
		for (Map.Entry<String, Integer> entry : words.entrySet())
		{
			if (entry.getValue() == maxFreq.getValue())
			{
				finalOptions.add(entry.getKey());
			}
		}
		if (! finalOptions.isEmpty())
		{
			return (String)finalOptions.iterator().next(); //not sure if this will return the first alphabetically, but it might...
		}
		else
		{
			return null;
		}
	}
	
	private Map<String, Integer> generateVarWords(String[] words)
	{
		Map<String, Integer> foundWords = new HashMap<String, Integer>();
		
		for (String word : words)
		{	
			String[] deletedVars = deleteVariants(word);
			String[] transposedVariants = transposeVariants(word);
			String[] alteredVariants = alterVariants(word);
			String[] insertedVariants = insertVariants(word);
			
			foundWords.putAll(findcaller(deletedVars));
			foundWords.putAll(findcaller(transposedVariants));
			foundWords.putAll(findcaller(alteredVariants));
			foundWords.putAll(findcaller(insertedVariants));
		}
		return foundWords;
	}
	
	//calls the find method for each word in the arrays given it (the variant words already generated)
	private Map<String, Integer> findcaller(String[] words)
	{
		Map<String, Integer> variantWords = new HashMap<String, Integer>();
		
		if (words != null)
		{
			for (String word : words)
			{

				MyNode node = null;

				if (word != null)
				{
					node = (MyNode) dictionary.find(word);
				}
				if (node != null)
				{

					variantWords.put(word, node.getValue());
				}
			}
		}

		return variantWords;		
	}
	
	
	private String[] deleteVariants(String word)
	{
		String[] toReturn = null;
		if (word != null)
		{
			toReturn = new String[word.length()];
		}
		else
		{
			return null;
		}
		for (int i = 0; i < word.length(); ++i)
		{
			String newWord = (word.substring(0, i) + word.substring(i +1, word.length()));
			
			if (newWord.length() > 0)
			{
				toReturn[i] = newWord;
			}
			
		}

		return toReturn;
	}
	
	private String[] transposeVariants(String word)
	{
		if (word != null) 
		{
			if (word.length() < 2)
			{
				String[] toReturn = new String[1];
				toReturn[0] = word;
				return toReturn;
			}
			
			String[] toReturn = new String[word.length() -1 ];
			
			for (int i = 0; i < word.length() -1; ++i)
			{
				String beginning = "", middle1 = "", middle2 = "", end = ""; //these are the first unaltered, first altered, second altered, and second unaltered parts of the new string
				beginning = word.substring(0, i);
				middle1 = word.substring(i, i +1);
				middle2 = word.substring(i +1, i +2);
				
				if ((word.length() - i) > 2)
				{
					end = word.substring(i +2);
				}
				toReturn[i] = beginning + middle2 + middle1 + end;
			}
			return toReturn;
		}
		return null;
	}	
	
	private String[] alterVariants(String word)
	{
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", ""};
		if (word != null)
		{
			String[] toReturn = new String[26 * word.length()];
			
			int index = 0;
			
			for (int i = 0; i < word.length(); ++i)
			{
				for (int j = 0; j < letters.length -1; ++j)
				{
					String end = "";
					if (word.length() >= i+1)
					{
						end = word.substring(i + 1);
					}
	
					toReturn[index] = word.substring(0, i) + letters[j] + end;
					index++;
				}
			}
			return toReturn;
		}
		else
		{
			return null;
		}
	}	

	private String[] insertVariants(String word)
	{
		String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
				"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", ""};
		if ( word != null )
		{
			String[] toReturn = new String[26 * (word.length()+1)];
			int index = 0;
			
			for (int i = 0; i < word.length(); ++i) //loop builds all words except case of insertion after last letter in word
			{
				for (int j = 0; j < letters.length -1; ++j)
				{
					String beginning = "", end = "";
					
					beginning = word.substring(0, i);
					end = word.substring(i, word.length());
					toReturn[index] = beginning + letters[j] + end;
					index++;
				}
			}
	
			if (word.length() > 0)
			{
				for (int j = 0; j < letters.length -1; ++j) //loop builds only words where case of insertion is after last letter in word
				{
					toReturn[index++] = word + letters[j];
				}
			}
			
			return toReturn;
		}
		else
		{
			return null;
		}
	}
	
	private Trie dictionary;
}
