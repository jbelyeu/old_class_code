/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedList;

import server.facade.ServerFacade;
import shared.communication.*;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class SearchHandler extends SuperHandler
{
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		SearchParams params = (SearchParams)this.xmlStream.fromXML(exchange.getRequestBody());
		LinkedList<Record> records = null;

		try 
		{
			User user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			if (user != null)
			{				
				records = ServerFacade.search(params.getFields(), params.getToFind(), params.getHost(), params.getPort());
			}
			else
			{
				throw new Exception("Could not validate user " + user.getUsername());
			}
		}
		catch (Exception e) 
		{
//          logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
			
		SearchResult result = new SearchResult();
		result.setRecords(records);
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}