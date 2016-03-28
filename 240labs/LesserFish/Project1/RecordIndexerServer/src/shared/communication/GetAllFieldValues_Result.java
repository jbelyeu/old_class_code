package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllFieldValues()
 * 
 * @author jbelyeu
 *
 */
public class GetAllFieldValues_Result 
{
	private ArrayList<FieldValue> fieldValues;
	
	/**Instantiates the GetAllFieldValues_Result class with null List<FieldValue> fieldValues
	 * 
	 */
	public GetAllFieldValues_Result() 
	{
		fieldValues = null;
	}

	/**
	 * @return the fieldValues
	 */
	public List<FieldValue> getFieldValues() 
	{
		return fieldValues;
	}

	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(ArrayList<FieldValue> fieldValues) 
	{
		this.fieldValues = fieldValues;
	}	
}
