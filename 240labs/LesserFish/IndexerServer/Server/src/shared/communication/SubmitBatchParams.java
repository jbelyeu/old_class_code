/**
 * 
 */
package shared.communication;

import java.io.Serializable;
import java.util.LinkedList;

import shared.model.FieldValue;

/**Wrapper class for parameters for SubmitBatch()
 * @author jbelyeu
 *
 */
public class SubmitBatchParams implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String password;
	Integer batchID;
	LinkedList<FieldValue> fieldValues;
	
	/** Creates fully initialized SubmitBatchParams wrapper object
	 * @param username
	 * @param password
	 * @param batchID
	 * @param fieldValues
	 */
	public SubmitBatchParams(String username, String password, Integer batchID,	LinkedList<FieldValue> fieldValues) 
	{
		this.username = username;
		this.password = password;
		this.batchID = batchID;
		this.fieldValues = fieldValues;
	}
	
	/** Creates new SubmitBatchParams wrapper object
	 * 
	 */
	public SubmitBatchParams() 
	{}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}

	/**
	 * @return the batchID
	 */
	public Integer getBatchID() 
	{
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(Integer batchID) 
	{
		this.batchID = batchID;
	}

	/**
	 * @return the fieldValues
	 */
	public LinkedList<FieldValue> getFieldValues() 
	{
		return fieldValues;
	}

	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(LinkedList<FieldValue> fieldValues) 
	{
		this.fieldValues = fieldValues;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubmitBatchParams [username=" + username + ", password="
				+ password + ", batchID=" + batchID + ", fieldValues="
				+ fieldValues + "]";
	}
	
	
	
}
