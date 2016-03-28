/**
 * 
 */
package server;

import com.sun.net.httpserver.HttpExchange;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.io.IOException;

import server.facade.ServerFacade;
import shared.model.*;
import shared.communication.*;

/**
 * @author jbelyeu
 *
 */
public class GetProjectsHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ArrayList<Project> projects = null;
		GetProjectsParams params = (GetProjectsParams) this.xmlStream.fromXML(exchange.getRequestBody());
		User user = null;
		
		try
		{
			user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			
			if (user != null)
			{
				projects = ServerFacade.getProjects();
			}
		}
		catch (Exception e) 
		{
//          logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
			
		GetProjectsResult result = null;
		if (user != null)
		{
			result = new GetProjectsResult();
			result.setProjects(projects);
		}		
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
