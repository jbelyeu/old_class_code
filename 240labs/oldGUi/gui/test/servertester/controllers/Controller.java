package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;
import shared.model.FieldValue;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser()
	{
		String[] userParams = this.getView().getParameterValues();
		String username = userParams[0];
		String password = userParams[1];
		
		String host = this.getView().getHost();
		String port = this.getView().getPort();
		
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
			getView().setResponse("FAILED\n");
		}
		
		if (result != null)
		{
			getView().setResponse(result.toString());
		}
		else if (! fail)
		{
			getView().setResponse("FALSE\n");
		}
	}
	
	private void getProjects()
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();
		GetProjectsParams params = new GetProjectsParams(userParams[0], userParams[1]);
		
		ClientCommunicator communicator = new ClientCommunicator();
		GetProjectsResult result = null;
		
		try
		{
			result = communicator.getProjects(params, port, host);
		}
		catch (Exception e)
		{
			getView().setResponse("FAILED\n");
		}	
		
		if (result != null)
		{
			getView().setResponse(result.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage()
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();
		GetSampleImageParams params = new GetSampleImageParams (userParams[0], userParams[1], Integer.parseInt(userParams[2])); 
		params.setHost(host);
		params.setPort(port + "/GetFile/");
		
		ClientCommunicator communicator = new ClientCommunicator();
		GetSampleImageResult result = null;
		
		try
		{
			result = communicator.getSampleImage(params, host, port);
		}
		catch (Exception e)
		{
			getView().setResponse("FAILED\n");
		}	
		getView().setResponse("FAILED\n");
		
		if (result != null && result.toString() != null )
		{
			if (result.toString() != "")
			{
				getView().setResponse(result.toString());
			}
		}
		
	}
	
	private void downloadBatch() 
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();
		int projectID = -1;
		
		if (userParams[2].length() > 0)
		{
			projectID = Integer.parseInt(userParams[2]);
		}
		else
		{
			getView().setResponse("FAILED\n");
			return;
		}
		
		GetBatchParams params = new GetBatchParams(userParams[0], userParams[1], projectID);
		params.setHost(host);
		params.setPort(port + "/GetFile/");
		
		ClientCommunicator communicator = new ClientCommunicator();
		GetBatchResult result = null;

		try
		{
			result = communicator.getBatch(params, port, host);
		}
		catch (Exception e)
		{
			getView().setResponse("FAILEDe\n");
		}	

		if (result != null)
		{
			getView().setResponse(result.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}		
	}
	
	private void getFields()
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();
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
			getView().setResponse("FAILED\n");
		}	
		
		if (result != null && result.getFields() != null && result.getFields().size() > 0) 
		{
			getView().setResponse(result.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}	
	}
	
	private void submitBatch() 
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();		
		String rawVals = userParams[3];
		
		String[] tuples = rawVals.split(";");
		LinkedList<FieldValue> values = new LinkedList<FieldValue>();

		for (String tuple : tuples)
		{		
			String[] fieldVals = tuple.split(",");
			for (int i = 0; i < fieldVals.length; ++i)
			{
				FieldValue valueObject = new FieldValue();
				valueObject.setRecordText(fieldVals[i]);
				valueObject.setRecordNumber(i+1);
				valueObject.setFieldsGivenByUser(fieldVals.length);
				values.add(valueObject);				
			}
		}
		
		ClientCommunicator communicator = new ClientCommunicator();
		SubmitBatchParams params = new SubmitBatchParams(userParams[0], userParams[1], Integer.parseInt(userParams[2]), values);
		
		boolean success = true;
		try
		{
			if (! communicator.SubmitBatch(params, port, host))
			{
				success = false;
			}

		}
		catch (Exception e)
		{
			getView().setResponse("FAILED\n");
		}		
		
		if (success)
		{
			getView().setResponse("TRUE\n");
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() 
	{
		String[] userParams = this.getView().getParameterValues();
		String port = this.getView().getPort();
		String host = this.getView().getHost();
		
		String[] fields = userParams[2].split(",");
		String[] searchValues = userParams[3].split(",");
		
		SearchParams params = new SearchParams(userParams[0], userParams[1], new LinkedList<String> (Arrays.asList(fields)), 
												new LinkedList<String> (Arrays.asList(searchValues) ) );
		params.setHost(host);
		params.setPort(port + "/GetFile/");
		
		SearchResult result = null;
		ClientCommunicator communicator = new ClientCommunicator();
		
		try
		{
			result = communicator.search(params, port, host);
		}
		catch (Exception e)
		{
			getView().setResponse("FAILED\n");
		}			
		
		if (result != null && result.getRecords().size() > 0)
		{
			getView().setResponse(result.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}		
	}
}

