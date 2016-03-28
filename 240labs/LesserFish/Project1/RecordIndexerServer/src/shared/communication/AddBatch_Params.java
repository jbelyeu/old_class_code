package shared.communication;

import shared.model.*;


/** Wrapper class for parameters passed to AddBatch()
 * 
 * @author jbelyeu
 *
 */
public class AddBatch_Params 
{
	private Batch batch;

	/** Instantiates AddBatch_Params object with null Batch
	 * 
	 */
	public AddBatch_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public Batch getBatch() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) 
	{
		this.batch = batch;
	}
}
