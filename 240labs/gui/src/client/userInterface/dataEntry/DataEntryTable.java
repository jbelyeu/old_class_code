/**
 * 
 */

package client.userInterface.dataEntry;

import javax.swing.JTable;










/**
 * @author jon
 *
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import client.utilities.BatchState;
import client.utilities.BatchState.Cell;
import client.utilities.CustomStateListener;


@SuppressWarnings("serial")
public class DataEntryTable extends JPanel implements CustomStateListener
{
	private EntryTableModel tableModel;
	private JTable table;
	private BatchState batchstate;

	public DataEntryTable(BatchState state) throws HeadlessException
	{
		this.batchstate = state;
		this.batchstate.addListener(this);
		this.setLayout(new BorderLayout());
	}

	private MouseAdapter mouseAdapter = new MouseAdapter()
	{
		@Override
		public void mouseReleased(MouseEvent e)
		{
			if (SwingUtilities.isRightMouseButton(e))
			{
				//TODO this code handles the color picker. Use it as format for the suggestions window
				
//				final int row = table.rowAtPoint(e.getPoint());
//				final int column = table.columnAtPoint(e.getPoint());
//				
//				if (row >= 0 && row < tableModel.getRowCount() && column >= 1
//						&& column < tableModel.getColumnCount())
//				{
//
//					final JColorChooser colorChooser = new JColorChooser();
//
//					ActionListener okListener = new ActionListener()
//					{
//						@Override
//						public void actionPerformed(ActionEvent e2)
//						{
//							Color newColor = colorChooser.getColor();
//							tableModel.setValueAt(ColorUtils.toString(newColor), row, column);
//						}
//					};
//
//					JDialog dialog = JColorChooser.createDialog(table, "Pick a Color", true,
//							colorChooser, okListener, null);
//					dialog.setVisible(true);
//				}
			}
		}
	};

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellSelectedChanged()
	 */
	@Override
	public void cellSelectedChanged()
	{
		if (this.batchstate.getSelectedCell() != null)
		{
			//image, cell are 1-based, this is 0-based
			int column = this.batchstate.getSelectedCell().getField() ;
			int row = this.batchstate.getSelectedCell().getRecord() -1;		
			table.changeSelection(row, column, false, false);
		}
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchDownloaded()
	 */
	@Override
	public void batchDownloaded()
	{
		tableModel = new EntryTableModel(batchstate);

		table = new JTable(tableModel);
		table.setRowHeight(18);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addMouseListener(mouseAdapter);
		table.setBackground(Color.white);
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i)
		{
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(90);
		}
		for (int i = 1; i < tableModel.getColumnCount(); ++i)
		{
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer( new RecordCellRenderer(batchstate) );
		}


		table.setFillsViewportHeight(false);
		this.add(table.getTableHeader(), BorderLayout.NORTH);
		this.add(table, BorderLayout.WEST);
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellValueChanged()
	 */
	@Override
	public void cellValueChanged()
	{
		this.revalidate();		
	}

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
	{
//		this.repaint(); not in spec		
	}		
}

@SuppressWarnings("serial")
class RecordCellRenderer extends JLabel implements TableCellRenderer
{

	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
	private BatchState batchstate;
	
	public RecordCellRenderer(BatchState state)
	{
		setOpaque(true);
		setFont(getFont().deriveFont((float) 12));
		this.batchstate = state;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column)
	{
		//TODO: 
		//It should get the values and search for each one in the spelling corrector. If they are wrong,
		//it should generate the list of suggestions and store them in the batch state, then make a 
		//listener and box and stuff for them
		
		
		if (isSelected)		
		{
			this.batchstate.setSelectedCell(column, row + 1);
		}
				
//		if (isSelected && batchstate.isHighlightsOn())
		if (isSelected)
		{
			this.setBorder(selectedBorder);
			this.setBackground(new Color(0,100,255,80));
		}
		else
		{
			this.setBackground(Color.WHITE);
			this.setBorder(unselectedBorder);
		}

		this.setText((String) value);
		return this;
	}
}