package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to DeleteField()
 * 
 * @author jbelyeu
 *
 */
public class DeleteField_Params 
{
	private Field batch;

	/** Instantiates DeleteField_Params object with null Field
	 * 
	 */
	public DeleteField_Params() 
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
