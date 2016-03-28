package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import shared.model.FieldValue;
import shared.model.Record;

/** Database access class for FieldValue objects
 * 
 * @author jbelyeu
 *
 */
public class FieldValuesDAO  extends DAOSuper
{	
	/**Database access class constructor for FieldValue objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	FieldValuesDAO (Database DB)
	{
		super(DB);
	}
	
	/** gets all FieldValues from database
	 * 
	 * @return list of FieldValues
	 */
	public ArrayList<FieldValue> getAll()
	{
		ArrayList<FieldValue> fieldValsToReturn = new ArrayList<FieldValue>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, RecordID, RecordNumber, RecordText FROM FieldValues";
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int ID = results.getInt(1);
				int recordID = results.getInt(2);
				int recordNum = results.getInt(3);
				String recordText = results.getString(4);	
				
				FieldValue valToReturn = new FieldValue(ID, recordID, recordNum, recordText);
				fieldValsToReturn.add(valToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to FieldValuesDAO.getAll()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return fieldValsToReturn;
	}
	
	/** 
	 * 
	 * @param batchID
	 * @param commit
	 * @return
	 */
	public FieldValue getFieldValue(int ID) 
	{
		FieldValue fieldValueToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT RecordID, RecordNumber, RecordText FROM FieldValues WHERE ID = " + ID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int recordID = results.getInt(1);
				int recordNum = results.getInt(2);
				String recordText = results.getString(3);	
				
				fieldValueToReturn = new FieldValue(ID, recordID, recordNum, recordText);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getFieldValue()");
			e.printStackTrace();
		}
		return fieldValueToReturn;
	}
	

	/** Adds FieldValue to database
	 * 
	 * @param FieldValue
	 */
	public void add (FieldValue fieldValue) //throws DatabaseException
	{
		PreparedStatement statement = null;

		try
		{
			String sql = "INSERT INTO FieldValues (RecordID, RecordNumber, RecordText, FieldID) VALUES (?, ?, ?, ?)";
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, fieldValue.getRecordID());
			statement.setInt(2, fieldValue.getRecordNumber());
			statement.setString(3, fieldValue.getRecordText());
			statement.setInt(4, fieldValue.getFieldID());
			
			fieldValue.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to FieldValuesDAO.add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	/** Updates FieldValue in database
	 * 
	 * @param FieldValue
	 */
	public void update (FieldValue fieldValue)
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE FieldValues SET RecordID = ?, RecordNumber = ?, RecordText = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1,  fieldValue.getRecordID());
			statement.setInt(2,  fieldValue.getRecordNumber());
			statement.setString(3,  fieldValue.getRecordText());
			statement.setInt(4,  fieldValue.getID());
			
			this.modifyHelper(statement, "update");	
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to FieldValuesDAO.update()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Deletes FieldValue from database
	 * 
	 * @param FieldValue
	 */
	public void delete (FieldValue fieldValue) 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM FieldValues WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, fieldValue.getID());
			
			this.modifyHelper(statement, "delete");	
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to FieldValuesDAO.delete()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LinkedList<Record> search(LinkedList<String> fieldIDs, LinkedList<String> searchTerms)
	{
		LinkedList<Record> recordResults = new LinkedList<Record>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			StringBuilder sqlBuilder = new StringBuilder("SELECT BatchID, ImagePath, RecordNumber, FieldID FROM "
					+ "FieldValues JOIN Records ON Records.ID = RecordID JOIN Batches ON Batches.ID = BatchID Where ((");
		
			for (int i = 0; i < fieldIDs.size(); ++i)
			{
				sqlBuilder.append("FieldID = " + fieldIDs.get(i).trim());
				if (i < fieldIDs.size()-1)
				{
					sqlBuilder.append(" OR ");
				}
			}		
			
			sqlBuilder.append(") AND (");
			for (int i = 0; i < searchTerms.size(); ++i)
			{
				sqlBuilder.append("UPPER(RecordText) = UPPER(\'" + searchTerms.get(i).trim() + "\')") ;
				if (i < searchTerms.size()-1)
				{
					sqlBuilder.append(" OR ");
				}
			}
			
			sqlBuilder.append("))");
			String sql = sqlBuilder.toString();

			statement = this.database.getConnection().prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next())
			{
				Record newRecord = new Record(results.getInt(1), results.getString(2), results.getInt(3), results.getInt(4));
				recordResults.add(newRecord);
			}
			
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to FieldValuesDAO.search()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return recordResults;
	}	
}
