/**
 * 
 */
package client.userInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JSplitPane;

import client.userInterface.dataEntry.LeftTabbedPane;
import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class IndexerHorizontalSplitter extends JSplitPane implements CustomStateListener
{
	private static final long serialVersionUID = 1L;
	private BatchState batchState;

	public IndexerHorizontalSplitter(BatchState state)
	{
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.batchState = state;
		this.batchState.addListener(this);
		this.setResizeWeight(0.5);		
		this.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent arg0)
			{
				recordPosition();				
			}
		});
		
		LeftTabbedPane left = new LeftTabbedPane(batchState);
		RightTabbedPane right = new RightTabbedPane();
		
		this.setLeftComponent(left);
		this.setRightComponent(right);
	}
	
	/**
	 * gets the location of the divider and saves it in the batchstate
	 */
	public void recordPosition()
	{
		int location = this.getDividerLocation();
		
		this.batchState.setHorizontalDividerLocation(location);
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
		// TODO Auto-generated method stub
		
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
		this.setDividerLocation(this.batchState.getHorizontalDividerLocation());
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
