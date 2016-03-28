package shared.model;

/**Models data for record from database
 * 
 * @author jbelyeu
 *
 */
public class Record 
{
	private int ID;
	private int batchID;
	private int rowNumber;
	
	/** Constructor to create Record
	 * 
	 */
	public Record() 
	{}
	
	/** Constructor to fully initialize Record
	 * 
	 * @param iD (int)
	 * @param batchID (int)
	 * @param rowNumber (int)
	 */
	public Record(int iD, int batchID, int rowNumber) 
	{
		ID = iD;
		this.batchID = batchID;
		this.rowNumber = rowNumber;
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
	 * @return the rowNumber
	 */
	public int getRowNumber() 
	{
		return rowNumber;
	}
	
	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) 
	{
		this.rowNumber = rowNumber;
	}
	
	

}
