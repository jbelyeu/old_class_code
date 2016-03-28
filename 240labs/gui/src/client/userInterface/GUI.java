/**
 * 
 */
package client.userInterface;

import client.userInterface.login.Login;

/**
 * @author jon
 * TODO: this class should handle the main-thread operations of starting and stopping the different windows
 * Use it to call the class for the login window (JFrame), the welcome message (dialog box), and the indexer window
 *
 */
public class GUI 
{
	/**This function should escape static land ASAP
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String host = "localhost";
		String port = "8080";
		if (args.length >= 2)
		{
			host = args[0];
			port = args[1];
		}
		GUI gui = new GUI(host, port);
	}
	
	/**Generate the login window
	 * 
	 * @param host
	 * @param port
	 */
	public GUI (String host, String port)
	{
		Login login = new Login(host, port);		
		login.setVisible(true);
	}
}
