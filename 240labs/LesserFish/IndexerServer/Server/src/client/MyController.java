/**
 * 
 */
package client;

import java.util.Arrays;
import java.util.LinkedList;

import shared.communication.GetBatchParams;
import shared.communication.GetBatchResult;
import shared.communication.GetFieldsParams;
import shared.communication.GetFieldsResult;
import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.communication.GetSampleImageParams;
import shared.communication.GetSampleImageResult;
import shared.communication.SearchParams;
import shared.communication.SearchResult;
import shared.communication.SubmitBatchParams;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;
import shared.model.FieldValue;
import client.communication.ClientCommunicator;

/**
 * @author jbelyeu
 *
 */
public class MyController 
{
	private ClientCommunicator communicator;
	
	public MyController ()
	{
		communicator = new ClientCommunicator();
	}
	
	public String validateUser(String[] userParams)
	{
		String username = userParams[0];
		String password = userParams[1];
		String host = userParams[2];
		String port = userParams[3];
		
		ClientCommunicator communicator = new ClientCommunicator();
		ValidateUserResult result = null;
		boolean fail = false;
		try
		{
			result = communicator.validateUser(new ValidateUserParams(username, password), port, host);
		}
		catch (Exception e)
		{
			fail = true;
			return "FAILED\n";
		}
		
		if (result != null)
		{
			return (result.toString());
		}
		else 
		{
			return ("FALSE\n");
		}
	}
	
	public String getProjects(String[] userParams)
	{
		String host = userParams[2];
		String port = userParams[3];
		
		GetProjectsParams params = new GetProjectsParams(userParams[0], userParams[1]);
		
		ClientCommunicator communicator = new ClientCommunicator();
		GetProjectsResult result = null;
		
		try
		{
			result = communicator.getProjects(params, port, host);
		}
		catch (Exception e)
		{
			return ("FAILED\n");
		}	
		
		if (result != null)
		{
			return (result.toString());
		}
		else
		{
			return ("FAILED\n");
		}
	}
//	
//	private void getSampleImage()
//	{
//		String[] userParams = this.getView().getParameterValues();
//		String port = this.getView().getPort();
//		String host = this.getView().getHost();
//		GetSampleImageParams params = new GetSampleImageParams (userParams[0], userParams[1], Integer.parseInt(userParams[2])); 
//		params.setHost(host);
//		params.setPort(port + "/GetFile/");
//		
//		ClientCommunicator communicator = new ClientCommunicator();
//		GetSampleImageResult result = null;
//		
//		try
//		{
//			result = communicator.getSampleImage(params, port, host);
//		}
//		catch (Exception e)
//		{
//			getView().setResponse("FAILED\n");
//		}	
//		getView().setResponse("FAILED\n");
//		
//		if (result != null && result.toString() != null )
//		{
//			if (result.toString() != "")
//			{
//				getView().setResponse(result.toString());
//			}
//		}
//		
//	}
//	
//	private void downloadBatch() 
//	{
//		String[] userParams = this.getView().getParameterValues();
//		String port = this.getView().getPort();
//		String host = this.getView().getHost();
//		int projectID = -1;
//		
//		if (userParams[2].length() > 0)
//		{
//			projectID = Integer.parseInt(userParams[2]);
//		}
//		else
//		{
//			getView().setResponse("FAILED\n");
//			return;
//		}
//		
//		GetBatchParams params = new GetBatchParams(userParams[0], userParams[1], projectID);
//		params.setHost(host);
//		params.setPort(port + "/GetFile/");
//		
//		ClientCommunicator communicator = new ClientCommunicator();
//		GetBatchResult result = null;
//
//		try
//		{
//			result = communicator.getBatch(params, port, host);
//		}
//		catch (Exception e)
//		{
//			getView().setResponse("FAILEDe\n");
//		}	
//
//		if (result != null)
//		{
//			getView().setResponse(result.toString());
//		}
//		else
//		{
//			getView().setResponse("FAILED\n");
//		}		
//	}
//	
	public String getFields(String[] userParams)
	{
		String host = userParams[3];
		String port = userParams[4];
		
		int projectID = -1;
		if (userParams[2].length() > 0)
		{
			projectID = Integer.parseInt(userParams[2]);
		}
		
		GetFieldsParams params = new GetFieldsParams(userParams[0], userParams[1], projectID);
		params.setHost(host);
		params.setPort(port + "/GetFile/");
		
		ClientCommunicator communicator = new ClientCommunicator();
		GetFieldsResult result = null;
		
		try
		{
			result = communicator.getFields(params, port, host);
		}
		catch (Exception e)
		{
			return ("FAILED\n");
		}	
		
		if (result != null && result.getFields() != null && result.getFields().size() > 0) 
		{
			return (result.toString());
		}
		else
		{
			return ("FAILED\n");
		}	
	}
//	
//	private void submitBatch() 
//	{
//		String[] userParams = this.getView().getParameterValues();
//		String port = this.getView().getPort();
//		String host = this.getView().getHost();		
//		String rawVals = userParams[3];
//		
//		String[] tuples = rawVals.split(";");
//		LinkedList<FieldValue> values = new LinkedList<FieldValue>();
//
//		for (String tuple : tuples)
//		{		
//			String[] fieldVals = tuple.split(",");
//			for (int i = 0; i < fieldVals.length; ++i)
//			{
//				FieldValue valueObject = new FieldValue();
//				valueObject.setRecordText(fieldVals[i]);
//				valueObject.setRecordNumber(i+1);
//				valueObject.setFieldsGivenByUser(fieldVals.length);
//				values.add(valueObject);				
//			}
//		}
//		
//		ClientCommunicator communicator = new ClientCommunicator();
//		SubmitBatchParams params = new SubmitBatchParams(userParams[0], userParams[1], Integer.parseInt(userParams[2]), values);
//		
//		boolean success = true;
//		try
//		{
//			if (! communicator.SubmitBatch(params, port, host))
//			{
//				success = false;
//			}
//
//		}
//		catch (Exception e)
//		{
//			getView().setResponse("FAILED\n");
//		}		
//		
//		if (success)
//		{
//			getView().setResponse("TRUE\n");
//		}
//		else
//		{
//			getView().setResponse("FAILED\n");
//		}
//	}
//	
//	private void search() 
//	{
//		String[] userParams = this.getView().getParameterValues();
//		String port = this.getView().getPort();
//		String host = this.getView().getHost();
//		
//		String[] fields = userParams[2].split(",");
//		String[] searchValues = userParams[3].split(",");
//		
//		SearchParams params = new SearchParams(userParams[0], userParams[1], new LinkedList<String> (Arrays.asList(fields)), 
//												new LinkedList<String> (Arrays.asList(searchValues) ) );
//		params.setHost(host);
//		params.setPort(port + "/GetFile/");
//		
//		SearchResult result = null;
//		ClientCommunicator communicator = new ClientCommunicator();
//		
//		try
//		{
//			result = communicator.search(params, port, host);
//		}
//		catch (Exception e)
//		{
//			getView().setResponse("FAILED\n");
//		}			
//		
//		if (result != null && result.getRecords().size() > 0)
//		{
//			getView().setResponse(result.toString());
//		}
//		else
//		{
//			getView().setResponse("FAILED\n");
//		}		
//	}
}
