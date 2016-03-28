/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import shared.model.User;

/**
 * @author jbelyeu
 *
 */
public class UsersDAOTest
{
	private static Database database;
	private UsersDAO dao;	
	
	@BeforeClass
	public static void setUpClass()
	{
		Database.initialize();
	}
	
	@AfterClass
	public static void tearDownClass()
	{
		Database.cleanDatabase();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		database = new Database();
		dao = new UsersDAO(database);
		database.startTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		dao = null;
		database = null;
	}

	/**
	 * Test method for {@link server.database.UsersDAO#getAll()}.
	 */
	@Test
	public void testGetAll()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.add(user);
			ArrayList<User> correctList = new ArrayList<User>();
			correctList.add(user);
			ArrayList<User> trialList = dao.getAll();
			assertEquals(trialList, correctList);
			dao.delete(user);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.UsersDAO#getUser(java.lang.Integer)}.
	 */
	@Test
	public void testGetUserByID()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.add(user);
			User retrievedUser = dao.getUser(user.getID());
			assertEquals(retrievedUser, user);
			dao.delete(user);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.UsersDAO#getUser(java.lang.String)}.
	 */
	@Test
	public void testGetUserByUsername()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.add(user);
			User retrievedUser = dao.getUser(user.getUsername());
			assertEquals(retrievedUser, user);
			dao.delete(user);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.UsersDAO#add(shared.model.User, boolean)}.
	 */
	@Test
	public void testAdd()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName2", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.add(user);
			User retrievedUser = dao.getUser( user.getID());
			if (retrievedUser != null)
			{
				assertEquals(user, retrievedUser);
				dao.delete(retrievedUser);
			}	
			else
			{
				fail("Added batch not found");
			}
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}	
	}

	/**
	 * Test method for {@link server.database.UsersDAO#update(shared.model.User, boolean)}.
	 */
	@Test
	public void testUpdate()
	{

		boolean commit = true;
		try
		{	
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.update(user);	
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.UsersDAO#delete(shared.model.User, boolean)}.
	 */
	@Test
	public void testDelete()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, "TestCurrentBatch");
			dao.add(user);
			dao.delete(user);
			User retrievedUser = dao.getUser(user.getID());
			assertNull(retrievedUser);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}
}
