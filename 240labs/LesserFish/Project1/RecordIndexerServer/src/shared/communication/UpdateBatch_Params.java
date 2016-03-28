package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateBatch()
 * 
 * @author jbelyeu
 *
 */
public class UpdateBatch_Params 
{

	private Batch batch;
	
	/** Instantiates the UpdateBatch_Params with null batch
	 * 
	 */
	public UpdateBatch_Params() 
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
