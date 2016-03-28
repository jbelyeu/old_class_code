/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.GetBatchResult;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.User;

/**
 * @author jbelyeu
 *
 *
 */
public class BatchesDAOTest
{
	private static Database database;
	private BatchesDAO dao;
	
	@BeforeClass
	public static void setUpClass()
	{
		Database.initialize();
		Database.cleanDatabase();
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
		dao = new BatchesDAO(database);
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
	 * Test method for {@link server.database.BatchesDAO#getAll()}.
	 */
	@Test
	public void testGetAll()
	{
		boolean commit = true;
		try
		{
			Database.cleanDatabase();
			Batch batch = new Batch(1, 1, "TestImagePathGetALL");
			dao.add(batch);
			ArrayList<Batch> correctList = new ArrayList<Batch>();
			correctList.add(batch);
			ArrayList<Batch> trialList = dao.getAll(batch.getProjectID());
			dao.delete(trialList.get(0));
			assertEquals(trialList, correctList);
		}
		catch (Exception e)
		{
			commit = false;
			e.printStackTrace();
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.BatchesDAO#getBatch(int)}.
	 */
	@Test
	public void testGetBatch()
	{
		boolean commit = true;
		try
		{
			Batch batch = new Batch(1, 1, "TestImagePath");
			dao.add(batch);
			Batch retrievedBatch = dao.getBatch(batch.getID());
			assertEquals(retrievedBatch, batch);
			dao.delete(batch);
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
	 * Test method for {@link server.database.BatchesDAO#getBatch(int)}.
	 */
	@Test
	public void testGetBatchResult()
	{
		boolean commit = true;
		try
		{
			User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, null);
			Batch batch = new Batch(1, 1, "TestImagePath");
			Field field = new Field(1, 1, "TestFieldTitle", 1, 1, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			Project project = new Project(1, "TestProject", 1, 1, 1);
			ArrayList<Field> fields = new ArrayList<Field>();
			
			fields.add(field);
			dao.add(batch);
			database.getUsersDAO().add(user);
			database.getFieldsDAO().add(field);
			database.getProjectsDAO().add(project);
			
			GetBatchResult correctResult = new GetBatchResult(batch.getID(), project.getID(), batch.getImagePath(), 
											project.getFirstYCoordinate(), project.getRecordHeight(), 1, 1, fields);
			GetBatchResult retrievedResult = dao.getBatch(project.getID(), user.getID());
			assertEquals(correctResult, retrievedResult);		
		}
		catch (Exception e)
		{
			commit = false;
			e.printStackTrace();
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.BatchesDAO#add(shared.model.Batch, boolean)}.
	 */
	@Test
	public void testAdd()
	{
		boolean commit = true;
		try
		{
			Batch batch = new Batch(2, 1, "TestImagePath2");
			dao.add(batch);
			Batch retrievedBatch = dao.getBatch( batch.getID());
			if (retrievedBatch != null)
			{
				assertEquals(batch, retrievedBatch);
				dao.delete(retrievedBatch);
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
	 * Test method for {@link server.database.BatchesDAO#update(shared.model.Batch, boolean)}.
	 */
	@Test
	public void testUpdate()
	{
		boolean commit = true;
		try
		{
			Batch batch = new Batch(1, 1, "TestImagePathUpdate");
			dao.add(batch);
			batch.setImagePath("Updated");
			batch.setProjectID(500);
			batch.setAssigned(1);
			dao.update(batch);
			Batch retrievedBatch = dao.getBatch(batch.getID());
			assertEquals(batch, retrievedBatch);
			dao.delete(retrievedBatch);
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
	 * Test method for {@link server.database.BatchesDAO#delete(shared.model.Batch, boolean)}.
	 */
	@Test
	public void testDelete()
	{
		boolean commit = true;
		try
		{
			Batch batch = new Batch(1, 1, "TestImagePathDelete");
			dao.add(batch);
			dao.delete(batch);
			Batch retrievedBatch = dao.getBatch(batch.getID());
			assertNull(retrievedBatch);
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
