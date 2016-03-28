package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrepImp extends FileProcessor implements Grep 
{
	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, String substringSelectionPattern, boolean recursive)
	{
		GrepMap = new HashMap<File, List<String>>();
		
		//first get an array list of the files
		ArrayList<File> files = selectFiles(directory, fileSelectionPattern, recursive);
		if (files != null)
		{
			try 
			{
				processFiles(files, substringSelectionPattern);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		
		Map RetMap = GrepMap;
		reset(true);
		return RetMap;
	}
	
	@Override
	protected void processLine(String line, File file, String searchPattern) 
	{	
		Pattern pattern = Pattern.compile(searchPattern);
		Matcher matcher = pattern.matcher(line);	
		
		if (matcher.find())
		{
			if (! GrepMap.containsKey(file))
			{
				List<String> resultsList = new ArrayList<String>();
				GrepMap.put(file, resultsList);
			}
			
			List<String> contents = GrepMap.get(file);
			contents.add(line);
			GrepMap.put(file, contents);
		}
	}
	
	@Override
	protected void reset(boolean end)
	{
		if (end)
		{
			GrepMap = new HashMap<File, List<String>>();
		}
	}
	
	protected Map<File, List<String>> GrepMap;
}