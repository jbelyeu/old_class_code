package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LineCounterImp extends FileProcessor implements LineCounter 
{
	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, boolean recursive)
	{
		LCounterMap = new HashMap<File, Integer>();

		//first get an array list of the files
		ArrayList<File> files = selectFiles(directory, fileSelectionPattern, recursive);
		if (files != null)
		{
			try 
			{
				processFiles(files, "");
			} 
			catch (FileNotFoundException e) 			{				e.printStackTrace();
			}
		}
		Map RetMap = LCounterMap;
		reset(true);
		return RetMap;
	}
	
	@Override
	protected void processLine(String line, File file, String searchPattern) 
	{
		NumberOfLines++;		
		LCounterMap.put(file, NumberOfLines); //will overwrite the filename each time, maybe bad		
	}
	
	@Override
	protected void reset(boolean end)
	{
		if (end)
		{
			LCounterMap = new HashMap<File, Integer>();		
		}
		NumberOfLines = 0;
	}
	
	int NumberOfLines;
	protected Map<File, Integer> LCounterMap;

}
