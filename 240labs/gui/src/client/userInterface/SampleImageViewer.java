/**
 * 
 */
package client.userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author jbelyeu
 *
 */
public class SampleImageViewer extends JDialog 
{
	private static final long serialVersionUID = 1L;
	protected BufferedImage image;
	
	public SampleImageViewer(BufferedImage image, String title)
	{
		this.setBackground(Color.blue);
		this.setTitle(title);
		this.setModal(true);
		this.setResizable(false);
		this.image = image;
		ViewerPanel panel = new ViewerPanel();
		this.setLayout(new BorderLayout());
		this.add(panel);
		this.setPreferredSize(new Dimension(panel.x / 2, (int)(panel.y / 1.85) ) );

		JButton button = new JButton("Close");
		button.setPreferredSize(new Dimension(75, 30));
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent event)
			{
				exit();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
	
	class ViewerPanel extends JComponent
	{
		private static final long serialVersionUID = 1L;
		public int x;
		public int y;

		public ViewerPanel()
		{
			this.setLayout(new BorderLayout());
			x = image.getWidth();
			y = image.getHeight();
			
			Graphics2D graphics = image.createGraphics();
			graphics.scale(0.5, 0.5);
			this.paintComponent(graphics);
		}
		
		@Override
		protected void paintComponent(Graphics g)
		{
			g.drawImage(image, 0, 0, null);
		}
	}
	protected void exit()
	{
		this.dispose();
	}
}