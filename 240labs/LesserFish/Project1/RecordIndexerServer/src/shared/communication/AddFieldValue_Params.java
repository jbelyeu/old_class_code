package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to AddFieldValue()
 * 
 * @author jbelyeu
 *
 */
public class AddFieldValue_Params 
{
	private FieldValue batch;

	/** Instantiates AddFieldValue_Params object with null FieldValue
	 * 
	 */
	public AddFieldValue_Params() 
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
