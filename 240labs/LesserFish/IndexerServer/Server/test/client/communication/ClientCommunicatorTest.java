/**
 * 
 */
package client.communication;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.*;
import shared.model.*;
/**
 * @author jbelyeu
 *
 */
public class ClientCommunicatorTest
{
	
	ClientCommunicator communicator;
	Database database;
	User correctUser;

	
	@BeforeClass
	public static void beforeClass()
	{
		Database.initialize();
	}
	
	/**
	 * ////@Throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		communicator = new ClientCommunicator();
		correctUser = new User(-1, "testusername", "testpassword",  "firstname", "lastname", "email", 1, null);
		database = new Database();		
		
		database.startTransaction();
		database.getUsersDAO().add(correctUser);
		database.endTransaction(true);
	}

	/**
	 * ////@Throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		database.startTransaction();
		database.getUsersDAO().delete(correctUser);
		database.endTransaction(true);
		
		Database database = null;
		communicator = null;
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#ClientCommunicator()}.
	 */
	@Test
	public void testClientCommunicator()
	{
		assertEquals(communicator.getClass(), ClientCommunicator.class);
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#validateUser(shared.communication.ValidateUserParams)}.
	 */
	@Test
	public void testValidateUser()
	{		
		ValidateUserParams params = new ValidateUserParams("testusername", "testpassword");
		ValidateUserResult result = null;
		ValidateUserResult correctResult = new ValidateUserResult(true, "firstname", "lastname", 1);
		try
		{
			result = communicator.validateUser(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		assertEquals(correctResult, result);		
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getProjects(shared.communication.GetProjectsParams)}.
	 */
	@Test
	public void testGetProjects()
	{
		GetProjectsParams params = new GetProjectsParams("testusername", "testpassword");
		GetProjectsResult result = new GetProjectsResult();
		GetProjectsResult correctResult = new GetProjectsResult();
		
		Project project = new Project(-1, "TestProjectTitle", 10, 11, 12);
		HashMap<Integer, String> correctProjectsList = new HashMap<Integer, String>();
		
		database.startTransaction();
		database.getProjectsDAO().add(project);
		project.setID(database.getProjectsDAO().getProject(project.getTitle()).getID());
		database.endTransaction(true);
		
		correctProjectsList.put(project.getID(), project.getTitle());
		correctResult.setProjects((correctProjectsList));
		
		try
		{
			result = communicator.getProjects(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	
		assertEquals(correctResult, result); 

		database.startTransaction();
		database.getProjectsDAO().delete(project);
		database.endTransaction(true);
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getSampleImage(shared.communication.GetSampleImageParams)}.
	 */
	@Test
	public void testGetSampleImage()
	{
		Batch batch = new Batch(-1, 111, "serverData/images/testFile.txt");

		database.startTransaction();
		database.getBatchesDAO().add(batch);
		database.endTransaction(true);
		GetSampleImageParams params = new GetSampleImageParams(correctUser.getUsername(), correctUser.getPassword(), 111);
		params.setHost("localhost");
		params.setPort("8080/GetFile/");
		GetSampleImageResult retrievedResult = null;
		GetSampleImageResult correctResult = new GetSampleImageResult("http://localhost:8080/GetFile/serverData/images/testFile.txt");		
		
		try
		{
			retrievedResult = communicator.getSampleImage(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		assertEquals(correctResult, retrievedResult);
		
		params.setProjectID(7);
		try
		{
			retrievedResult = communicator.getSampleImage(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		database.startTransaction();
		database.getBatchesDAO().delete(batch);
		database.endTransaction(true);
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getBatch(shared.communication.GetBatchParams)}.
	 */
	@Test
	public void testGetBatch()
	{
		Project project = new Project(1, "TestProject", 1, 1, 1);
		database.startTransaction();
		database.getProjectsDAO().add(project);
		Batch batch = new Batch(1, project.getID(), "TestImagePath");
		database.getBatchesDAO().add(batch);
		Field field = new Field(1, project.getID(), "TestFieldTitle", 1, 1, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		database.getFieldsDAO().add(field);		
		database.endTransaction(true);
		
		batch.setImagePath("http://" + "localhost" + ":" + "8080/GetFile/" + batch.getImagePath());
		field.setFieldHelpFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field.getFieldHelpFileName());
		field.setKnownDataFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field.getKnownDataFileName());
		
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(field);
		
		GetBatchParams params = new GetBatchParams(correctUser.getUsername(), correctUser.getPassword(), batch.getProjectID());
		GetBatchResult correctResult = new GetBatchResult(batch.getID(), project.getID(), batch.getImagePath(), 
							project.getFirstYCoordinate(), project.getRecordHeight(), 1, 1, fields);
		GetBatchResult retrievedResult = null;
		
		params.setHost("localhost");
		params.setPort("8080/GetFile/");
		
		try
		{
			retrievedResult = communicator.getBatch(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (retrievedResult == null)
		{
			fail("Batch not found");
			return;
		}
				
		assertEquals(correctResult, retrievedResult);
		
		database.startTransaction();
		database.getBatchesDAO().delete(batch);
		database.getFieldsDAO().delete(field);
		database.getProjectsDAO().delete(project);
		database.endTransaction(true);
	}
	
	/**
	 * Test method for {@link client.communication.ClientCommunicator#SubmitBatch(shared.communication.SubmitBatchParams)}.
	 */
	@Test
	public void testSubmitBatch()
	{
		database.startTransaction();
		Project project = new Project(1, "TestProject", 1, 1, 1);
		correctUser.setCurrentBatch("1");
		database.getUsersDAO().update(correctUser);
		database.getProjectsDAO().add(project);
		Batch batch = new Batch(1, project.getID(), "TestImagePath");
		batch.setAssigned(1);
		database.getBatchesDAO().add(batch);
		Record record = new Record(batch.getID(), 1);
		database.getRecordsDAO().add(record);
		Field realfield = new Field(-1, project.getID(), "whatever", 0, 0, "fieldhelp", null, 1);
		Field realfield2 = new Field(-1, project.getID(), "whatever", 0, 0, "fieldhelp", null, 1);
		correctUser.setCurrentBatch(Integer.toString(batch.getID()));
		database.getUsersDAO().update(correctUser);
		database.getFieldsDAO().add(realfield);
		database.getFieldsDAO().add(realfield2);
		database.endTransaction(true);
		
		LinkedList<FieldValue> fieldValues = new LinkedList<FieldValue>(); 
		FieldValue value1 = new FieldValue(-1, record.getID(), 1, "TestRecordText1");
		FieldValue value2 = new FieldValue(-1, record.getID(), 1, "TestRecordText2");
		value1.setFieldID(value1.getFieldID());
		value1.setFieldsGivenByUser(2);
		value1.setFieldID(value2.getFieldID());
		value2.setFieldsGivenByUser(2);
		fieldValues.add(value1);
		fieldValues.add(value2);
		
		try
		{
			communicator.SubmitBatch(new SubmitBatchParams(correctUser.getUsername(), correctUser.getPassword(), 
					batch.getID(), fieldValues), "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		database.startTransaction();
		ArrayList<FieldValue> resultList = database.getFieldValuesDAO().getAll();		
		FieldValue result1 = resultList.get(0);
		FieldValue result2 = resultList.get(1);		
		value1.setID(result1.getID());
		value2.setID(result2.getID());
		
		int updatedNumIndexed = database.getUsersDAO().getUser(correctUser.getID()).getRecordsIndexed();
		
		database.getFieldValuesDAO().delete(value1);
		database.getFieldValuesDAO().delete(value2);
		database.getBatchesDAO().delete(batch);
		database.getProjectsDAO().delete(project);
		database.getRecordsDAO().delete(record);
		database.getFieldsDAO().delete(realfield);
		database.getFieldsDAO().delete(realfield2);
		database.endTransaction(true);
		correctUser.setRecordsIndexed(correctUser.getRecordsIndexed() + project.getRecordsPerImage());
		
		assertEquals(value1, result1);
		assertEquals(value2, result2);
		assertEquals(correctUser.getRecordsIndexed(), updatedNumIndexed);
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#getFields(shared.communication.GetFieldsParams)}.
	 */
	@Test
	public void testGetFieldsByProjectID()
	{		
		correctUser.setCurrentBatch("TestCurrentBatch");
		database.startTransaction();
		Project project = new Project(1, "TestProject", 1, 1, 1);
		database.getProjectsDAO().add(project);
		
		Field field1 = new Field(1, project.getID(), "TestTitle1", 10, 10, "TestHelpFileName1", "TestDataFileName1", 1);
		Field field2 = new Field(1, project.getID()+1, "TestTitle2", 10, 10, "TestHelpFileName2", "TestDataFileName2", 1);
		ArrayList<Field> correctFields = new ArrayList<Field>();
		correctFields.add(field1);
		
		database.getFieldsDAO().add(field1);
		database.getFieldsDAO().add(field2);
		database.endTransaction(true);
		
		field1.setFieldHelpFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field1.getFieldHelpFileName());
		field1.setKnownDataFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field1.getKnownDataFileName());
		
		GetFieldsParams params = new GetFieldsParams(correctUser.getUsername(), correctUser.getPassword(), project.getID());
		params.setHost("localhost");
		params.setPort("8080/GetFile/");
		GetFieldsResult correct = new GetFieldsResult(correctFields);  
		GetFieldsResult result = null;
		try
		{
			result = communicator.getFields(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		database.startTransaction();
		database.getFieldsDAO().delete(field1);
		database.getFieldsDAO().delete(field2);
		database.getProjectsDAO().delete(project);
		database.endTransaction(true);

		assertEquals(result, correct);
	}

	/**
	 * Test method for {@link client.communication.ClientCommunicator#search(shared.communication.SearchParams)}.
	 */
	@Test
	public void testSearch()
	{
		database.startTransaction();
		Batch batch = new Batch(1, 1, "TestImagePathGetALL");
		database.getBatchesDAO().add(batch);
		Record record = new Record(batch.getID(), 10);
		database.getRecordsDAO().add(record);
		
		FieldValue fieldValue = new FieldValue(1, record.getID(), 10, "TestRecordText1");
		FieldValue fieldValue2 = new FieldValue(1, record.getID(), 10, "TestRecordText2");
		Field field = new Field(1, 1, "TestFieldTitle1", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		Field field2 = new Field(1, 1, "TestFieldTitle2", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		
		database.getFieldsDAO().add(field);
		database.getFieldsDAO().add(field2);
		
		fieldValue.setFieldID(field.getID());
		fieldValue2.setFieldID(field2.getID());
		
		database.getFieldValuesDAO().add(fieldValue);
		database.getFieldValuesDAO().add(fieldValue2);
		
		field.setFieldHelpFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field.getFieldHelpFileName());
		field.setKnownDataFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field.getKnownDataFileName());
		field2.setFieldHelpFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field2.getFieldHelpFileName());
		field2.setKnownDataFileName("http://" + "localhost" + ":" + "8080/GetFile/" + field2.getKnownDataFileName());
		batch.setImagePath("http://" + "localhost" + ":" + "8080/GetFile/" + batch.getImagePath());
		
		database.endTransaction(true);
		
		LinkedList<String> fieldIDs = new LinkedList<String>();
		fieldIDs.add(Integer.toString(field.getID()));
		fieldIDs.add(Integer.toString(field2.getID()));
		LinkedList<String> searchTerms = new LinkedList<String>();
		searchTerms.add("TestRecordText1");
		searchTerms.add("TestRecordText2");
		
		SearchParams params = new SearchParams(correctUser.getUsername(), correctUser.getPassword(), fieldIDs, searchTerms);
		params.setHost("localhost");
		params.setPort("8080/GetFile/");
		
		SearchResult retrieved = null;
		try
		{
			retrieved = communicator.search(params, "8080", "localhost");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Record corRecord1 = new Record(batch.getID(), batch.getImagePath(), record.getRecordNumber(), field.getID());
		Record corRecord2 = new Record(batch.getID(), batch.getImagePath(), record.getRecordNumber(), field2.getID());
		SearchResult correct  = new SearchResult();
		correct.addRecord(corRecord1);
		correct.addRecord(corRecord2);
		
		assertEquals(correct, retrieved);	
		
		database.startTransaction();
		database.getRecordsDAO().delete(record);
		database.getFieldValuesDAO().delete(fieldValue);
		database.getFieldValuesDAO().delete(fieldValue2);
		database.getFieldsDAO().delete(field);
		database.getFieldsDAO().delete(field2);
		database.getBatchesDAO().delete(batch);
		database.endTransaction(true);	
	}
	
	/**
	 * Test method for {@link client.communication.ClientCommunicator#getFile()}.
	 */
	@Test
	public void testGetFile()
	{
		String urlPath = "http://localhost:8080/GetFile/serverData/images/testFile.txt";
		String filename = "testFile.txt";
		File toRetrieve = new File ("serverData/images/testFile.txt");
		File correctFile = new File ("testFile.txt");
		
		File resultFile = null;
		try
		{			
			toRetrieve.createNewFile();
			resultFile = this.communicator.getFile(urlPath);
		}
		catch (ClientException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		assertEquals(correctFile, resultFile);
		
		correctFile.delete();
		resultFile = null;
	}
}
