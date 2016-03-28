package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateUser
 * 
 * @author jbelyeu
 *
 */
public class UpdateUser_Params 
{

	private User user;
	
	/** Instantiates the UpdateUser_Params with null user
	 * 
	 */
	public UpdateUser_Params() 
	{
		user = null;
	}

	/**
	 * @return the user
	 */
	public User getUser() 
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) 
	{
		this.user = user;
	}	
}
