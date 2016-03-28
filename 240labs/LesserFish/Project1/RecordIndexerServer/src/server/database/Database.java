package server.database;

import java.sql.Connection;

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
	private FieldsDAO FieldsDAO;
	private FieldValuesDAO fieldValuesDAO;
	private RecordsDAO recordsDAO;
	private Connection connection;
	
	
	/** Initializes database connection
	 * 
	 * @throws DatabaseException
	 */
	public static void initialize() throws DatabaseException
	{}
	
	/** Database access object constructor
	 * 
	 */
	public Database()
	{}

	/** Gets the database access object for Projects
	 * 
	 * @return ProjectsDAO
	 */
	public ProjectsDAO getProjectsDAO() {
		return projectsDAO;
	}

	/** Gets the database access object for Users
	 * 
	 * @return UsersDAO
	 */
	public UsersDAO getUsersDAO() {
		return usersDAO;
	}

	/** Gets the database access object for Batches
	 * 
	 * @return BatchesDAO
	 */
	public BatchesDAO getBatchesDAO() {
		return batchesDAO;
	}

	/** Gets the database access object for Fields
	 * 
	 * @return FieldsDAO
	 */
	public FieldsDAO getFieldsDAO() {
		return FieldsDAO;
	}

	/** Gets the database access object for FieldValues
	 * 
	 * @return FieldValuesDAO
	 */
	public FieldValuesDAO getFieldValuesDAO() {
		return fieldValuesDAO;
	}

	/** Gets the database access object for Records
	 * 
	 * @return RecordsDAO
	 */
	public RecordsDAO getRecordsDAO() {
		return recordsDAO;
	}

	/** Gets the database connection
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/** Begins the database transaction
	 * 
	 * @throws DatabaseException
	 */
	public void startTransaction () throws DatabaseException
	{}
	
	/** Ends current database transaction
	 * 
	 * @param commit (Boolean, to save data or not
	 */
	public void endTransaction(boolean commit)
	{}
	
	
	

	
}
