/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import shared.communication.GetSampleImageParams;
import shared.communication.GetSampleImageResult;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class GetSampleImageHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		String imageURL = null;
		User user = null;
		GetSampleImageParams params = (GetSampleImageParams)this.xmlStream.fromXML(exchange.getRequestBody());
		try
		{
			user = ServerFacade.validateUser(params.getUsername(), params.getPassword());
			
			if (user != null)
			{
				imageURL = ServerFacade.getSampleImage(params.getProjectID(), params.getHost(), params.getPort());
			}
		}
		catch (Exception e) 
		{
//            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
			
		try
		{
			GetSampleImageResult result = null;
			if (user != null)
			{
				result = new GetSampleImageResult();
				result.setImageURL(imageURL);
			}
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		catch (Exception e) 
		{
//            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
		
	}
}
