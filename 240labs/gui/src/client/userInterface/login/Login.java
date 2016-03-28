/**
 * 
 */
package client.userInterface.login;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.facade.ClientFacade;
import client.utilities.BatchState;
import shared.communication.ValidateUserParams;
import shared.communication.ValidateUserResult;

/**
 * @author jon
 *
 */
public class Login extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private String host;
	private String port;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private BatchState batchState;
	
	public Login (String host, String port)
	{
		this.host = host;
		this.port = port;
		this.setTitle("Login to Indexer");
		this.setPreferredSize(new Dimension(375, 92));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.batchState = new BatchState(host, port);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel usernameArea = new JPanel();
		usernameArea.setLayout(new BoxLayout(usernameArea, BoxLayout.LINE_AXIS));
		
		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(300, 15));
		usernameArea.add(new JLabel(" Username: "));
		usernameArea.add(usernameField);
		usernameArea.add(Box.createRigidArea(new Dimension(10, 15)));
		
		JPanel passwordArea = new JPanel();
		passwordArea.setLayout(new BoxLayout(passwordArea, BoxLayout.LINE_AXIS));
		
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(400, 15));
		passwordArea.add(new JLabel(" Password: "));
		passwordArea.add(Box.createRigidArea(new Dimension(2, 10)));
		passwordArea.add(passwordField);
		passwordArea.add(Box.createRigidArea(new Dimension(10, 15)));
		passwordField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) 
			{
	            if (e.getKeyCode() == KeyEvent.VK_ENTER)
	            {
	            	attemptLogin();
	            }
	         }  
		});
		
		JButton loginButton = new JButton();
		loginButton.add(new JLabel("Log In "));
		loginButton.setPreferredSize(new Dimension(60, 40));
		
		
		loginButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent event)
			{
				attemptLogin();
			}
		});
		
		JButton exitButton = new JButton();
		exitButton.setPreferredSize(new Dimension(60, 40));
		exitButton.add(new JLabel("Exit "));
		
		exitButton.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent event)
			{
				exit();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
				
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension (20, 30)));
		buttonPanel.add(exitButton);
		
		panel.add(Box.createRigidArea(new Dimension (300, 5)));
		panel.add(usernameArea);
		panel.add(Box.createRigidArea(new Dimension (300, 5)));
		panel.add(passwordArea);
		panel.add(Box.createRigidArea(new Dimension (300, 5)));
		panel.add(buttonPanel);		

		this.add(panel);
		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
		
		//this only works on one screen. When there are multiple displays it looks weird
		this.setLocationRelativeTo(null);	
		
		this.usernameField.setText("sheila");
		this.passwordField.setText("parker");
	}
	
	
	/** Calls function in the client Facade to query the database and check validity of 
	 * the user, then proceed as validity directs
	 * 
	 */
	protected void attemptLogin()
	{
		String username = this.usernameField.getText();
		String password = new String(this.passwordField.getPassword());
		ValidateUserParams params = new ValidateUserParams(username, password);
		
		ClientFacade facade = new ClientFacade();
		ValidateUserResult result = facade.validateUser(params, this.host, this.port);
		if (result == null || ! result.getValid())
		{
			LoginFailedDialog dialog = new LoginFailedDialog(); 
		}
		else
		{
			batchState.setPassword(password);
			batchState.setUsername(username);
			LoginSucceededDialog dialog = new LoginSucceededDialog(result.getFirstName() + " " 
												+ result.getLastName(), result.getNumRecords(), batchState);
			this.dispose();
		}
	}
	
	/**User clicked exit, close
	 * 
	 */
	protected void exit()
	{
		System.exit(0);
	}
}
