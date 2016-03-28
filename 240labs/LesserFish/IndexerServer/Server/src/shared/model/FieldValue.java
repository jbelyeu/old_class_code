package shared.model;

import org.w3c.dom.*;

import server.dataLoader.DataImporter;

/**Models data for field value (from batch) from database
 * 
 * @author jbelyeu
 *
 */
public class FieldValue extends ModelSuper
{
	private int recordID;
	private int recordNumber;
	private String textValue;
	private int fieldsGivenByUser = 0;
	private int fieldID;
	
	/** Constructor to create FieldValue 
	 * 
	 */
	public FieldValue() 
	{}
	
	/** Constructor to fully initialize FieldValue 
	 * 
	 * @param iD
	 * @param recordID
	 * @param fieldID
	 * @param recordNumber
	 * @param recordText
	 */
	public FieldValue(int iD, int recordID, int recordNumber, String recordText) 
	{
		super(iD);
		this.recordID = recordID;
		this.recordNumber = recordNumber;
		this.textValue = recordText;
	}
	
	/**Extracts data from Element object to initialize class fields.
	 * 
	 * @param element
	 */
	public FieldValue(Element element, int recordID, int recordNumber)
	{
		this.recordID = recordID;			
		this.recordNumber = recordNumber;
		textValue = DataImporter.getValue((Element)element);
	}
	

	/**
	 * @return the recordID
	 */
	public int getRecordID() 
	{
		return recordID;
	}


	/**
	 * @param recordID the recordID to set
	 */
	public void setRecordID(int recordID) 
	{
		this.recordID = recordID;
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
	 * @return the recordText
	 */
	public String getRecordText() 
	{
		return textValue;
	}


	/**
	 * @param textValue the recordText to set
	 */
	public void setRecordText(String textValue) 
	{
		this.textValue = textValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + recordID;
		result = prime * result + recordNumber;
		result = prime * result
				+ ((textValue == null) ? 0 : textValue.hashCode());
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
		FieldValue other = (FieldValue) obj;
		if (recordID != other.recordID)
		{
			return false;
		}
		if (recordNumber != other.recordNumber)
		{
			return false;
		}
		if (textValue == null)
		{
			if (other.textValue != null)
			{
				return false;
			}
		}
		else
		{	if (!textValue.equals(other.textValue))
			{
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "FieldValue [recordID=" + recordID + ", recordNumber="
				+ recordNumber + ", textValue=" + textValue + ", ID=" + ID
				+ "]\n";
	}

	/**
	 * @return the fieldsGivenByUser
	 */
	public int getFieldsGivenByUser() {
		return fieldsGivenByUser;
	}

	/**
	 * @param fieldsGivenByUser the fieldsGivenByUser to set
	 */
	public void setFieldsGivenByUser(int fieldsGivenByUser) {
		this.fieldsGivenByUser = fieldsGivenByUser;
	}

	/**
	 * @return the fieldID
	 */
	public int getFieldID() {
		return fieldID;
	}

	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	
}
