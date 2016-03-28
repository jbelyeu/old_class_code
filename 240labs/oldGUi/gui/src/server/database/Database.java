package server.database;

import java.sql.*;

/**
 * 
 * @author jbelyeu
 *
 */
public class Database 
{	
	private ProjectsDAO projectsDAO;
	private UsersDAO usersDAO;	
	private BatchesDAO batchesDAO;
	private FieldsDAO fieldsDAO;
	private FieldValuesDAO fieldValuesDAO;
	private RecordsDAO recordsDAO;
	private Connection connection;
	private static boolean initialized = false;
	
	
	/** Loads database driver
	 * 
	 */
	public static void initialize() 
	{
		if (! initialized)
		{
			final String driver = "org.sqlite.JDBC";
			
			try
			{			
				Class.forName(driver);
				initialized = true;
			}
			catch (ClassNotFoundException e)
			{
				initialized = false;
				System.out.println("ERROR: Driver class " + driver + " not found");
				e.printStackTrace();
			}
		}
	}
	
	/** Database access object constructor
	 * 
	 */
	public Database()
	{
		this.projectsDAO = new ProjectsDAO(this);
		this.usersDAO = new UsersDAO(this);
		this.batchesDAO = new BatchesDAO(this);
		this.fieldsDAO = new FieldsDAO(this);
		this.fieldValuesDAO = new FieldValuesDAO(this);
		this.recordsDAO = new RecordsDAO(this);
	}

	/** Gets the database access object for Projects
	 * 
	 * @return ProjectsDAO
	 */
	public ProjectsDAO getProjectsDAO()
	{
		return projectsDAO;
	}

	/** Gets the database access object for Users
	 * 
	 * @return UsersDAO
	 */
	public UsersDAO getUsersDAO() 
	{
		return usersDAO;
	}

	/** Gets the database access object for Batches
	 * 
	 * @return BatchesDAO
	 */
	public BatchesDAO getBatchesDAO() 
	{
		return batchesDAO;
	}

	/** Gets the database access object for Fields
	 * 
	 * @return FieldsDAO
	 */
	public FieldsDAO getFieldsDAO()
	{
		return fieldsDAO;
	}

	/** Gets the database access object for FieldValues
	 * 
	 * @return FieldValuesDAO
	 */
	public FieldValuesDAO getFieldValuesDAO()
	{
		return fieldValuesDAO;
	}

	/** Gets the database access object for Records
	 * 
	 * @return RecordsDAO
	 */
	public RecordsDAO getRecordsDAO() 
	{
		return recordsDAO;
	}

	/** Gets the database connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection() 
	{
		return connection;
	}
	
	/** Begins the database transaction
	 * 
	 */
	public void startTransaction () //throws DatabaseException
	{
		String DBname = "database/indexer_server.sqlite";
		String connectionURL = "jdbc:sqlite:" + DBname;
		try
		{
			//open the connection
			connection = DriverManager.getConnection(connectionURL);
			
			//start transaction
			connection.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			System.out.println("ERROR: Failed to start transaction");
			e.printStackTrace();
		}
	}
	
	/** Ends current database transaction
	 * 
	 * @param commit (Boolean, to save data or not
	 */
	public void endTransaction(boolean commit)
	{
		try 
		{
			if (commit)
			{
				connection.commit();
			}
			else
			{
				connection.rollback();
			}
		}
		catch (SQLException e )
		{
			System.out.println("ERROR: Failed to end transaction");
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				connection.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("ERROR: Failed to close connection");
				e.printStackTrace();
			}
		}
		connection = null;
	}
	
	/**Drops all tables from the database and runs SQL commands to relaod them
	 * 
	 */
	public static void cleanDatabase()
	{
		DatabaseCleaner.clean(new Database(), true);
	}
}
