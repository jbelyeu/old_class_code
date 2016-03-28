/**
 * 
 */
package shared.communication;

/** Wrapper class for parameters passed to ValidateUser()
 * @author jbelyeu
 *
 */
public class ValidateUserParams 
{
	String username;
	String password;
	
	/** Creates fully initialized ValidateUserParams object
	 * 
	 * @param username (String)
	 * @param password
	 */
	public ValidateUserParams(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}
	
	/** Creates new ValidateUserParams object
	 * 
	 */
	public ValidateUserParams()
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
		return "ValidateUserParams [username=" + username + ", password="
				+ password + "]";
	}
	
	

}
