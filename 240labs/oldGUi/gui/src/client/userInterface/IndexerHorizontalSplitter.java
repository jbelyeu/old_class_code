/**
 * 
 */
package client.userInterface;

import javax.swing.JSplitPane;

/**
 * @author jon
 *
 */
public class IndexerHorizontalSplitter extends JSplitPane
{
	private static final long serialVersionUID = 1L;

	public IndexerHorizontalSplitter()
	{
		super(JSplitPane.HORIZONTAL_SPLIT);
		this.setResizeWeight(0.5);
		
		LeftTabbedPane left = new LeftTabbedPane();
		RightTabbedPane right = new RightTabbedPane();
		
		this.setLeftComponent(left);
		this.setRightComponent(right);
	}
}
