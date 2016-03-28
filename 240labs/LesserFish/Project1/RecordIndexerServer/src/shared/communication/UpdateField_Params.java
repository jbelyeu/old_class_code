package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateField()
 * 
 * @author jbelyeu
 *
 */
public class UpdateField_Params 
{

	private Field field;
	
	/** Instantiates the UpdateField_Params with null field
	 * 
	 */
	public UpdateField_Params() 
	{
		field = null;
	}

	/**
	 * @return the field
	 */
	public Field getField() 
	{
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) 
	{
		this.field = field;
	}	
}
