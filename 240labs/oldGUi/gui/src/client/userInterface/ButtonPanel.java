/**
 * 
 */
package client.userInterface;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jon
 *
 */
public class ButtonPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public ButtonPanel()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(910, 35));
		
		ZoomInButton zoomInButton = new ZoomInButton();
		ZoomOutButton zoomOutButton = new ZoomOutButton();
		InvertButton invertButton = new InvertButton();
		HighlightsButton highlightsButton = new HighlightsButton();
		SaveButton saveButton = new SaveButton();
		SubmitButton submitButton = new SubmitButton();
		
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
	
	class ZoomInButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public ZoomInButton ()
		{
			super("Zoom In");
		}
	}
	
	class ZoomOutButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public ZoomOutButton()
		{
			super("Zoom Out");
		}
		
	}
	
	class InvertButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public InvertButton()
		{
			super("Invert Image");
		}
	}
	
	class HighlightsButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public HighlightsButton()
		{
			super("Toggle Highlights");
		}
	}
	
	class SaveButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public SaveButton()
		{
			super("Save");
		}
	}
	
	class SubmitButton extends JButton
	{
		private static final long serialVersionUID = 1L;
		
		public SubmitButton()
		{
			super("Submit");
		}
	}
}
