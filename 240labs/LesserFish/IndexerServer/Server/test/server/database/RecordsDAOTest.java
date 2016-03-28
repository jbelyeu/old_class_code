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

import shared.model.Record;

/**
 * @author jbelyeu
 *
 */
public class RecordsDAOTest
{
	private static Database database;
	private RecordsDAO dao;	
	
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
		dao = new RecordsDAO(database);
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
	 * Test method for {@link server.database.RecordsDAO#getAll()}.
	 */
	@Test
	public void testGetAll()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 1, 10);
			record.setID(1);
			dao.add(record);
			ArrayList<Record> correctList = new ArrayList<Record>();
			correctList.add(record);
			ArrayList<Record> trialList = dao.getAll();
			assertEquals(trialList, correctList);
			dao.delete(record);
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
	
	@Test
	public void testGetRecordByBatch()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 1, 10);
			record.setID(1);
			dao.add(record);
			Record result = dao.getRecordByBatch(record.getBatchID());

			assertEquals(record, result);
			dao.delete(result);
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
	
	@Test
	public void testGetRecord()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 1, 10);
			record.setID(1);
			dao.add(record);
			Record result = dao.getRecord(record.getID());

			assertEquals(record, result);
			dao.delete(result);
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
	 * Test method for {@link server.database.RecordsDAO#add(shared.model.Record, boolean)}.
	 */
	@Test
	public void testAdd()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 11, 10);
			dao.add(record);
			Record retrievedRecord = dao.getRecord( record.getID());
			
			
			if (retrievedRecord != null)
			{
				assertEquals(record, retrievedRecord);
				dao.delete(retrievedRecord);
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
	 * Test method for {@link server.database.RecordsDAO#update(shared.model.Record, boolean)}.
	 */
	@Test
	public void testUpdate()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 1, 100);
			record.setID(1);
			dao.update(record);	
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
	 * Test method for {@link server.database.RecordsDAO#delete(shared.model.Record, boolean)}.
	 */
	@Test
	public void testDelete()
	{
		boolean commit = true;
		try
		{
			Record record = new Record( 1, 10);
			record.setID(1);
			dao.add(record);
			dao.delete(record);
			Record retrievedRecord = dao.getRecord(record.getID());
			assertNull(retrievedRecord);
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
