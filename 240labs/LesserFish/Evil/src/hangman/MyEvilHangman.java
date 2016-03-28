package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MyEvilHangman implements EvilHangmanGame
{
	@Override
	public void startGame(File dictionary, int wordLength) 
	{
		try
		{
			if (! (dictionary.isFile() && dictionary.canRead() && wordLength >= 2 ) )
			{
				throw new InvalidArgumentException();
			}
			else
			{
				this.dictionarySet = new HashSet<String>();
				this.wordLength = wordLength;
				loadDictionary(dictionary);
			}
		}
		catch (Exception e)
		{
			System.out.println("Usage: java MyEvilHangman dictionary wordLength guesses");
		}
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException 
	{
		Map<String, HashSet<String>> wordGroupsMap = buildWordGroups(guess);
		pickWordGroup(wordGroupsMap);
		return dictionarySet;
	}
	
	private void pickWordGroup (Map<String, HashSet<String>> wordGroupsMap)
	{
		//first, check the set cardinality and return greatest if one greater than all others
		Set<String> keys =  wordGroupsMap.keySet();
		String finalKey = "";
		Integer maxLength = 0;
		Map<Integer, Set<String>> groupKeysBySize = new HashMap<Integer, Set<String>>();
		
		for (String key : keys)
		{
			//get the number of words in each of the sets in the map
			Integer size = wordGroupsMap.get(key).size();
			
			if (! groupKeysBySize.containsKey(size))
			{
				groupKeysBySize.put(size, new HashSet<String>());
			}
			
			Set<String> sameSizeSet = groupKeysBySize.get(size);
			sameSizeSet.add(key);
			groupKeysBySize.put(size, sameSizeSet);
			
			if (size > maxLength)
			{
				finalKey = key;
				maxLength = size;
			}
		}
	
		//if there is only one key in the groups map at the largest size (most words in set), that is the correct set
		if (groupKeysBySize.get(maxLength).size() == 1)
		{
			dictionarySet = wordGroupsMap.get(finalKey); 
		}
		else
		{
			advancedPickWordGroup(wordGroupsMap, groupKeysBySize.get(maxLength));
		}
	}
	
	private void advancedPickWordGroup (Map<String, HashSet<String>> wordGroupsMap, Set<String> keys)
	{
		//build a string which has no matches of this letter. 
		StringBuilder noLetterInstance = new StringBuilder();
		for (int i = 0; i < wordLength; ++i)
		{
			noLetterInstance.append("-");
		}
		
		//If there is a key in the set to match it, use that set
		if ( keys.contains (noLetterInstance.toString() ) )
		{
			dictionarySet = wordGroupsMap.get(noLetterInstance.toString());
		}
		else //choose set with fewest matches
		{
			dictionarySet = wordGroupsMap.get(letterInstanceCom(keys));
		}
	}

	private String letterInstanceCom (Set<String> keys)
	{
		Integer leastOccurrences = 100000;
		Map<Integer, Set<String>> keysByOccurFreq = new HashMap<Integer, Set<String>>();
		
		for (String key : keys)
		{
			int occurrences = key.replace("-", "").length();
			if (! keysByOccurFreq.containsKey(occurrences))
			{
				Set<String> keysOccurrences = new HashSet<String>();
				keysByOccurFreq.put(occurrences, keysOccurrences);
			}
			Set<String> keysOccurrences = keysByOccurFreq.get(occurrences);
			keysOccurrences.add(key);
			keysByOccurFreq.put(occurrences, keysOccurrences);
			
			if (occurrences < leastOccurrences )
			{
				leastOccurrences = occurrences;
			}
		}
		
		//if there's only one string in the set with the smallest number of occurrences of the guess, return that string
		//as the key. Otherwise, keep going
		Set<String> refKeys = keysByOccurFreq.get(leastOccurrences);
		
		if (refKeys.size() == 1)
		{
			return refKeys.iterator().next();
		}
	
		Set<String> strings = rightMost(refKeys, 0);
		int ignorage = 1;

		while (strings.size() > 1)
		{
			strings = rightMost(refKeys, ignorage++);
		}
		
		return strings.iterator().next();
		
	}
	
	private Set<String> rightMost(Set<String> keys, int ignoreNum)
	{
		Map<Integer, Set<String>> rightMostDis = new HashMap<Integer, Set<String>>();
		int closest = 100000;
		for (String key : keys)
		{
			System.out.println("key: " + key);
			for (Integer i = key.length() -1; i > 0; --i)
			{
				if (key.charAt(i) != '-')
				{
					if (ignoreNum <= 0)
					{
						Integer distance = (key.length() -1) -i;
						
						if (! rightMostDis.containsKey(distance))
						{
							Set<String> strings = new HashSet<String>();
							rightMostDis.put(distance, strings);
						}						

						Set<String> strings = rightMostDis.get(distance);
						strings.add(key);
						System.out.println(strings);
						rightMostDis.put(distance, strings);
						if (distance < closest)
						{
							System.out.println("fish");
							closest = distance;
							break;
						}
					}
					else
					{
						ignoreNum--;
					}
				}
			}
		}

		System.out.println(rightMostDis.get(closest));
		return rightMostDis.get(closest);
	}
	
	private Map<String, HashSet<String>> buildWordGroups (char guess)
	{		
		Map<String, HashSet<String>> wordGroupsMap = new HashMap<String, HashSet<String>>();
		
		for (String word : dictionarySet)
		{
			if (word != null)
			{
				StringBuilder key = new StringBuilder();
				for (int i = 0; i < word.length(); ++i)
				{
					if (word.charAt(i) == guess)
					{
						key.append(guess);
					}
					else
					{
						key.append('-');
					}
				}

				if (! wordGroupsMap.containsKey(key.toString()))
				{
					HashSet<String> newSet = new HashSet<String>();
					wordGroupsMap.put(key.toString(), newSet);
				}			
				
				HashSet<String> wordSet = wordGroupsMap.get(key.toString());
				wordSet.add(word);
				wordGroupsMap.put(key.toString(), wordSet);
			}
		}
		return wordGroupsMap;
	}
	
	private void loadDictionary (File dictionary) throws FileNotFoundException
	{
		Scanner scanner = new Scanner (dictionary);
		while (scanner.hasNext())
		{
			String word = scanner.next();
			if (word.length() == this.wordLength)
			{	
				try
				{
					dictionarySet.add(word.toLowerCase());
				}
				catch (UnsupportedOperationException e)
				{
					System.out.println("Unsupported operation");
				}
				catch (ClassCastException f)
				{
					System.out.println("Class Cast Exception");
				}
				catch (NullPointerException g)
				{
					System.out.println("Null Pointer Exception");
				}
			}
		}
		scanner.close();
	}
	
	static class InvalidArgumentException extends Exception
	{
		private static final long serialVersionUID = 1L;
	}

	private int wordLength;
	private Set<String> dictionarySet;
}
