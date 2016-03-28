/**
 * 
 */
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*; 
import javax.swing.border.Border;

import servertester.controllers.Controller;
import servertester.controllers.IController;

/**
 * @author jbelyeu
 *
 */
public class SearchGUI 
{	
	public static void main(String args[])
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
            public void run() 
            {
            	SignInFrame window = new SignInFrame();
            	window.setVisible(true);
            }
        });
	}
}

@SuppressWarnings("serial")
class SignInFrame extends JFrame
{
	private MyController controller;
	private JTextArea usernameField;
	private JTextArea passwordField;
	private JTextArea hostField;
	private JTextArea portField;
	
	public SignInFrame()
	{
		this.controller = new MyController();
		
		// Set the window's title
		this.setTitle("Sign In");
		this.setPreferredSize(new Dimension(600, 200));
		this.setLayout(new BorderLayout());
		
		// Specify what should happen when the user clicks the window's close icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		JPanel centerPiece = new JPanel();
		
		hostField = new JTextArea(2, 30); //username entry field
		portField = new JTextArea(2, 30); //password entry field
		usernameField = new JTextArea(2, 30); //password entry field
		passwordField = new JTextArea(2, 30); //password entry field
		
		//add fields to input the port, host, user, pass
		centerPiece.add(hostField);
		centerPiece.add(new JLabel("-Host"));
		centerPiece.add(portField);
		centerPiece.add(new JLabel("-Port "));
		centerPiece.add(usernameField);
		centerPiece.add(new JLabel("-User"));
		centerPiece.add(passwordField);
		centerPiece.add(new JLabel("-Pass"));
		
		centerPiece.setBackground(Color.LIGHT_GRAY);
		
		// Add subcomponents to the window
		centerPiece.add(new JLabel("Enter User Name and Password"), BorderLayout.NORTH);
		this.add(centerPiece, BorderLayout.CENTER);
		
		MyButton button = new MyButton("Submit");
		
		//add a button to submit username and password
		this.add(button, BorderLayout.SOUTH);
		
		// Set the location of the window on the desktop
		this.setLocation(500, 400);

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
		
		//set minimum size to the preferred size; anything smaller is unusable
		this.setMinimumSize(this.getPreferredSize());
	}
	
	protected void processUser()
	{
		String[] params = new String[5];
		params[0] = usernameField.getText();
		params[1] = passwordField.getText();
		params[2] = hostField.getText();
		params[3] = portField.getText();

		String result = controller.validateUser(params);
		
		//color code shows what failed
		if (result == "FALSE\n")
		{
			this.usernameField.setBackground(Color.RED);
			this.passwordField.setBackground(Color.RED);
			this.hostField.setBackground(Color.WHITE);
			this.portField.setBackground(Color.WHITE);
		}					
		else if (result == "FAILED\n")
		{
			this.hostField.setBackground(Color.RED);
			this.portField.setBackground(Color.RED);
			this.usernameField.setBackground(Color.DARK_GRAY);
			this.passwordField.setBackground(Color.DARK_GRAY);
		}
		else
		{
			this.hostField.setBackground(Color.WHITE);
			this.portField.setBackground(Color.WHITE);
			this.usernameField.setBackground(Color.WHITE);
			this.passwordField.setBackground(Color.WHITE);
			params[4] = "Projects";
			this.setVisible(false);
			this.dispose();
			ProjectGetterFrame projectGetter = new ProjectGetterFrame(controller, params);
		}
	}
	
	class MyButton extends JButton
	{
		public MyButton(String name)
		{
			super(name);
			this.setAlignmentX(RIGHT_ALIGNMENT);
			
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent event) 
				{
					processUser();
			    }
			});
		}
	}
}
