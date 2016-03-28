package shared.communication;

import shared.model.*;


/** Wrapper class for parameters passed to DeleteBatch()
 * 
 * @author jbelyeu
 *
 */
public class DeleteBatch_Params 
{
	private Batch batch;

	/** Instantiates DeleteBatch_Params object with null Batch
	 * 
	 */
	public DeleteBatch_Params() 
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
