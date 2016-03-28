package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateFieldValue()
 * 
 * @author jbelyeu
 *
 */
public class UpdateFieldValue_Params 
{

	private FieldValue fieldValue;
	
	/** Instantiates the UpdateFieldValue_Params with null fieldValue
	 * 
	 */
	public UpdateFieldValue_Params() 
	{
		fieldValue = null;
	}

	/**
	 * @return the fieldValue
	 */
	public FieldValue getFieldValue() 
	{
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(FieldValue fieldValue) 
	{
		this.fieldValue = fieldValue;
	}	
}
