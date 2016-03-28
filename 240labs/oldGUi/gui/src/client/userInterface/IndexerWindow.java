/**
 * 
 */
package client.userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.utilities.BatchState;

/**
 * @author jon
 *
 */
public class IndexerWindow extends JFrame
{
	private static final long serialVersionUID = 8623528378511431600L;
	private BatchState batchState;

	public IndexerWindow(BatchState batchState)
	{
		this.batchState = batchState;
		batchState.setIndexerWindow(this);
		this.setPreferredSize(new Dimension(1000, 700));
		this.setTitle("Indexer");
		
		//TODO break this out into its own class
		IndexerMenu menu = new IndexerMenu(batchState);
		
		this.setMenuBar(menu);
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		
		ButtonPanel buttons = new ButtonPanel();
		basePanel.add(buttons, BorderLayout.NORTH);

		basePanel.add(new IndexerVerticalSplitter());
		
		this.add(basePanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void exitSession()
	{
		this.batchState.save();
		this.dispose();
	}
}
