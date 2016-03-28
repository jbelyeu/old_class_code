package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to DeleteUser()
 * 
 * @author jbelyeu
 *
 */
public class DeleteUser_Params 
{
	private User batch;

	/** Instantiates DeleteUser_Params object with null User
	 * 
	 */
	public DeleteUser_Params() 
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
