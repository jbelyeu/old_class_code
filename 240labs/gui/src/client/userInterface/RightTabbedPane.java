/**
 * 
 */
package client.userInterface;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author jon
 *
 */
public class RightTabbedPane extends JTabbedPane
{
	private static final long serialVersionUID = 1L;
	
	public RightTabbedPane()
	{
		JPanel fieldHelp = new JPanel();
		this.addTab("Field Help", fieldHelp);
		
		JPanel imageNavigator = new JPanel();
		this.addTab("Image Navigation", imageNavigator);
	}

}
