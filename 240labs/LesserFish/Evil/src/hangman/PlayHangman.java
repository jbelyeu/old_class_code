package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.awt.List;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class PlayHangman {

	/**
	 * @param args
	 * @throws GuessAlreadyMadeException 
	 */
	public static void main (String[] args) throws GuessAlreadyMadeException
	{		
		PlayHangman player = new PlayHangman();
		player.play(args);
	}
		
	private void play (String[] args) throws GuessAlreadyMadeException
	{
		hanger = new MyEvilHangman();
		int wordLength = 0, guesses = 0;
		File file = null;
		String correctWord = "";
		try
		{			
			file = new File(args[0]);
			wordLength = Integer.parseInt(args[1]);
			guesses = Integer.parseInt(args[2]);
			if (guesses < 1)
			{
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			System.out.println("Usage: java MyEvilHangman dictionary wordLength guesses");
		}
		
			usedLetters = new LinkedList<Character>();
			hanger.startGame(file, wordLength);
			
			//builds the string of dashes to represent the word be be guessed
			StringBuilder wordSoFar = new StringBuilder();
			for (Integer i = 0; i < wordLength; ++i)
			{
				wordSoFar.append("-");
			}
			
			//runs the turns
			for (int i = 0; i < guesses; ++i)
			{
				startTurnOutputter((guesses - i), wordSoFar.toString());
				char guess = getGuess();
				boolean lastTurn = (i == guesses -1);
				if (guess == '!')
				{
					i--;
					System.out.println();
					continue;
				}
				else
				{
					usedLetters.add(guess);
					Set<String> wordGroup = hanger.makeGuess(guess);

					wordSoFar = new StringBuilder( upDateWordPattern (guess, wordGroup.iterator().next() ) );
					correctWord = wordGroup.iterator().next();

					if (foundWord(wordGroup))
					{
						System.out.println ( "You Win!");
						System.out.println("The word was: " + correctWord);
						return;
					}
					
					String aWord = wordGroup.iterator().next();
					int count = 0;
					for (int j = 0; j < wordLength; ++j)
					{
						if (aWord.charAt(j) == guess)
						{
							count++;
						}
					}
					
					if (count > 0 && ! lastTurn)
					{
						if (count == 1) 
						{
							System.out.println("Yes, there is 1 " + guess);
						}
						else
						{
							System.out.println("Yes, there are " + count + " " + guess + "'s");
						}
					}
					else if (! lastTurn)
					{
						System.out.println("Sorry, there are no " + guess + "'s");
					}
					
				}
				if (! lastTurn)
				{
					System.out.println();
				}
			}
			System.out.println("You lose!");
			System.out.println("The word was: " + correctWord);
			
	}
	
	private void startTurnOutputter (int remainingGuesses, String wordSoFar)
	{
		if ( remainingGuesses == 1)
		{
			System.out.println("You have 1 guess left");
		}
		else
		{
			System.out.println("You have " + remainingGuesses + " guesses left");
		}
		
		System.out.print("Used letters: ");
		for (Character letter : usedLetters)
		{
			System.out.print(letter + " ");
		}
		System.out.println("\nWord: " + wordSoFar);
		System.out.print("Enter guess: ");
	}
	
	private char getGuess()
	{
		Scanner userInputGetter = new Scanner(System.in);
		String guessStr = userInputGetter.next().toLowerCase();
		char retChar = guessStr.charAt(0);
		
		if (guessStr.length() != 1)
		{
			System.out.println("Invalid input");
			retChar =  '!';
		}			
		if (! (retChar >= 'a' && retChar <= 'z'))
		{
			System.out.println("Invalid input");
			retChar = '!';
		}
		if ( usedLetters.contains( (Character) guessStr.charAt(0) ) )
		{
			System.out.print("\nYou already used that letter");
			retChar = '!';
		}

		return retChar;
	}
	
	private String upDateWordPattern (char guess, String newWordPattern)
	{
		StringBuilder wordSoFar = new StringBuilder();
		for (int i = 0; i < newWordPattern.length(); ++i)
		{
			if ( usedLetters.contains( newWordPattern.charAt(i) ) )
			{
				wordSoFar.append(newWordPattern.charAt(i));
			}
			else
			{
				wordSoFar.append("-");
			}
		}
		return wordSoFar.toString();
	}
	
	private boolean foundWord (Set<String> wordGroup)
	{
		if ( wordGroup.size() == 1 )
		{
			String word = wordGroup.iterator().next();
			
			for (int i = 0; i < word.length(); ++i)
			{
				if (! usedLetters.contains(word.charAt(i) ) )
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private LinkedList<Character> usedLetters;
	private EvilHangmanGame hanger;
}