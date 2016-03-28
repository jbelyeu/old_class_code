package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to AddUser()
 * 
 * @author jbelyeu
 *
 */
public class AddUser_Params 
{
	private User batch;

	/** Instantiates AddUser_Params object with null User
	 * 
	 */
	public AddUser_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public User getUser() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setUser(User batch) 
	{
		this.batch = batch;
	}
}
