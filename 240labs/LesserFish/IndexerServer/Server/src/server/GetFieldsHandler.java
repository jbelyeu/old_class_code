/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import server.facade.ServerFacade;
import shared.communication.*;
import shared.model.*;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class GetFieldsHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ArrayList<Field> fields = null;
		GetFieldsParams params = (GetFieldsParams)this.xmlStream.fromXML(exchange.getRequestBody());

		try 
		{
			User user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			if (user != null)
			{
				if (params.getProjectID() != -1)
				{
					fields = ServerFacade.getFields(params.getProjectID(), params.getHost(), params.getPort());
				}
				else
				{
					fields = ServerFacade.getFields(params.getHost(), params.getPort());
				}
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
	
		GetFieldsResult result = new GetFieldsResult();
		if (fields != null)
		{
		
			result.setFields(fields);
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
