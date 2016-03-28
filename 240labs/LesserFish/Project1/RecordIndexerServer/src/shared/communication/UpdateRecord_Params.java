package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateRecord()
 * 
 * @author jbelyeu
 *
 */
public class UpdateRecord_Params 
{

	private Record record;
	
	/** Instantiates the UpdateRecord_Params with null record
	 * 
	 */
	public UpdateRecord_Params() 
	{
		record = null;
	}

	/**
	 * @return the record
	 */
	public Record getRecord() 
	{
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(Record record) 
	{
		this.record = record;
	}	
}
