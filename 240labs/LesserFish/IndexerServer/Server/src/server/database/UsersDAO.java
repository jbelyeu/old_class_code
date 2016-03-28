package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.model.User;

/** Database access class for User objects
 * 
 * @author jbelyeu
 *
 */
public class UsersDAO extends DAOSuper
{
	
	/**Database access class constructor for User objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	UsersDAO (Database DB)
	{
		super(DB);
	}
	
	/** gets all Users from database
	 * 
	 * @return list of Users
	 */
	public ArrayList<User> getAll()
	{
		ArrayList<User> usersToReturn = new ArrayList<User>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, UserName, Password, FirstName, LastName, Email, RecordsIndexed, CurrentBatch FROM Users";
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next () )
			{
				int id = results.getInt(1);
				String username = results.getString(2); 
				String password = results.getString(3);
				String fname = results.getString(4);
				String lname = results.getString(5);		
				String email = results.getString(6);
				int recordsIndexed = results.getInt(7);
				String currentBatch = results.getString(8);
				
				User userToReturn = new User(id, username, password, fname, lname, email, recordsIndexed, currentBatch);
				usersToReturn.add(userToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to UsersDAO getAll()");
			e.printStackTrace();
		}

		return usersToReturn;
	}
	
	/**Gets a single User from the database by ID
	 * 
	 * @param ID
	 * @return
	 */
	public User getUser(int ID)
	{
		User userToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try
		{
			String query = "SELECT UserName, Password, FirstName, LastName, Email, RecordsIndexed, CurrentBatch"
						+ "	FROM Users WHERE ID = " + ID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();			
			
			while (results.next())
			{	
				String username = results.getString(1); 
				String password = results.getString(2);
				String fname = results.getString(3);
				String lname = results.getString(4);		
				String email = results.getString(5);
				int recordsIndexed = results.getInt(6);
				String currentBatch = results.getString(7);
				userToReturn = new User(ID, username, password, fname, lname, email, recordsIndexed, currentBatch);
		
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getUser()");
			e.printStackTrace();
		}

		return userToReturn;
	}
	
	/**Gets a single User from the database by username. 
	 * 
	 * @param ID
	 * @return
	 */
	public User getUser(String username)
	{
		User userToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		try
		{			
			String query = "SELECT ID, Password, FirstName, LastName, Email, RecordsIndexed, CurrentBatch FROM Users WHERE UserName = ?";
			
			statement = this.database.getConnection().prepareStatement(query);
			statement.setString(1, username);
			
			results = statement.executeQuery();			
			
			while (results.next())
			{
				int id = results.getInt(1);
				String password = results.getString(2);
				String fname = results.getString(3);
				String lname = results.getString(4);		
				String email = results.getString(5);
				int recordsIndexed = results.getInt(6);
				String currentBatch = results.getString(7);

				userToReturn = new User(id, username, password, fname, lname, email, recordsIndexed, currentBatch);

			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getUser()");
			e.printStackTrace();
		}
		return userToReturn;
	}
	
	/** Adds User to database
	 * 
	 * @param User
	 */
	public void add(User user) 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "INSERT INTO Users (UserName, Password, FirstName, LastName, Email, RecordsIndexed, CurrentBatch) VALUES (?, ?, ?, ?,?, ?, ?)";
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setInt(6, user.getRecordsIndexed());
			statement.setString(7, user.getCurrentBatch());			
			
			user.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in UsersDAO.add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	 
	/** Updates User in database
	 * 
	 * @param User
	 */
	public void update (User user)
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE Users SET UserName = ?, Password = ?, FirstName = ?, LastName = ?, "
					+ "Email = ?, RecordsIndexed = ?, CurrentBatch = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getUsername());
			statement.setInt(6, user.getRecordsIndexed());
			statement.setString(7, user.getCurrentBatch());	
			statement.setInt(8, user.getID());
			
			this.modifyHelper(statement, "update");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in UsersDAO.update()");
			e.printStackTrace();
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/** Deletes User from database
	 * 
	 * @param User
	 */
	public void delete (User user) 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM Users WHERE ID = ?";

			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, user.getID());	
			
			this.modifyHelper(statement, "delete");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in UsersDAO.delete()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
