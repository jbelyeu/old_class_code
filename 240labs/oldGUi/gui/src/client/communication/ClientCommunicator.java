package client.communication;
import java.io.*;
import java.net.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.*;

/**Handles communication between the client and the database access classes
 * 
 * @author jbelyeu
 *
 */
public class ClientCommunicator 
{	
	private String urlPrefix;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private XStream xmlStream;
	
	/** Creates ClientCommunicator object
	 * 
	 */
	public ClientCommunicator()
	{
		xmlStream = new XStream(new DomDriver());
	}
	
	/**Checks user password and name
	 * 
	 * @param userName
	 * @param password
	 * @return ValidateUserResult
	 * @throws ClientException 
	 */
	public ValidateUserResult validateUser(ValidateUserParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		return (ValidateUserResult)doPost("/ValidateUser", params);
	}
	
	/**Gets all available projects
	 * 
	 * @param user
	 * @param password
	 * @return GetProjectsResult
	 */
	public GetProjectsResult getProjects (GetProjectsParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		return (GetProjectsResult)doPost("/GetProjects", params);
	}
	
	/**Gets a sample image from a project
	 * 
	 * @param user
	 * @param password
	 * @param projectID
	 * @return GetSampleImageResult
	 */
	public GetSampleImageResult getSampleImage(GetSampleImageParams params, String host, String port) throws Exception
	{
		urlPrefix = "http://" + params.getHost() + ":" + port;
		return (GetSampleImageResult)doPost("/GetSampleImage", params);	
	}
	
	/**Downloads a Batch from database 
	 * 
	 * @param user
	 * @param password
	 * @param projectID
	 * @return GetBatchResult
	 */
	public GetBatchResult getBatch(GetBatchParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		return (GetBatchResult)doPost("/DownLoadBatch", params);
	}
	
	/**Saves batch to the database
	 * 
	 * @param user
	 * @param password
	 * @param batchID
	 * @param values
	 */
	public boolean SubmitBatch(SubmitBatchParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		try
		{
			doPost("/SubmitBatch", params);	
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
		
	}
	
	/**Gets information about all fields in a project from the database
	 *  
	 * @param user
	 * @param password
	 * @param projectID
	 * @return
	 */
	public GetFieldsResult getFields(GetFieldsParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		return (GetFieldsResult)doPost("/GetFields", params);
	}
	
	/**Searches all indexed records for the specified strings
	 * 
	 * @param toFind
	 * @return
	 */
	public SearchResult search(SearchParams params, String port, String host) throws Exception
	{
		urlPrefix = "http://" + host + ":" + port;
		return (SearchResult)doPost("/Search", params);
	}
	
	public File getFile(String filepath) throws ClientException
	{
		File retrievedFile = null;
		String[] requestParams = filepath.split("/");
		String filename = requestParams[requestParams.length-1];
		
		try
		{
			URL url = new URL(filepath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();					
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
			{
				retrievedFile = new File(filename);
				OutputStream out = new FileOutputStream(retrievedFile);				
				
				byte[] buffer = new byte[1024];
				int length;
				while ( ( length = connection.getInputStream().read(buffer)) > 0 )
				{
					out.write(buffer,  0,  length);
				}
				out.close();
				connection.getInputStream().close();
			}
			else 
			{
				throw new ClientException(String.format("doGet failed: %s (http code %d)",
						filepath, connection.getResponseCode() ) );
			}
		}
		catch (IOException e) 
		{
			throw new ClientException(String.format("doGet failed: %s", e.getMessage()), e);
		}

		return retrievedFile;
	}
	
	/**
	 * 
	 * @param urlPath
	 * @param postData
	 * @return
	 * @throws ClientException
	 */
	private Object doPost(String urlPath, Object postData) throws Exception 
	{
		
		// Make HTTP POST request to the specified URL, 
		// passing in the specified postData object
		try 
		{
			URL url = new URL(urlPrefix + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
			
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) 
			{
				throw new ClientException(String.format("doPost failed: %s (http code %d)", 
													urlPath, connection.getResponseCode()));
			}
			else if (urlPath != "/SubmitBatch")
			{
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else
			{
				return null;
			}
		}
		catch (Exception e) 
		{
			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
}
