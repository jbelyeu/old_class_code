package shared.model;

/**Models data for batch (image) from database
 * 
 * @author jbelyeu
 *
 */
public class Batch 
{
	private int ID;
	private int projectID;
	private String imagePath;
	
	/** Constructor to create Batch 
	 * 
	 */
	public Batch () 
	{}
	
	/** Constructor to fully initialize Batch 
	 * 
	 * @param iD
	 * @param projectID
	 * @param imagePath
	 */
	public Batch (int iD, int projectID, String imagePath) 
	{
		ID = iD;
		this.projectID = projectID;
		this.imagePath = imagePath;
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
	 * @return the projectID
	 */
	public int getProjectID() 
	{
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) 
	{
		this.projectID = projectID;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() 
	{
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) 
	{
		this.imagePath = imagePath;
	}
	
	
	
}
