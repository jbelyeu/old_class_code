package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllUsers()
 * 
 * @author jbelyeu
 *
 */
public class GetAllUsers_Result 
{
	private ArrayList<User> users;
	
	/**Instantiates the GetAllUsers_Result class with null List<User> users
	 * 
	 */
	public GetAllUsers_Result() 
	{
		users = null;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() 
	{
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(ArrayList<User> users) 
	{
		this.users = users;
	}	
}
