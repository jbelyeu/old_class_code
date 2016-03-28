package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.communication.GetBatchResult;
import shared.model.Batch;
import shared.model.Field;
import shared.model.User;

/** Database access class for Batch objects
 * 
 * @author jbelyeu
 *
 */
public class BatchesDAO extends DAOSuper
{
	/**Database access class constructor for Batch objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	BatchesDAO (Database DB)
	{
		super(DB);
	}
	
	/** gets all batches for a project from from database.
	 * 
	 * @return list of batches
	 */
	public ArrayList<Batch> getAll(int projectID)
	{
		ArrayList<Batch> batchesToReturn = new ArrayList<Batch>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, ImagePath FROM Batches WHERE ProjectID = " + projectID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();

			while ( results.next() )
			{
				int id = results.getInt(1);
				String imagePath = results.getString(2);
				
				Batch projectToReturn = new Batch(id, projectID, imagePath);
				batchesToReturn.add(projectToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to BatchesDAO getAll()");
			e.printStackTrace();
		}
		return batchesToReturn;
	}
	
	/** gets a batch from database.
	 * @return Batch
	 */
	public Batch getBatch(int batchID) 
	{
		Batch batchToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ImagePath, ProjectID FROM Batches WHERE ID = " + batchID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				String imagePath = results.getString(1); 
				int projectID = results.getInt(2);
				
				batchToReturn = new Batch(batchID, projectID, imagePath);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getBatch()");
			e.printStackTrace();
		}
		return batchToReturn;
	}
	
	/** gets a batch from database.
	 * @return GetBatchResult
	 */
	public GetBatchResult getBatch(int projectID, int userID) 
	{
		GetBatchResult resultToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{			
			User user = database.getUsersDAO().getUser(userID);
			String current = user.getCurrentBatch();
			
			if (current == null || current.equals("") || current.equals("0"))
			{
				resultToReturn = new GetBatchResult();
				String sql = "SELECT ImagePath, FirstYCoordinate, RecordHeight, "
						+ "RecordsPerImage, Batches.ID FROM Batches JOIN Projects WHERE ProjectID = ? AND Assigned > -1 AND Assigned < 1";
				
				statement = this.database.getConnection().prepareStatement(sql);
				statement.setInt(1, projectID);
				results = statement.executeQuery();
				
				ArrayList<Field> fields = database.getFieldsDAO().getAll(projectID);

				if (!results.next() )
				{
					return null;
				}
				resultToReturn.setProjectID(projectID);
				resultToReturn.setImageURL(results.getString(1));
				resultToReturn.setFirstYCoord(results.getInt(2));
				resultToReturn.setRecordHeight(results.getInt(3));
				resultToReturn.setNumRecords(results.getInt(4));
				resultToReturn.setBatchID(results.getInt(5));
				resultToReturn.setNumFields(fields.size());
				resultToReturn.setFields(fields);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getBatch()");
			e.printStackTrace();
		}
		return resultToReturn;
	}
	
	/** Adds batch to database
	 * 
	 * @param batch
	 */
	public void add(Batch batch) 
	{
		PreparedStatement statement = null;
		
		try 
		{
			String sql = "INSERT INTO Batches (ImagePath, ProjectID, Assigned) VALUES (?, ?, ?)";
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, batch.getImagePath());
			statement.setInt(2, batch.getProjectID());
			statement.setInt(3, batch.getAssigned());
			

			batch.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in BatchesDAO.add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	/** Updates batch in database
	 * 
	 * @param batch
	 */
	public void update (Batch batch )
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE Batches SET ImagePath = ?, ProjectID = ?, Assigned = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, batch.getImagePath());
			statement.setInt(2, batch.getProjectID());
			statement.setInt(3, batch.getAssigned());
			statement.setInt(4, batch.getID());			
			
			this.modifyHelper(statement, "update");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in BatchesDAO.update()");
			e.printStackTrace();
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Deletes batch from database
	 * 
	 * @param Batch
	 */
	public void delete(Batch batch)
	{
		try
		{
			delete(batch.getID());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Deletes batch from database
	 * 
	 * @param ID
	 */
	public void delete(int ID )
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM Batches WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, ID);
			
			this.modifyHelper(statement, "delete");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in BatchesDAO.delete()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
