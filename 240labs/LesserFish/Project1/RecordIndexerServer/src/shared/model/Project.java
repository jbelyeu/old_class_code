package shared.model;

/**Models data for indexing project from database
 * 
 * @author jbelyeu
 *
 */
public class Project 
{
	private int ID;
	private String name;
	
	/** Constructor to create Project 
	 * 
	 */
	public Project() 
	{}
	
	/** Constructor to fully initialize Project 
	 * 
	 * @param iD
	 * @param name
	 */
	public Project(int iD, String name) 
	{
		ID = iD;
		this.name = name;
	}
	
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
	 * @return the name
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}


	

}
