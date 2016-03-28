package server.database;

/** Extends Exception, provides Database specific error-handling
 *  
 * @author jbelyeu
 *
 */
@SuppressWarnings("serial")
public class DatabaseException extends Exception 
{
	/** Instantiates the DatabaseException class
	 * 
	 */
	public DatabaseException() 
	{
		return;
	}

	/**Instantiates the DatabaseException class with error message
	 * 
	 * @param message
	 */
	public DatabaseException(String message) 
	{
		super(message);
	}

	/**Instantiates the DatabaseException class with cause of throw
	 * 
	 * @param cause
	 */
	public DatabaseException(Throwable cause) 
	{
		super(cause);
	}

	/** Instantiates the DatabaseException class with error message and cause of throw
	 * 
	 * @param message
	 * @param cause
	 */
	public DatabaseException(String message, Throwable cause) 
	{
		super(message, cause);
	}
}
