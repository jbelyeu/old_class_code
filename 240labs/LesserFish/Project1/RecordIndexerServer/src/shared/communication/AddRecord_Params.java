package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to AddRecord()
 * 
 * @author jbelyeu
 *
 */
public class AddRecord_Params 
{
	private Record batch;

	/** Instantiates AddRecord_Params object with null Record
	 * 
	 */
	public AddRecord_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public Record getRecord() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setRecord(Record batch) 
	{
		this.batch = batch;
	}
}
