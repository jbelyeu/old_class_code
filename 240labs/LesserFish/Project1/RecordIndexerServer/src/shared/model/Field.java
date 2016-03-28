package shared.model;

/**Models data for field (from batch) from database
 * 
 * @author jbelyeu
 *
 */
public class Field 
{
	private int ID;
	private int projectID;
	private String title;
	private int width;
	private String FieldHelpFileName;
	private int projectNumber;
	
	/**  Constructor to create Field 
	 * 
	 */
	public Field () 
	{}
	
	/**  Constructor to fully initialize Field 
	 * 
	 * @param iD
	 * @param projectID
	 * @param title
	 * @param width
	 * @param fieldHelpFileName
	 * @param projectNumber
	 */
	public Field (int iD, int projectID, String title, int width, String fieldHelpFileName, int projectNumber) 
	{
		ID = iD;
		this.projectID = projectID;
		this.title = title;
		this.width = width;
		FieldHelpFileName = fieldHelpFileName;
		this.projectNumber = projectNumber;
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
	 * @return the title
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}

	/**
	 * @return the width
	 */
	public int getWidth() 
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) 
	{
		this.width = width;
	}

	/**
	 * @return the fieldHelpFileName
	 */
	public String getFieldHelpFileName() 
	{
		return FieldHelpFileName;
	}

	/**
	 * @param fieldHelpFileName the fieldHelpFileName to set
	 */
	public void setFieldHelpFileName(String fieldHelpFileName) 
	{
		FieldHelpFileName = fieldHelpFileName;
	}

	/**
	 * @return the projectNumber
	 */
	public int getProjectNumber() 
	{
		return projectNumber;
	}

	/**
	 * @param projectNumber the projectNumber to set
	 */
	public void setProjectNumber(int projectNumber) 
	{
		this.projectNumber = projectNumber;
	}
	
	

}
