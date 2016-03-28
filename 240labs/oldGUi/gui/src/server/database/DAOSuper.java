/**
 * 
 */
package server.database;

import java.sql.*;
import java.util.logging.Logger;

/**
 * @author jbelyeu
 *
 */
public abstract class DAOSuper 
{
	
	protected Database database;
	protected static Logger logger;
	
	
	/**Database access class constructor
	 * 
	 * @param DB (instance of direct database access class)
	 */
	DAOSuper (Database DB)
	{
		this.database = DB;
		Database.initialize();
	}
	
	/**Helps add an item by receiving the statement from child class and running SQL command
	 * 
	 * @param statement
	 * @return
	 */
	public int addHelper(PreparedStatement statement)
	{		
		java.sql.Statement keyStatement = null;
		ResultSet resultKey = null;	
		int newID = 0;

		try 
		{			
			if (statement.executeUpdate() == 1)
			{
				String sql = "SELECT LAST_INSERT_ROWID()";
				
				keyStatement = database.getConnection().createStatement();
				resultKey = keyStatement.executeQuery(sql);				
				resultKey.next();
				newID = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException();
			}
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed call to addHelper()");
			e.printStackTrace();
		}	
		finally
		{
			try
			{
				if (statement != null)  statement.close();
				if (keyStatement != null)  keyStatement.close();
				if (resultKey != null) resultKey.close();
			}
			catch (SQLException e)
			{
				System.out.println("ERROR: Failed to close statements");
				e.printStackTrace();
			}
		}
		return newID;
	}
	
	
	/**Handles update and delete request to DB
	 * 
	 * @param statement
	 * @param sql
	 * @param commit
	 * @param request
	 */
	public void modifyHelper(PreparedStatement statement, String request)
	{
		try 
		{			
			statement.executeUpdate();			
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to " + request);
		}	
	}
}
