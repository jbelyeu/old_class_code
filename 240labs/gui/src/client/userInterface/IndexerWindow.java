/**
 * 
 */
package client.userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class IndexerWindow extends JFrame implements CustomStateListener
{
	private static final long serialVersionUID = 8623528378511431600L;
	private BatchState batchState;	

	public IndexerWindow(BatchState batchState)
	{
		this.batchState = batchState;
		batchState.addListener(this);
		batchState.setIndexerWindow(this);
		this.setPreferredSize(new Dimension(1000, 700));
		this.setMinimumSize(new Dimension(200, 100));
		this.setTitle("Indexer");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentHidden(ComponentEvent e)
			{}

			@Override
			public void componentMoved(ComponentEvent e)
			{
				setWindowLocation();				
			}

			@Override
			public void componentResized(ComponentEvent e)
			{
				setSize();				
			}

			@Override
			public void componentShown(ComponentEvent e)
			{}
		});
		
		IndexerMenu menu = new IndexerMenu(batchState);
		
		this.setMenuBar(menu);
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BorderLayout());
		
		ButtonPanel buttons = new ButtonPanel(batchState);
		basePanel.add(buttons, BorderLayout.NORTH);
		basePanel.add(new IndexerVerticalSplitter(batchState));
		
		this.add(basePanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.batchState.loadOldBatch();
	}
	
	/**
	 * Only sets the location for the batch state in order to reload correctly
	 */
	public void setWindowLocation()
	{
		batchState.setWindowPosition(getLocation());		
	}
	
	/**
	 * Only sets the size for the batch state in order to reload correctly
	 */
	public void setSize()
	{
		this.batchState.setWindowSize(getSize());
	}
	
	public void exitSession()
	{
		this.batchState.save();
		this.dispose();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellSelectedChanged()
	 */
	@Override
	public void cellSelectedChanged()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchDownloaded()
	 */
	@Override
	public void batchDownloaded()
	{
		this.repaint();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellValueChanged()
	 */
	@Override
	public void cellValueChanged()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#scaled()
	 */
	@Override
	public void scaled()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchSubmitted()
	 */
	@Override
	public void batchSubmitted()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#invertImage()
	 */
	@Override
	public void invertImage()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#loadedPositioningData()
	 */
	@Override
	public void loadedPositioningData()
	{
		this.setSize(this.batchState.getWindowSize());
		this.setLocation(this.batchState.getWindowPosition());
		
		this.repaint();		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#highlightsToggled()
	 */
	@Override
	public void highlightsToggled()
	{
		// TODO Auto-generated method stub
		
	}
}
