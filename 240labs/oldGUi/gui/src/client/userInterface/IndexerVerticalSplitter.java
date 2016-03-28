/**
 * 
 */
package client.userInterface;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author jon
 *
 */
public class IndexerVerticalSplitter extends JSplitPane
{
	private static final long serialVersionUID = 1L;

	public IndexerVerticalSplitter()
	{ 
		super(JSplitPane.VERTICAL_SPLIT);
		
		ImageViewer top = new ImageViewer();
		this.setTopComponent(top);
		this.setResizeWeight(0.5);
		
		IndexerHorizontalSplitter bottom = new IndexerHorizontalSplitter();
		this.setBottomComponent(bottom);
		
	}
}
