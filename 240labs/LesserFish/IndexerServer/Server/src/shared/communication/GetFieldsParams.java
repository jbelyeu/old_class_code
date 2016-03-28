package shared.communication;

/** Wrapper class for parameters passed to GetFields()
 * 
 * @author jbelyeu
 *
 */
public class GetFieldsParams
{
	private String username;
	private String password;
	private Integer projectID = -1;
	private String host;
	private String port;
	
	/**Creates fully initialized GetFieldsResult wrapper object
	 * @param user
	 * @param password
	 * @param projectID
	 */
	public GetFieldsParams(String username, String password, Integer projectID)
	{
		this.username = username;
		this.password = password;
		this.projectID = projectID;
	}
	
	/**Creates new GetFieldsResult wrapper object
	 *
	 */
	public GetFieldsParams()
	{}

	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String user) 
	{
		this.username = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}

	/**
	 * @return the projectID
	 */
	public Integer getProjectID() 
	{
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(Integer projectID) 
	{
		this.projectID = projectID;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}	
}
