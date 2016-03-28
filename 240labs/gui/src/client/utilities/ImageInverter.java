/**
 * 
 */
package client.utilities;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageInverter 
{
	
	public static void invert(BufferedImage image)
	{
		int width = image.getWidth();
		int height = image.getHeight();
		WritableRaster raster = image.getRaster();
		
		for (int i = 0; i < width; ++i)
		{
			for (int j = 0; j < height; ++j)
			{
				int[] pixelVals = raster.getPixel(i, j, (int[]) null);				
				pixelVals[0] = Math.abs(pixelVals[0] - 255);				
				raster.setPixel(i, j, pixelVals);
			}
		}		
	}
}
