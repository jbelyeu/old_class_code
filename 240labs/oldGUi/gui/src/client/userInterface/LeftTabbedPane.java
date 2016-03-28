/**
 * 
 */
package client.userInterface;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.text.TabSet;

/**
 * @author jon
 *
 */
public class LeftTabbedPane extends JTabbedPane
{
	private static final long serialVersionUID = 1L;

	public LeftTabbedPane()
	{
		JPanel table = new JPanel();
		this.addTab("Table Entry", table);
		
		JPanel form = new JPanel();
		this.addTab("Form Entry", form);
	}
}
