/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.SearchResult;
import shared.model.Batch;
import shared.model.Field;
import shared.model.FieldValue;
import shared.model.Record;

/**
 * @author jbelyeu
 *
 */
public class FieldValuesDAOTest
{
	private static Database database;
	private FieldValuesDAO dao;
	
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
		dao = new FieldValuesDAO(database);
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
	 * Test method for {@link server.database.FieldValuesDAO#getAll()}.
	 */
	@Test
	public void testGetAll()
	{
		try
		{
			FieldValue fieldvalue = new FieldValue(1, 1, 1, "TestRecordText");
			dao.add(fieldvalue);
			ArrayList<FieldValue> correctList = new ArrayList<FieldValue>();
			correctList.add(fieldvalue);
			ArrayList<FieldValue> trialList = dao.getAll();
			assertEquals(trialList, correctList);
			dao.delete(fieldvalue);
		}
		catch (Exception e)
		{
		}
		finally
		{
			database.endTransaction(false);
		}
	}

	/**
	 * Test method for {@link server.database.FieldValuesDAO#add(shared.model.FieldValue, boolean)}.
	 */
	@Test
	public void testAdd()
	{
		try
		{
			FieldValue fieldvalue = new FieldValue(1, 1, 1, "TestRecordText");
			dao.add(fieldvalue);
			FieldValue retrievedFieldValue = dao.getFieldValue (fieldvalue.getID());
			if (retrievedFieldValue != null)
			{
				assertEquals(fieldvalue, retrievedFieldValue);
				dao.delete(retrievedFieldValue);
			}	
			else
			{
				fail("Added batch not found");
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			database.endTransaction(false);
		}
	}

	/**
	 * Test method for {@link server.database.FieldValuesDAO#update(shared.model.FieldValue, boolean)}.
	 */
	@Test
	public void testUpdate()
	{
		try
		{
			FieldValue fieldvalue = new FieldValue(1, 1, 1, "TestRecordText");
			dao.add(fieldvalue);
			fieldvalue.setRecordText("textValue");
			fieldvalue.setRecordID(500);
			dao.update(fieldvalue);
			FieldValue retrievedFieldValue = dao.getFieldValue(fieldvalue.getID());
			assertEquals(fieldvalue, retrievedFieldValue);
			dao.delete(retrievedFieldValue);
		}
		catch (Exception e)
		{
		}
		finally
		{
			database.endTransaction(false);
		}
	}

	/**
	 * Test method for {@link server.database.FieldValuesDAO#delete(shared.model.FieldValue, boolean)}.
	 */
	@Test
	public void testDelete()
	{
		try
		{
			FieldValue fieldvalue = new FieldValue(1, 1, 1, "TestRecordText");
			dao.add(fieldvalue);
			dao.delete(fieldvalue);
			FieldValue retrievedFieldValue = dao.getFieldValue(fieldvalue.getID());
			assertNull(retrievedFieldValue);	
		}
		catch (Exception e)
		{
		}
		finally
		{
			database.endTransaction(false);
		}
	}
	
	
	@Test
	public void testSearch()
	{
		Record record = new Record(1, 10);
		FieldValue fieldValue = new FieldValue(1, 1, 10, "TestRecordText1");
		FieldValue fieldValue2 = new FieldValue(1, 1, 10, "TestRecordText2");
		Field field = new Field(1, 1, "TestFieldTitle1", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		Field field2 = new Field(1, 1, "TestFieldTitle2", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		Batch batch = new Batch(1, 1, "TestImagePathGetALL");
		
		database.startTransaction();
		database.getFieldsDAO().add(field);
		database.getFieldsDAO().add(field2);
		fieldValue.setFieldID(field.getID());
		fieldValue2.setFieldID(field2.getID());
		database.getBatchesDAO().add(batch);
		record.setBatchID(batch.getID());
		database.getRecordsDAO().add(record);
		fieldValue.setRecordID(record.getID());
		fieldValue2.setRecordID(record.getID());
		database.getFieldValuesDAO().add(fieldValue);
		database.getFieldValuesDAO().add(fieldValue2);
		
		LinkedList<String> fieldIDs = new LinkedList<String>();
		fieldIDs.add(Integer.toString(field.getID()));
		fieldIDs.add(Integer.toString(field2.getID()));
		LinkedList<String> searchTerms = new LinkedList<String>();
		searchTerms.add("TestRecordText1");
		searchTerms.add("TestRecordText2");
		
		try
		{
			SearchResult retrieved = new SearchResult(database.getFieldValuesDAO().search(fieldIDs, searchTerms));
			Record corRecord1 = new Record(batch.getID(), batch.getImagePath(), record.getRecordNumber(), field.getID());
			Record corRecord2 = new Record(batch.getID(), batch.getImagePath(), record.getRecordNumber(), field2.getID());
			SearchResult correct  = new SearchResult();
			correct.addRecord(corRecord1);
			correct.addRecord(corRecord2);
			
			assertEquals(correct, retrieved);			
		}
		catch (Exception e)
		{
		}
		finally
		{
			database.endTransaction(false);
		}
	}
}
