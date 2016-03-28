/**
 * 
 */
package shared.communication;

import java.util.ArrayList;

import shared.model.*;

/**Wrapper class for results of GetFields()
 * @author jbelyeu
 *
 */
public class GetFieldsResult 
{
	private ArrayList<Field> fields;
	
	/**Create fully initialized GetFieldsResult wrapper object
	 * @param projectID
	 * @param fieldID
	 * @param fieldTitle
	 */
	public GetFieldsResult(ArrayList<Field> fields) 
	{
		this.fields = fields;
	}
	
	/**Create new GetFieldsResult wrapper object
	 * 
	 */
	public GetFieldsResult() 
	{}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields()
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder();
		for (Field field : fields)
		{
			response.append(field.getProjectID() + "\n" + field.getID() + "\n" + field.getFieldTitle() + "\n");
		}
		return response.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetFieldsResult other = (GetFieldsResult) obj;
		if (fields == null)
		{
			if (other.fields != null)
				return false;
		}
		else
			if (!fields.equals(other.fields))
				return false;
		return true;
	}
}
