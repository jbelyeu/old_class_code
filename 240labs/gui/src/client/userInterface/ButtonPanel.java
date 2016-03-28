/**
 * 
 */
package client.userInterface;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class ButtonPanel extends JPanel implements CustomStateListener
{
	private static final long serialVersionUID = 1L;
	private ArrayList<JButton> buttons;
	private BatchState batchState;

	public ButtonPanel(BatchState state)
	{
		this.batchState = state;
		batchState.addListener(this);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(910, 35));
		
		ZoomInButton zoomInButton = new ZoomInButton();
		ZoomOutButton zoomOutButton = new ZoomOutButton();
		InvertButton invertButton = new InvertButton();
		HighlightsButton highlightsButton = new HighlightsButton();
		SaveButton saveButton = new SaveButton();
		SubmitButton submitButton = new SubmitButton();
		
		buttons = new ArrayList<JButton>();
		buttons.add(zoomInButton);
		buttons.add(zoomOutButton);
		buttons.add(invertButton);
		buttons.add(highlightsButton);
		buttons.add(saveButton);
		buttons.add(submitButton);
		
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(zoomInButton);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(zoomOutButton);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(invertButton);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(highlightsButton);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(saveButton);
		this.add(Box.createRigidArea(new Dimension(5, 20)));
		this.add(submitButton);
		this.add(Box.createGlue());
		
		this.validate();
		this.setVisible(true);
	}
	
	protected void zoomOut()
	{
		this.batchState.setZoomLevel(batchState.getZoomLevel() *  0.75 , true);
	}
	
	protected void zoomIn()
	{
		this.batchState.setZoomLevel(batchState.getZoomLevel() *  1.25 , true);
	}
	
	protected void invert()
	{
		System.out.println("client userinterface ButtonPanel");
		System.out.println("button to invert");
		this.batchState.setImageInverted();
	}
	
	protected void toggleHighlights()
	{
		this.batchState.setHighlightsOn();
	}
	
	protected void save()
	{
		System.out.println("client userinterface ButtonPanel");
		System.out.println("button to save");
		
		this.batchState.save();
	}
	
	protected void submit()
	{
		System.out.println("client userinterface ButtonPanel");
		System.out.println("button to submit");
		
		this.batchState.submit();
		
		for ( JButton button : buttons)
		{
			button.setEnabled(false);
		}
	}
	
	class ZoomInButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public ZoomInButton ()
		{
			super("Zoom In");		
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					zoomIn();
				}
			});
		}
	}
	
	class ZoomOutButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public ZoomOutButton()
		{
			super("Zoom Out");	
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					zoomOut();
				}
			});
		}	
	}
	
	class InvertButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public InvertButton()
		{
			super("Invert Image");
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					invert();
				}
			});
		}
	}
	
	class HighlightsButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public HighlightsButton()
		{
			super("Toggle Highlights");
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					toggleHighlights();
				}
			});
		}
	}
	
	class SaveButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public SaveButton()
		{
			super("Save");
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					save();
				}
			});
		}
	}
	
	class SubmitButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public SubmitButton()
		{
			super("Submit");
			this.setEnabled(false);
			this.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					submit();
				}
			});
		}
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
		for ( JButton button : buttons)
		{
			button.setEnabled(true);
		}
		
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
	{}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#highlightsToggled()
	 */
	@Override
	public void highlightsToggled()
	{
		// TODO Auto-generated method stub
		
	}
}
