/**
 * 
 */
package client.userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author jbelyeu
 *
 */
public class SampleImageViewer extends JDialog 
{
	protected BufferedImage image;
	
	public SampleImageViewer(BufferedImage image, String title)
	{
		this.setTitle(title);
		this.setModal(true);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(600, 600));
		this.image = image;
		ViewerPanel panel = new ViewerPanel();
		this.add(panel);
		
		JButton button = new JButton("Close");
		button.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked (MouseEvent event)
			{
				exit();
			}
		});
		
		this.pack();
		this.setVisible(true);
	}
	
	class ViewerPanel extends JPanel
	{
		public ViewerPanel()
		{
			this.setPreferredSize(new Dimension(600, 600));
			this.setLayout(new BorderLayout());
			
			Graphics2D graphics = image.createGraphics();
			this.paintComponent(graphics);
		}
		
		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(image, 0, 0, null);
		}
	}
	protected void exit()
	{
		this.dispose();
	}
}


/*


public ImageViewer(String URL)
	{
		this.setPreferredSize(new Dimension(600, 600));
		this.setLayout(new BorderLayout());
		MyController controller = new MyController();
		File imageFile = controller.downloadFile(URL);
		try 
		{
			image = ImageIO.read(imageFile);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		Graphics2D graphics = image.createGraphics();
		this.paintComponent(graphics);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
*/