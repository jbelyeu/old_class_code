package shared.model;

/**Models data for user from database
 * 
 * @author jbelyeu
 *
 */
public class User 
{
	private int ID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int recordsIndexed;
	private Batch currentBatch;
	
	/** Constructor to create User
	 * 
	 */
	public User()
	{}
	
	/** Constructor to fully initialize User
	 * 
	 * @param ID (int)
	 * @param username (string)
	 * @param password (string)
	 * @param firstname (string)
	 * @param lastname (string)
	 * @param recordsIndexed (int)
	 * @param currentBatch (string)
	 */
	public User(int ID, String username, String password, String firstname, 
			String lastname, int recordsIndexed, Batch currentBatch)
	{}
	
	
	
	/**
	 * @return the iD
	 */
	public int getID() 
	{
		return ID;
	}
	
	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) 
	{
		ID = iD;
	}

	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() 
	{
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	/**
	 * @return the recordsIndexed
	 */
	public int getRecordsIndexed() 
	{
		return recordsIndexed;
	}
	
	/**
	 * @param recordsIndexed the recordsIndexed to set
	 */
	public void setRecordsIndexed(int recordsIndexed) 
	{
		this.recordsIndexed = recordsIndexed;
	}
	
	/**
	 * @return the currentBatch
	 */
	public Batch getCurrentBatch() 
	{
		return currentBatch;
	}
	
	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setCurrentBatch(Batch currentBatch) 
	{
		this.currentBatch = currentBatch;
	}
	
	
	
	

}
