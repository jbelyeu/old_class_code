package server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() 
	{
	}
	
	@After
	public void teardown() 
	{
	}
	
	@Test
	public void test_1()
	{
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{
		
		String[] testClasses = new String[] 
		{
				"server.ServerUnitTests",
				"server.database.DatabaseTest",
				"server.database.DatabaseCleanerTest",
				"server.database.BatchesDAOTest",
				"server.database.FieldsDAOTest",
				"server.database.FieldValuesDAOTest",
				"server.database.ProjectsDAOTest",
				"server.database.RecordsDAOTest",
				"server.database.UsersDAOTest",
				"server.facade.ServerFacadeTest",
				"client.communication.ClientCommunicatorTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

