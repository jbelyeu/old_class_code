/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author admin
 *
 */
public class DatabaseCleanerTest
{
	private Database database = null;
	
	
	@BeforeClass
	public static void setupbefore()
	{
		Database.initialize();
	}
	
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
	 * Test method for {@link server.database.DatabaseCleaner#clean(server.database.Database)}.
	 */
	@Test
	public void testClean()
	{
		try
		{
			DatabaseCleaner.clean(database, false);
		}
		catch (Exception e)
		{
			fail("Failed to clean database");
		}
	}
}
