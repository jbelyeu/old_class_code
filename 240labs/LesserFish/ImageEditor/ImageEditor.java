import java.util.*;
import java.io.*;

public class ImageEditor 
{
	private int width;
	private int height;
	
	//function to read in the file and return array of pixels
	private Pixel[][] readIn (Scanner scanner)
	{
		scanner.useDelimiter("(\\s+)(#[^\\n]*)?\\s*|(#[^\\n]*)\\s+");
		scanner.next("P3");
		
		width = scanner.nextInt();
		height = scanner.nextInt();

		scanner.nextDouble();
		Pixel[][] pixelArray = new Pixel[height][width];	
		
		for (int i = 0; i < height; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				Pixel newPix = new Pixel();
				newPix.setRed(scanner.nextInt());
				newPix.setGreen(scanner.nextInt());
				newPix.setBlue(scanner.nextInt());
				pixelArray[i][j] = newPix;
			}
		}
		return pixelArray;
	}	
	
	//function to write file out
	private String buildOutput(Pixel[][] pixelArray)
	{
		StringBuilder output = new StringBuilder();
		output.append("P3\n" + width + "\n" + height + "\n 255");
		
		for (int i = 0; i < height; ++i)
		{
			output.append("\n");
			for (int j = 0; j < width; ++j)
			{
				output.append(pixelArray[i][j].getRed() + " ");
				output.append(pixelArray[i][j].getGreen() + " ");
				output.append(pixelArray[i][j].getBlue() + "\t");
			}
		}
		
		return output.toString();
	}
	
	private Pixel[][] grayscaleIt(Pixel[][] pixelArray)
	{
		for (int i = 0; i < height; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				int average = (pixelArray[i][j].getRed() + pixelArray[i][j].getGreen() +pixelArray[i][j].getBlue()) / 3;
				pixelArray[i][j].setRed(average);
				pixelArray[i][j].setGreen(average);
				pixelArray[i][j].setBlue(average);
			}
		}
		return pixelArray;
	}
	
	private Pixel[][] invertIt(Pixel[][] pixelArray)
	{		
		for (int i = 0; i < height; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				pixelArray[i][j].setRed(Math.abs(pixelArray[i][j].getRed() -255));
				pixelArray[i][j].setGreen(Math.abs(pixelArray[i][j].getGreen() -255));
				pixelArray[i][j].setBlue(Math.abs(pixelArray[i][j].getBlue() -255));
			}
		}
		return pixelArray;
	}
	
	private Pixel[][] embossIt(Pixel[][] pixelArray)
	{
		for (int i = height-1; i >= 0; --i)
		{
			for (int j = width -1; j >= 0 ; --j)
			{
				int redDif = 128, greenDif = 128, blueDif = 128;
				int maxDif = 128;
				
				if ((i -1) >= 0 && (j -1) >= 0)
				{
					redDif = pixelArray[i][j].getRed() - pixelArray[i -1][j -1].getRed();
					greenDif = pixelArray[i][j].getGreen() - pixelArray[i -1][j -1].getGreen();
					blueDif = pixelArray[i][j].getBlue() - pixelArray[i -1][j -1].getBlue();				
					
					if (Math.abs(redDif) >= Math.abs(greenDif) )
					{
						maxDif = redDif;
					}
					else
					{
						maxDif = greenDif;
					}					
					
					if ( Math.abs (blueDif ) > Math.abs(maxDif) )
					{
						maxDif = blueDif;
					}

					maxDif += 128;
					
					if (maxDif < 0)
					{
						maxDif = 0;
					}
					else if (maxDif > 255 )
					{
						maxDif = 255;
					}
				}
				pixelArray[i][j].setRed(maxDif);
				pixelArray[i][j].setGreen(maxDif);
				pixelArray[i][j].setBlue(maxDif);
			}
		}
		
		return pixelArray;
	}
	
	private Pixel[][] motionBlurIt(Pixel[][] pixelArray, int blurLen)
	{
		for (int i = 0; i < height; ++i)
		{
			for (int j = 0; j < width; ++j)
			{
				int distToEnd = width - j;
				int realBlurLen = distToEnd < blurLen ? distToEnd : blurLen;
				int avgRed = 0, avgGreen = 0, avgBlue = 0;
				
				for (int a = j; a < (j + realBlurLen); ++a)
				{
					avgRed += pixelArray[i][a].getRed();
					avgGreen += pixelArray[i][a].getGreen();
					avgBlue += pixelArray[i][a].getBlue();
				}
				pixelArray[i][j].setRed(avgRed / realBlurLen);
				pixelArray[i][j].setGreen(avgGreen / realBlurLen);
				pixelArray[i][j].setBlue(avgBlue / realBlurLen);
			}
		}
		return pixelArray;
	}
	
	//real main, called by the static main
	private void runEdit(String[] args) throws IOException
	{
		File fileIn = new File(args[0]);
		
		Scanner scanner = new Scanner(fileIn);
		Pixel[][] pixelArray = readIn(scanner);
		
		if ( args[2].equals("grayscale"))
		{
			pixelArray = grayscaleIt(pixelArray);
		}
		else if (args[2].equals("invert"))
		{
			pixelArray = invertIt(pixelArray);
		}
		else if (args[2].equals("emboss"))
		{
			pixelArray = embossIt(pixelArray);
		}
		else 
		{
			int blurNum = Integer.valueOf(args[3]);
			pixelArray = motionBlurIt(pixelArray, blurNum);
		}
		
		String output = buildOutput(pixelArray);
		PrintWriter pwriter = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
		pwriter.print(output.toString());
		pwriter.close();
	}	
	
	public static void main (String[] args)
	{
		try
		{
			ImageEditor editor = new ImageEditor();
			editor.runEdit(args);
		}
		catch (Exception ex)
		{
			System.out.println("USAGE: java ImageEditor in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		}
	}
	
}
