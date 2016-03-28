/**
 * 
 */
package server;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jbelyeu
 *
 */
public class GetFileHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle (HttpExchange exchange) throws IOException
	{		
		String filename = exchange.getRequestURI().normalize().toString();
		filename = filename.substring(9);
		
		byte[] file = null;

		try 
		{
			if (filename != null)
			{
				Path path = Paths.get(filename);
				file = Files.readAllBytes(path);
			}
			else
			{
				throw new Exception("Could not validate filename " );
			}
		}
		catch (Exception e) 
		{
//          logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		exchange.getResponseBody().write(file);
		exchange.getResponseBody().close();
	}
}
