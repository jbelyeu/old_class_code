
package client.userInterface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.utilities.BatchState;
import client.utilities.CustomStateListener;

public class ImageViewer implements CustomStateListener
{
	private DrawingComponent component;
	private BatchState batchstate;
	private JPanel top;

	public ImageViewer(BatchState state)
	{
		this.batchstate = state;
		batchstate.addListener(this);
		top = new JPanel();
		top.setBackground(Color.LIGHT_GRAY);
		component = new DrawingComponent(batchstate);
	}
	
	public void setOrigin(int w_newOriginX, int w_newOriginY)
	{
		component.setOrigin(w_newOriginX, w_newOriginY);
	}
	
	public JComponent getTop()
	{
		return this.top;
	}
	
	public JComponent getImage()
	{
		return this.component;
	}
	
	private void loadBatchInfo()
	{
		component.setUp();
		component.addMouseWheelListener(wheelListener);
		component.revalidate();
		
//		this.add(component, BorderLayout.CENTER);
//		this.setBackground(Color.LIGHT_GRAY);
//		
//		this.addMouseWheelListener(wheelListener);
//		this.setLocation(100, 100);
		if (this.batchstate.isImageInverted())
		{
			invertImage();
		}
//		this.revalidate();
	}

	private MouseWheelListener wheelListener = new MouseAdapter()
	{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			double increment = e.getWheelRotation() * 0.1;
			double newScale = component.scale - increment;
			if (newScale <= 0)
			{
				newScale = 0;
			}
			
			System.out.println(component.width);
			if (component.width * newScale > 200 && component.width * newScale < 1200)
			{
				component.setScale(newScale);			
				batchstate.setZoomLevel(newScale, true);
			}
		}
	};


	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellSelectedChanged()
	 */
	@Override
	public void cellSelectedChanged()
	{
		this.component.selectedCellChanged();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchDownloaded()
	 */
	@Override
	public void batchDownloaded()
	{
		loadBatchInfo();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellValueChanged()
	 */
	@Override
	public void cellValueChanged()
	{}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#scaled()
	 */
	@Override
	public void scaled()
	{
		this.component.scale = this.batchstate.getZoomLevel();
		this.component.repaint();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchSubmitted()
	 */
	@Override
	public void batchSubmitted()
	{}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#invertImage()
	 */
	@Override
	public void invertImage()
	{
		this.component.invertImage();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#loadedPositioningData()
	 */
	@Override
	public void loadedPositioningData()
	{
		if (this.batchstate.isImageInverted())
		{
			this.component.invertImage();
		}
		this.component.w_originX = batchstate.getScrollPosition().x;
		this.component.w_originY = batchstate.getScrollPosition().y;
		this.scaled();
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#highlightsToggled()
	 */
	@Override
	public void highlightsToggled()
	{
		this.component.repaint();;
		
	}
}