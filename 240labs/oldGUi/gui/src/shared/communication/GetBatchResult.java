package shared.communication;

import java.util.ArrayList;
import shared.model.*;

/** Wrapper class for parameters passed to GetBatch()
 * 
 * @author jbelyeu
 *
 */
public class GetBatchResult 
{
	private int batchID;
	private int projectID;
	private String imageURL;
	private int firstYCoord;
	private int recordHeight;
	private int numRecords = -1;
	private int numFields;
	
	private ArrayList<Field> fields;
	
	
	/**Instantiates the GetBatchResult class with null Batch
	 * 
	 */
	public GetBatchResult() 
	{}	

	/**
	 * @param batchID
	 * @param projectID
	 * @param imageURL
	 * @param firstYCoord
	 * @param recordHeight
	 * @param numRecords
	 * @param numFields
	 */
	public GetBatchResult(int batchID, int projectID, String imageURL,
			int firstYCoord, int recordHeight, int numRecords, int numFields, ArrayList<Field> fields)
	{
		this.batchID = batchID;
		this.projectID = projectID;
		this.imageURL = imageURL;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
		this.numRecords = numRecords;
		this.numFields = numFields;
		this.fields = fields;
	}

	/**
	 * @return the batchID
	 */
	public int getBatchID()
	{
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID)
	{
		this.batchID = batchID;
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
	 * @return the imageURL
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}

	/**
	 * @return the firstYCoord
	 */
	public int getFirstYCoord()
	{
		return firstYCoord;
	}

	/**
	 * @param firstYCoord the firstYCoord to set
	 */
	public void setFirstYCoord(int firstYCoord)
	{
		this.firstYCoord = firstYCoord;
	}

	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight()
	{
		return recordHeight;
	}

	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight)
	{
		this.recordHeight = recordHeight;
	}

	/**
	 * @return the numRecords
	 */
	public int getNumRecords()
	{
		return numRecords;
	}

	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(int numRecords)
	{
		this.numRecords = numRecords;
	}

	/**
	 * @return the numFields
	 */
	public int getNumFields()
	{
		return numFields;
	}

	/**
	 * @param numFields the numFields to set
	 */
	public void setNumFields(int numFields)
	{
		this.numFields = numFields;
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields()
	{
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields)
	{
		this.fields = fields;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + batchID;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + firstYCoord;
		result = prime * result
				+ ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + numFields;
		result = prime * result + numRecords;
		result = prime * result + projectID;
		result = prime * result + recordHeight;
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
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		GetBatchResult other = (GetBatchResult) obj;
		if (batchID != other.batchID)
		{
			return false;
		}
		if (fields == null)
		{
			if (other.fields != null)
			{
				return false;
			}
		}
		else
		{
			if (!fields.equals(other.fields))
			{
				return false;
			}
		}
		if (firstYCoord != other.firstYCoord)
		{
			return false;
		}
		if (imageURL == null)
		{
			if (other.imageURL != null)
			{
				return false;
			}
		}
		else
		{
			if (!imageURL.equals(other.imageURL))
			{
				return false;
			}
		}
		if (numFields != other.numFields)
		{
			return false;
		}
		if (numRecords != other.numRecords)
		{
			return false;
		}
		if (projectID != other.projectID)
		{
			return false;
		}
		if (recordHeight != other.recordHeight)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder();
		response.append(this.batchID + "\n" + this.projectID + "\n" + this.imageURL + "\n" + this.firstYCoord + "\n"
						+ this.recordHeight + "\n" + this.numRecords + "\n" + this.numFields + "\n");
		
		for (Field field : fields)
		{
			response.append(field.getID() + "\n" + field.getFieldNumber() + "\n" + field.getFieldTitle() + "\n" 
					+ field.getFieldHelpFileName() + "\n" + field.getXCoordinate() + "\n" + field.getWidth() + "\n");
			if (field.getKnownDataFileName() != null && !field.getKnownDataFileName().equals(""))
			{
				response.append(field.getKnownDataFileName() + "\n");
			}
		}		
		return response.toString();
	}
}
