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

import shared.model.Field;

/**
 * @author jbelyeu
 *
 */
public class FieldsDAOTest 
{
	private static Database database;
	private FieldsDAO dao;
	
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
		dao = new FieldsDAO(database);
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
	 * Test method for {@link server.database.FieldsDAO#getAll()}.
	 */
	@Test
	public void testGetAllByProjectID() 
	{

		boolean commit = true;
		try
		{
			Field field = new Field(1, 1, "TestFieldTitle", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			dao.add(field);
			ArrayList<Field> correctList = new ArrayList<Field>();
			correctList.add(field);
			ArrayList<Field> trialList = dao.getAll(field.getProjectID());
			assertEquals(correctList, trialList);
			dao.delete(field);
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
	 * Test method for {@link server.database.FieldsDAO#getAll()}.
	 */
	@Test
	public void testGetAll() 
	{

		boolean commit = true;
		try
		{
			Field field = new Field(1, 1, "TestFieldTitle", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			dao.add(field);
			ArrayList<Field> correctList = new ArrayList<Field>();
			correctList.add(field);
			ArrayList<Field> trialList = dao.getAll();
			assertEquals(correctList, trialList);
			dao.delete(field);
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
	 * Test method for {@link server.database.FieldsDAO#add(shared.model.Field)}.
	 */
	@Test
	public void testAdd() 
	{
		boolean commit = true;
		try
		{
			Field field = new Field(5, 1, "TestFieldTitleAdd", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			dao.add(field);
			Field retrievedField = dao.getField(field.getID());
			if (retrievedField != null)
			{
				assertEquals(field, retrievedField);
				dao.delete(retrievedField);
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
	 * Test method for {@link server.database.FieldsDAO#update(shared.model.Field)}.
	 */
	@Test
	public void testUpdate() 
	{
		boolean commit = true;
		try
		{
			Field field = new Field(5, 1, "TestFieldTitle", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			dao.add(field);
			field.setFieldHelpFileName("Updated");
			field.setProjectID(500);
			field.setFieldTitle("updatedTitle");
			field.setKnownDataFileName("UpdatedKnownDataFileName");
			dao.update(field);
			Field retrievedField = dao.getField(field.getID());
			assertEquals(field, retrievedField);
			dao.delete(retrievedField);
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
	 * Test method for {@link server.database.FieldsDAO#delete(shared.model.Field)}.
	 */
	@Test
	public void testDelete() 
	{
		boolean commit = true;
		try
		{
			Field field = new Field(5, 1, "TestFieldTitleDelete", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
			dao.add(field);
			dao.delete(field);
			Field retrievedField = dao.getField(field.getID());
			assertNull(retrievedField);	
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
