/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedList;

import server.facade.ServerFacade;
import shared.communication.SubmitBatchParams;
import shared.model.FieldValue;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class SubmitBatchHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		SubmitBatchParams params = (SubmitBatchParams)this.xmlStream.fromXML(exchange.getRequestBody());
		LinkedList<FieldValue> fieldValues = null;

		try 
		{
			User user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			if (user != null)
			{
				int newbatchID = Integer.parseInt(user.getCurrentBatch());
				
				if (newbatchID == params.getBatchID())
				{
					fieldValues = params.getFieldValues();
					int batchID = params.getBatchID();
					ServerFacade.submitBatch(fieldValues, batchID, user);
				}
				else
				{
					throw new Exception();
				}
			}
			else
			{
				throw new Exception("Could not validate user " + params.getUsername());
			}
		}
		catch (Exception e) 
		{
//          logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}			
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
	}
}
