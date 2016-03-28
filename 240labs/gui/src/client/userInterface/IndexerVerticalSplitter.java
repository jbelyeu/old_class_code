/**
 * 
 */
package client.userInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;

import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class IndexerVerticalSplitter extends JSplitPane implements CustomStateListener
{
	private static final long serialVersionUID = 1L;
	private BatchState batchState;
	private ImageViewer viewer;

	public IndexerVerticalSplitter(BatchState state)
	{ 
		super(JSplitPane.VERTICAL_SPLIT);
		
		this.batchState = state;
		this.batchState.addListener(this);
		this.viewer = new ImageViewer(batchState);
		this.setTopComponent(viewer.getTop());
		this.setResizeWeight(0.5);
		this.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent arg0)
			{
				recordPosition();				
			}	
		});
		
		IndexerHorizontalSplitter bottom = new IndexerHorizontalSplitter(batchState);
		this.setBottomComponent(bottom);
	}
	
	/**
	 * gets the location of the divider and saves it in the batchstate
	 */
	public void recordPosition()
	{
		int location = this.getDividerLocation();
		
		this.batchState.setVerticalDividerLocation(location);
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
		this.remove(viewer.getTop());
		this.setTopComponent(this.viewer.getImage());
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
		this.setDividerLocation(this.batchState.getVerticalDividerLocation());
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
