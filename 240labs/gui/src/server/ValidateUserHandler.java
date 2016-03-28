/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;
import shared.model.User;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author jbelyeu
 *
 */
public class ValidateUserHandler extends SuperHandler
{

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		ValidateUserParams params = (ValidateUserParams)this.xmlStream.fromXML(exchange.getRequestBody());

		String username = params.getUsername();
		String password = params.getPassword();
		User user = new User();
		
		try
		{
			user = ServerFacade.validateUser(username, password);
		}
		catch (Exception e) 
		{
//            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		ValidateUserResult result = new ValidateUserResult(); 
		if (user != null)
		{	
			result.setFirstName(user.getFirstName());
			result.setLastName(user.getLastName());
			result.setNumRecords(user.getRecordsIndexed());
			result.setValid(true);
		}
		else
		{
			result.setValid(false);
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		this.xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
