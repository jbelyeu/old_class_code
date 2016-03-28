package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to DeleteRecord()
 * 
 * @author jbelyeu
 *
 */
public class DeleteRecord_Params 
{
	private Record batch;

	/** Instantiates DeleteRecord_Params object with null Record
	 * 
	 */
	public DeleteRecord_Params() 
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
