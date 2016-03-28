
package shared.communication;

import java.util.LinkedList;

/**Wrapper class for parameters for Search()
 * @author jbelyeu
 *
 */
public class SearchParams 
{
	private String username;
	private String password;
	private LinkedList<String> fields;
	private LinkedList<String> toFind;
	private String host;
	private String port;
	
	/** Creates fully initialized SearchParams wrapper object
	 * @param username
	 * @param password
	 * @param fields
	 * @param toFind
	 */
	public SearchParams(String username, String password, LinkedList<String> fields, LinkedList<String> toFind) 
	{
		this.username = username;
		this.password = password;
		this.fields = fields;
		this.toFind = toFind;
	}
	
	/** Creates new SearchParams wrapper object
	 *
	 */
	public SearchParams() 
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
	 * @return the fields
	 */
	public LinkedList<String> getFields() 
	{
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(LinkedList<String> fields) 
	{
		this.fields = fields;
	}

	/**
	 * @return the toFind
	 */
	public LinkedList<String> getToFind() 
	{
		return toFind;
	}

	/**
	 * @param toFind the toFind to set
	 */
	public void setToFind(LinkedList<String> toFind) 
	{
		this.toFind = toFind;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchParams [username=" + username + ", password=" + password
				+ ", fields=" + fields + ", toFind=" + toFind + "]";
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
