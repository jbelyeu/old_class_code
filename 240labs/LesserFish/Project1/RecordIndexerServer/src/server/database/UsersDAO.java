package server.database;

import java.util.ArrayList;
import shared.model.User;

/** Database access class for User objects
 * 
 * @author jbelyeu
 *
 */
public class UsersDAO 
{
	private Database dataBase;
	
	/**Database access class constructor for User objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	UsersDAO (Database DB)
	{}
	
	/** gets all Users from database
	 * 
	 * @return list of Users
	 * @throws DatabaseException
	 */
	public ArrayList<User> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds User to database
	 * 
	 * @param User
	 * @throws DatabaseException
	 */
	public void add(User User) throws DatabaseException
	{}
	 
	/** Updates User in database
	 * 
	 * @param User
	 * @throws DatabaseException
	 */
	public void update (User User)
	{}
	
	/** Deletes User from database
	 * 
	 * @param User
	 * @throws DatabaseException
	 */
	public void delete(User User) throws DatabaseException 
	{}
}
