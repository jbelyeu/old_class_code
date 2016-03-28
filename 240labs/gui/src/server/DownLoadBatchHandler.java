/**
 * 
 */
package server;

import java.io.IOException;



import java.net.HttpURLConnection;
import server.facade.ServerFacade;
import shared.communication.GetBatchParams;
import shared.communication.GetBatchResult;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class DownLoadBatchHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		GetBatchResult result = null;
		GetBatchParams params = (GetBatchParams)this.xmlStream.fromXML(exchange.getRequestBody());
		
		try 
		{
			User user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			
			if (user != null)
			{
				result = ServerFacade.downloadBatch(params.getProjectID(), user.getID(), params.getHost(), params.getPort());
			}
		}
		catch (Exception e) 
		{
//            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}

		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
