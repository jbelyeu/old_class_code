/**
 * 
 */
package client.userInterface.dataEntry;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.TabSet;

import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class LeftTabbedPane extends JTabbedPane implements CustomStateListener
{
	private static final long serialVersionUID = 1L;
	
	private BatchState batchstate;
	private DataEntryTable tablePanel;

	public LeftTabbedPane(BatchState state)
	{
		this.batchstate = state;
		batchstate.addListener(this);
		tablePanel = new DataEntryTable(this.batchstate);
		JScrollPane pane = new JScrollPane(tablePanel);
		this.addTab("Table Entry", pane);
		
		JPanel form = new JPanel();
		this.addTab("Form Entry", form);
		this.setVisible(true);		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellSelectedChanged()
	 */
	@Override
	public void cellSelectedChanged()
	{}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchDownloaded()
	 */
	@Override
	public void batchDownloaded()
	{
		this.tablePanel.setBackground(Color.white);
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
	{}

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
	{}

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
	{}
}
