package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllRecords()
 * 
 * @author jbelyeu
 *
 */
public class GetAllRecords_Result 
{
	private ArrayList<Record> records;
	
	/**Instantiates the GetAllRecords_Result class with null List<Record> Records
	 * 
	 */
	public GetAllRecords_Result() 
	{
		records = null;
	}

	/**
	 * @return the Records
	 */
	public List<Record> getRecords() 
	{
		return records;
	}

	/**
	 * @param Records the Records to set
	 */
	public void setRecords(ArrayList<Record> Records) 
	{
		this.records = Records;
	}	
}
