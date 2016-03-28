package shared.model;

/**Models data for field value (from batch) from database
 * 
 * @author jbelyeu
 *
 */
public class FieldValue 
{

	private int ID;
	private int recordID;
	private int fieldID;
	private int recordNumber;
	private String recordText;
	
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
	public FieldValue(int iD, int recordID, int fieldID, int recordNumber, String recordText) 
	{
		ID = iD;
		this.recordID = recordID;
		this.fieldID = fieldID;
		this.recordNumber = recordNumber;
		this.recordText = recordText;
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
		return recordText;
	}


	/**
	 * @param recordText the recordText to set
	 */
	public void setRecordText(String recordText) 
	{
		this.recordText = recordText;
	}
}
