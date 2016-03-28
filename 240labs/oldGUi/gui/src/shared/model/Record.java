package shared.model;

/**Models data for record from database
 * 
 * @author jbelyeu
 *
 */
public class Record extends ModelSuper
{
	private int batchID;
	private String imageURL;
	private int recordNumber;
	private int fieldID;
	
	/** Constructor to create Record
	 * 
	 */
	public Record() 
	{}

	/**
	 * @param batchID
	 * @param imageURL
	 * @param recordNumber
	 * @param fieldID
	 */
	public Record(int batchID, String imageURL, int recordNumber, int fieldID)
	{
		this.batchID = batchID;
		this.imageURL = imageURL;
		this.recordNumber = recordNumber;
		this.fieldID = fieldID;
	}
	
	/**
	 * @param batchID
	 * @param imageURL
	 * @param recordNumber
	 * @param fieldID
	 */
	public Record(int batchID, int recordNumber)
	{
		this.batchID = batchID;
		this.recordNumber = recordNumber;
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
	 * @return the recordNumber
	 */
	public int getRecordNumber()
	{
		return recordNumber;
	}

	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(int recordNumber)
	{
		this.recordNumber = recordNumber;
	}

	/**
	 * @return the fieldID
	 */
	public int getFieldID()
	{
		return fieldID;
	}

	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(int fieldID)
	{
		this.fieldID = fieldID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return batchID + "\n" + imageURL + "\n" + recordNumber + "\n" + fieldID + "\n";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + batchID;
		result = prime * result + fieldID;
		result = prime * result
				+ ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + recordNumber;
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
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Record other = (Record) obj;
		if (batchID != other.batchID)
		{
			return false;
		}
		if (fieldID != other.fieldID)
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
		if (recordNumber != other.recordNumber)
		{
			return false;
		}
		return true;
	}	
	
}
