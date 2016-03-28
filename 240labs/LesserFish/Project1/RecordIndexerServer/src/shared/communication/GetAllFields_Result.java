package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllFields()
 * 
 * @author jbelyeu
 *
 */
public class GetAllFields_Result 
{
	private ArrayList<Field> fields;
	
	/**Instantiates the GetAllFields_Result class with null List<Field> fields
	 * 
	 */
	public GetAllFields_Result() 
	{
		fields = null;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() 
	{
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) 
	{
		this.fields = fields;
	}	
}
