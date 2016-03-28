/**
 * 
 */
package shared.communication;

/** Wrapper class for parameters for getSampleImage()
 * @author jbelyeu
 *
 */
public class GetSampleImageParams 
{
	private String username;
	private String password;
	private Integer ProjectID;
	private String host;
	private String port;
	
	/**Creates fully initialized GetSampleImageParams wrapper object
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public GetSampleImageParams(String username, String password, Integer projectID)
	{
		this.username = username;
		this.password = password;
		ProjectID = projectID;
	}
	 
	/**Creates new GetSampleImageParams object
	 * 			
	 */
	public GetSampleImageParams()
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
	public void setUsername(String username)
	{
		this.username = username;
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
		return ProjectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(Integer projectID) 
	{
		ProjectID = projectID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GetSampleImageParams [username=" + username + ", password="
				+ password + ", ProjectID=" + ProjectID + "]";
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
