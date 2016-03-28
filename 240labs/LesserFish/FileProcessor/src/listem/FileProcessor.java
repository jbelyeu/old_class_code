package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FileProcessor 
{
	protected ArrayList<File> selectFiles (File directory, String fileSelectionPattern, boolean recursive) //recursive
	{
		//needs to go through all files and get the right ones. Recursive
		
		ArrayList<File> selectedFiles = new ArrayList<File>();
		File[] files = directory.listFiles();
		
		Pattern pattern = Pattern.compile(fileSelectionPattern);
		Matcher matcher = null;

		//loop through all of the files
		if (files != null)
		{
			for (File file : files)
			{
				//if the file is actually a directory and recursive is true, pass the file into this function again
				//and put the returned files into the array list
				if (file.isDirectory() && recursive)
				{
					selectedFiles.addAll(selectFiles(file, fileSelectionPattern, recursive));
				}
				
				matcher = pattern.matcher(file.getName());
				
				//if the file is a file and the name matches the regex expression, put it in the array list
				if (file.isFile() && matcher.matches())
				{
					selectedFiles.add(file);
				}
			}
			return selectedFiles;
		}
		else
		{
			return null;
		}
	} 
	
	protected void processFiles(ArrayList<File> files, String searchPattern) throws FileNotFoundException
	{
		if (files != null)
		{	
			for (File file : files)
			{
				Scanner scanner = new Scanner(file);

				while (scanner.hasNextLine())
				{
					processLine (scanner.nextLine(), file, searchPattern);
				}
				reset(false);
				scanner.close();
			}
		}
	}
	
	protected abstract void processLine (String line, File file, String searchPattern);
	protected abstract void reset (boolean end);	
}
