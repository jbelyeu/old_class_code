/**
 * 
 */
package client.utilities;

import client.userInterface.IndexerWindow;

/**
 * @author jon
 *
 */
public class BatchState
{
	private String host;
	private String port;
	private String username;
	private String password;
	private IndexerWindow indexerWindow;
	
	public BatchState(String host, String port)
	{
		this.host = host;
		this.port = port;
	}
	
	public void save()
	{
		System.out.println("BatchState Save()");
	}

	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * @return the indexerWindow
	 */
	public IndexerWindow getIndexerWindow()
	{
		return indexerWindow;
	}

	/**
	 * @param indexerWindow the indexerWindow to set
	 */
	public void setIndexerWindow(IndexerWindow indexerWindow)
	{
		this.indexerWindow = indexerWindow;
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
}
