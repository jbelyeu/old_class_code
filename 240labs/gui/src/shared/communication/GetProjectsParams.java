/**
 * 
 */
package shared.communication;

/**Wrapper for parameters passed to getProjects()
 * @author jbelyeu
 *
 */
public class GetProjectsParams 
{
	String username;
	String password;
	
	/** Creates fully initialized GetProjectsParams object
	 * @param username
	 * @param password
	 */
	public GetProjectsParams(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}
	
	/**Creates new GetProjectsParams object
	 * 
	 */
	public GetProjectsParams()
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "GetProjectsParams [username=" + username + ", password="
				+ password + "]";
	}
	
	
}
