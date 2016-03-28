package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.model.Record;

/** Database access class for Record objects
 * 
 * @author jbelyeu
 *
 */
public class RecordsDAO extends DAOSuper
{	
	/**Database access class constructor for Record objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	RecordsDAO (Database DB)
	{
		super(DB);
	}

	/** gets all Records from database
	 * 
	 * @return list of Records
	 */
	public ArrayList<Record> getAll() 
	{
		ArrayList<Record> recordsToReturn = new ArrayList<Record>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, BatchID, RowNumber FROM Records";
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int ID = results.getInt(1);
				int batchID = results.getInt(2);
				int rowNum = results.getInt(3);
				
				Record recordToReturn = new Record(batchID, rowNum);
				recordToReturn.setID(ID);
				recordsToReturn.add(recordToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to RecordsDAO getAll()");
			e.printStackTrace();
		}		
		
		return recordsToReturn;
	}
	
	/** gets all Records from database
	 * 
	 * @return list of Records
	 */
	public ArrayList<Record> getAll(int batchID) 
	{
		ArrayList<Record> recordsToReturn = new ArrayList<Record>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, RowNumber FROM Records WHERE BatchID = " + batchID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int ID = results.getInt(1);
				int rowNum = results.getInt(2);
				
				Record recordToReturn = new Record(batchID, rowNum);
				recordToReturn.setID(ID);
				recordsToReturn.add(recordToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to RecordsDAO getAll(batchID)");
			e.printStackTrace();
		}		
		
		return recordsToReturn;
	}
	
	/**Gets a record by batch ID
	 * 
	 * @param batchID
	 * @return
	 */
	public Record getRecord (int ID) 
	{
		Record recordToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT BatchID, RowNumber FROM Records WHERE ID = " + ID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int batchID = results.getInt(1);
				int rowNum = results.getInt(2);
				
				recordToReturn = new Record(batchID, rowNum);
				recordToReturn.setID(ID);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to RecordsDAO getAll()");
			e.printStackTrace();
		}
		
		return recordToReturn;
	}
	
	/**Gets a record by batch ID
	 * 
	 * @param batchID
	 * @return
	 */
	public Record getRecordByBatch (int batchID) 
	{
		Record recordToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, RowNumber FROM Records WHERE BatchID = " + batchID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int ID = results.getInt(1);
				int rowNum = results.getInt(2);
				
				recordToReturn = new Record(batchID, rowNum);
				recordToReturn.setID(ID);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to RecordsDAO getRecordByBatch()");
			e.printStackTrace();
		}
		
		if (recordToReturn == null)
		{
			ArrayList<Record> records = getAll(batchID);
			int rownum = 0;

			if (records.size() > 0)
			{
				for (Record rec : records)
				{
					if (rec.getRecordNumber() > rownum)
					{
						rownum = rec.getRecordNumber();
					}
				}
			}
			recordToReturn = new Record(batchID, rownum+1);
			this.add(recordToReturn);
		}		
		return recordToReturn;
	}
	
	/** Adds Record to database
	 * 
	 * @param Record
	 */
	public void add(Record record) 
	{	
		PreparedStatement statement = null;
		
		try 
		{			
			String sql = "INSERT INTO Records (BatchID, RowNumber) VALUES (?, ?)";
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, record.getBatchID());
			statement.setInt(2, record.getRecordNumber());			

			record.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in RecordsDAO.add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	 
	/** Updates Record in database
	 * 
	 * @param Record
	 */
	public void update (Record record)
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE Records SET BatchID = ?, RowNumber = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, record.getBatchID());
			statement.setInt(2, record.getRecordNumber());
			statement.setInt(3, record.getID());
			
			this.modifyHelper(statement, "update");		

		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in RecordsDAO.update()");
			e.printStackTrace();
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/** Deletes Record from database
	 * 
	 * @param Record
	 */
	public void delete(Record record) 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM Records WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, record.getID());
			
			this.modifyHelper(statement, "delete");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in RecordsDAO.delete()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
