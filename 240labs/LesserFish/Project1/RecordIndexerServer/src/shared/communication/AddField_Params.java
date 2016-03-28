package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to AddField()
 * 
 * @author jbelyeu
 *
 */
public class AddField_Params 
{
	private Field batch;

	/** Instantiates AddField_Params object with null Field
	 * 
	 */
	public AddField_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public Field getField() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setField(Field batch) 
	{
		this.batch = batch;
	}
}
