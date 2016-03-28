/**
 * 
 */

package client.userInterface.dataEntry;

import java.awt.*;

import javax.swing.table.*;

import client.utilities.BatchState;

@SuppressWarnings("serial")
public class EntryTableModel extends AbstractTableModel
{
	private BatchState batchstate;

	public EntryTableModel(BatchState state)
	{
		this.batchstate = state;
	}

	@Override
	public int getColumnCount()
	{
		return this.batchstate.getBatchInfo().getFields().size() + 1;
	}

	@Override
	public String getColumnName(int column)
	{
		if (column == 0)
		{
			return "Record Number";
		}
		return this.batchstate.getBatchInfo().getFields().get(column - 1).getFieldTitle();
	}

	@Override
	public int getRowCount()
	{
		return this.batchstate.getBatchInfo().getNumRecords();
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		if (column != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Object getValueAt(int row, int column)
	{

		// if it is the first column, just return the row number
		if (column == 0)
		{
			Integer recordNum = row + 1;
			return recordNum;
		}
		
		// otherwise, decrement to get the corresponding value from the
		// batchState
		else
		{
			--column;
		}

		if (row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount())
		{
			return this.batchstate.getValueAt(row, column);
		}
		else
		{
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		boolean resetCol = false;
		if (column != 0)
		{
			resetCol = true;
			column--;
		}

		if (row >= 0 && row < getRowCount() && column >= 0 && column < getColumnCount())
		{
			this.batchstate.setValueAt((String) value, row, column);
			if (resetCol)
			{
				column++;
			}
			this.fireTableCellUpdated(row, column);
		}
		else
		{
			throw new IndexOutOfBoundsException();
		}
	}

}