/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jbelyeu
 *
 */
public class DatabaseTest 
{
	Database database;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		Database.initialize();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		database = new Database();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception 
	{
		database = null;
	}

	/**
	 * Test method for {@link server.database.Database#getProjectsDAO()}.
	 */
	@Test
	public void testGetProjectsDAO() 
	{
		assertEquals(database.getProjectsDAO().getClass(), ProjectsDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#getUsersDAO()}.
	 */
	@Test
	public void testGetUsersDAO()
	{
		assertEquals(database.getUsersDAO().getClass(), UsersDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#getBatchesDAO()}.
	 */
	@Test
	public void testGetBatchesDAO() 
	{
		assertEquals(database.getBatchesDAO().getClass(), BatchesDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#getFieldsDAO()}.
	 */
	@Test
	public void testGetFieldsDAO() 
	{
		assertEquals(database.getFieldsDAO().getClass(), FieldsDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#getFieldValuesDAO()}.
	 */
	@Test
	public void testGetFieldValuesDAO() 
	{
		assertEquals(database.getFieldValuesDAO().getClass(), FieldValuesDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#getRecordsDAO()}.
	 */
	@Test
	public void testGetRecordsDAO() 
	{
		assertEquals(database.getRecordsDAO().getClass(), RecordsDAO.class);
	}

	/**
	 * Test method for {@link server.database.Database#startTransaction()}.
	 */
	@Test
	public void testStartTransaction()
	{
		database.startTransaction();
	}

	/**
	 * Test method for {@link server.database.Database#endTransaction(boolean)}.
	 */
	@Test
	public void testEndTransaction() 
	{
		try
		{
			database.startTransaction();
			database.endTransaction(false);
		}
		catch (Exception e)
		{
			fail();
		}
	}

}
