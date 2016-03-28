/**
 * 
 */
package client.userInterface.login;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.userInterface.IndexerWindow;
import client.utilities.BatchState;

/**
 * @author jon
 *
 */
public class LoginSucceededDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private BatchState batchState;
	
	public LoginSucceededDialog(String username, int records, BatchState batchState)
	{
		this.batchState = batchState;
		this.setPreferredSize(new Dimension(275, 120));
		this.setModal(true);
		this.setTitle("Welcome to Indexer");
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridheight = 1;
		cons.gridwidth = 3;
		cons.gridx = 0;
		cons.gridy = 0;
		panel.add(new JLabel("Welcome, " + username + "."), cons);
					
		cons.gridx = 2;
		cons.gridy = 1;
		cons.gridheight = 1;
		cons.gridwidth = 3;			
		JPanel filler = new JPanel();
		filler.setPreferredSize(new Dimension(10, 5));
		panel.add(filler, cons);
		
		cons.gridheight = 1;
		cons.gridwidth = 3;
		cons.gridx = 0;
		cons.gridy = 2;
		panel.add(new JLabel("You have indexed " + records + " records."), cons);
		
		cons.gridx = 2;
		cons.gridy = 3; 
		cons.gridwidth = 1;
		panel.add(filler, cons);
		
		cons.gridx = 2;
		cons.gridy = 4; 
		cons.gridwidth = 1;
		
		JButton button = new JButton("OK");
		button.setPreferredSize(new Dimension(60, 30));
		panel.add(button, cons);
		
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent event)
			{
				exitDialog();
			}
		});
		
		button.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e) 
			{
	            if (e.getKeyCode() == KeyEvent.VK_ENTER)
	            {
	            	exitDialog();
	            }
	         }  
		});
		
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	private void exitDialog()
	{
		this.dispose();
		IndexerWindow indexer = new IndexerWindow(this.batchState);
	}
}