package server.database;

import java.sql.*;
import java.util.ArrayList;
import shared.model.Field;

/** Database access class for Field objects
 * 
 * @author jbelyeu
 *
 */
public class FieldsDAO extends DAOSuper
{
	/**Database access class constructor for Field objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	FieldsDAO (Database DB)
	{
		super(DB);
	}
	
	/** gets all Fields from database
	 * 
	 * @return list of Fields
	 */
	public ArrayList<Field> getAll(int projectID) 
	{
		ArrayList<Field> fieldsToReturn = new ArrayList<Field>();
		PreparedStatement statement = null;
		ResultSet results = null;		
		
		try
		{
			String query = "SELECT ID, FieldTitle, XCoordinate, Width, FieldHelpFileName,"
					+ " KnownDataFileName, FieldNumber FROM Fields WHERE ProjectID = " + projectID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int id = results.getInt(1);
				String fieldTitle = results.getString(2);
				int xcoord = results.getInt(3);
				int width = results.getInt(4);
				String fieldHelpFilename = results.getString(5);
				String knownDataFileName = results.getString(6);
				int fieldNum = results.getInt(7);
				
				Field fieldToReturn = new Field(id, projectID, fieldTitle, xcoord, width, fieldHelpFilename, knownDataFileName, fieldNum);
				fieldsToReturn.add(fieldToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to FieldsDAO.GetALL()");
			e.printStackTrace();
		}

		return fieldsToReturn;
	}
	
	/** gets all Fields from database
	 * 
	 * @return list of Fields
	 */
	public ArrayList<Field> getAll() 
	{
		ArrayList<Field> fieldsToReturn = new ArrayList<Field>();
		PreparedStatement statement = null;
		ResultSet results = null;		
		
		try
		{
			String query = "SELECT ID, FieldTitle, XCoordinate, Width, FieldHelpFileName,"
						+ " KnownDataFileName, FieldNumber, ProjectID FROM Fields";
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int id = results.getInt(1);
				String fieldTitle = results.getString(2);
				int xcoord = results.getInt(3);
				int width = results.getInt(4);
				String fieldHelpFilename = results.getString(5);
				String knownDataFileName = results.getString(6);
				int fieldNum = results.getInt(7);
				int projectID = results.getInt(8);
				
				Field fieldToReturn = new Field(id, projectID, fieldTitle, xcoord, width, fieldHelpFilename, knownDataFileName, fieldNum);
				fieldsToReturn.add(fieldToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to FieldsDAO.GetALL()");
			e.printStackTrace();
		}

		return fieldsToReturn;
	}
	
	public Field getField(int ID) 
	{
		Field fieldToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;		
		
		try
		{
			String query = "SELECT ID, ProjectID, FieldTitle, XCoordinate, Width, FieldHelpFileName, "
							+ "KnownDataFileName, FieldNumber FROM Fields WHERE ID = " + ID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int id = results.getInt(1);
				int projID = results.getInt(2);
				String fieldTitle = results.getString(3);
				int xcoord = results.getInt(4);
				int width = results.getInt(5);
				String fieldHelpFilename = results.getString(6);
				String knownDataFileName = results.getString(7);
				int fieldNum = results.getInt(8);
				
				fieldToReturn = new Field(id, projID, fieldTitle, xcoord, width, fieldHelpFilename, knownDataFileName, fieldNum);
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to FieldsDAO.GetField()");
			e.printStackTrace();
		}
		return fieldToReturn;
	}
	
	/** Adds Field to database
	 * 
	 * @param Field
	 */
	public void add(Field field) 
	{
		PreparedStatement statement = null;
		
		try 
		{
			String sql = "INSERT INTO Fields (ProjectID, FieldTitle, XCoordinate, Width, FieldHelpFileName, "
						+ "KnownDataFileName, FieldNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, field.getProjectID());
			statement.setString(2, field.getTitle());
			statement.setInt(3, field.getXCoordinate());
			statement.setInt(4, field.getWidth());
			statement.setString(5, field.getFieldHelpFileName());
			statement.setString(6, field.getKnownDataFileName());
			statement.setInt(7, field.getFieldNumber());
			
			field.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	/** Updates Field in database
	 * 
	 * @param Field
	 * @throws DatabaseException
	 */
	public void update (Field field)
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE Fields SET ProjectID = ?, FieldTitle = ?, XCoordinate = ?, Width = ?, "
						+ "FieldHelpFileName = ?, KnownDataFileName = ?, FieldNumber = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, field.getProjectID());
			statement.setString(2, field.getTitle());
			statement.setInt(3, field.getXCoordinate());
			statement.setInt(4, field.getWidth());
			statement.setString(5, field.getFieldHelpFileName());
			statement.setString(6, field.getKnownDataFileName());
			statement.setInt(7, field.getFieldNumber());
			statement.setInt(8, field.getID());
			
			this.modifyHelper(statement, "update");		
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to update()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/** Deletes Field from database
	 * 
	 * @param Field
	 */
	public void delete(Field field)// throws DatabaseException 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM Fields WHERE ID = ?";

			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, field.getID());	

			this.modifyHelper(statement, "delete");
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to delete()");
			e.printStackTrace();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
