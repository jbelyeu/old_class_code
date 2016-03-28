/**
 * 
 */
package client.facade;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.communication.GetSampleImageParams;
import shared.communication.GetSampleImageResult;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;
import client.communication.ClientCommunicator;
import client.communication.ClientException;

/**
 * @author jon
 *
 */
public class ClientFacade
{
	ClientCommunicator communicator;
	
	public ClientFacade()
	{
		communicator = new ClientCommunicator();
	}
	
	
	public ValidateUserResult validateUser(ValidateUserParams params, String host, String port)
	{
		ValidateUserResult result = null;
		try
		{
			result = communicator.validateUser(params, port, host);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public GetProjectsResult getProjects (GetProjectsParams params, String host, String port)
	{
		GetProjectsResult result = null;
		try
		{
			result = communicator.getProjects(params, port, host);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public BufferedImage getSampleImage(GetSampleImageParams params, String host, String port)
	{
		GetSampleImageResult result = null;		
		String[] urlArray = params.getPort().split("/");
		params.setPort(urlArray[0] + "/GetFile/");
		
		try 
		{
			result = communicator.getSampleImage(params, host, port);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		
		BufferedImage image = null;
		try
		{
			String url = result.getImageURL();
			image = ImageIO.read(communicator.getFile(url));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		return image;
	}
}
