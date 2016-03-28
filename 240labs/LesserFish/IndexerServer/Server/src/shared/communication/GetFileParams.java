/**
 * 
 */
package shared.communication;

/**
 * @author jbelyeu
 *
 */
public class GetFileParams
{
	String fileName;
	String username;
	String password;
	
	/**
	 * @param fileName
	 * @param username
	 * @param password
	 */
	public GetFileParams(String fileName, String username, String password)
	{
		this.fileName = fileName;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 
	 */
	public GetFileParams()
	{}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

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
	
	
	
}
