package shared.model;

import org.w3c.dom.Element;

import server.dataLoader.DataImporter;

/**Models data for batch (image) from database
 * 
 * @author jbelyeu
 *
 */
public class Batch extends ModelSuper
{	
	private int projectID = -1;
	private String imagePath = "nullpath";
	private int assigned = 0;
	
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
		super(iD);
		this.projectID = projectID;
		this.imagePath = imagePath;
	}	
	
	/**Extracts data from Element object to initialize class fields.
	 * 
	 * @param element
	 */
	public Batch(Element element, int projectID)
	{
		this.projectID = projectID;
		imagePath = DataImporter.getValue((Element)element.getElementsByTagName("file").item(0));
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
	public void setAssigned(int i) 
	{
		this.assigned = i;
	}
	
	/**
	 * @return the imagePath
	 */
	public int getAssigned() 
	{
		return this.assigned;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) 
	{
		this.imagePath = imagePath;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + projectID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Batch other = (Batch) obj;
		if (imagePath == null)
		{
			if (other.imagePath != null)
			{
				return false;
			}
		}
		else
		{	if (!imagePath.equals(other.imagePath))
			{
				return false;
			}
		}
		
		if (projectID != other.projectID)
		{
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Batch [projectID=" + projectID + ", imagePath=" + imagePath
				+ ", ID=" + ID + "]";
	}	
}
