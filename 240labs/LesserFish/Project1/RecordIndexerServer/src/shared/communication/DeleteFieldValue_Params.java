package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to DeleteFieldValue()
 * 
 * @author jbelyeu
 *
 */
public class DeleteFieldValue_Params 
{
	private FieldValue batch;

	/** Instantiates DeleteFieldValue_Params object with null FieldValue
	 * 
	 */
	public DeleteFieldValue_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public FieldValue getFieldValue() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setFieldValue(FieldValue batch) 
	{
		this.batch = batch;
	}
}
