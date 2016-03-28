/**
 * 
 */
package server.facade;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;
import shared.communication.GetBatchResult;
import shared.communication.SearchResult;
import shared.model.*;

/**
 * @author jbelyeu
 *
 */
public class ServerFacadeTest
{
	Database database = null;
	
	/**init the Server Facade
	 * 
	 */
	@BeforeClass
	public static void setupClass()
	{
		 
	}

	/**
	 * @Throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		database = new Database();
		
	}

	/**
	 * @Throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		database = null;
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#validateUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testValidateUser()
	{
		User correctUser = new User(-1, "testusername", "testpassword",  "firstname", "lastname", "email", 1, null);
		
		database.startTransaction();
		database.getUsersDAO().add(correctUser);
		database.endTransaction(true);
		
		String username = "testusername";
		String password = "testpassword";
		User resultUser = new User();
		
		resultUser = ServerFacade.validateUser(username, password);
				

		assertEquals(correctUser, resultUser);
		database.startTransaction();
		database.getUsersDAO().delete(correctUser);
		database.endTransaction(true);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#getProjects()}.
	 */
	@Test
	public void testGetProjects()
	{
		ArrayList<Project> correctProjects = new ArrayList<Project>(); 
		Project project = new Project(1, "TestProject", 1, 10, 10);
		correctProjects.add(project);
				
		database.startTransaction();
		database.getProjectsDAO().add(project);
		database.endTransaction(true);
		
		ArrayList<Project> resultProjects = ServerFacade.getProjects();
		
		assertEquals(correctProjects, resultProjects);
		database.startTransaction();
		database.getProjectsDAO().delete(project);
		database.endTransaction(true);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#getSampleImage(int)}.
	 */
	@Test
	public void testGetSampleImage()
	{
		Batch batch = new Batch(1, 1, "TestImagePath");
		database.startTransaction();
		database.getBatchesDAO().add(batch);
		database.endTransaction(true);
		
		String resultPath = ServerFacade.getSampleImage(batch.getProjectID(), "host", "port");
		
		assertEquals("http://" + "host" + ":" + "port" + batch.getImagePath(), resultPath);
		database.startTransaction();
		database.getBatchesDAO().delete(batch);
		database.endTransaction(true);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#downloadBatch(int)}.
	 */
	@Test
	public void testDownloadBatch()
	{
		database.startTransaction();
		Project project = new Project(1, "TestProjectForDownloadBatch", 1, 10, 10);
		database.getProjectsDAO().add(project);
		
		Batch batch = new Batch(1, project.getID(), "TestImagePath");
		Field field = new Field(1, project.getID(), "TestFieldTitle", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, null);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(field);
		
		database.getBatchesDAO().add(batch);
		database.getUsersDAO().add(user);
		database.getFieldsDAO().add(field);
		database.endTransaction(true);
		

		field.setFieldHelpFileName("http://host:portTestFieldHelpFileName" );
		field.setKnownDataFileName(("http://host:portTestKnownDataFileName" ));
		batch.setImagePath("http://host:port" + batch.getImagePath());
		
		GetBatchResult result = ServerFacade.downloadBatch(project.getID(), user.getID(), "host", "port");
		project.setID(result.getProjectID());		
		GetBatchResult correct = new GetBatchResult(batch.getID(), project.getID(), batch.getImagePath(), 
				project.getFirstYCoordinate(), project.getRecordHeight(), project.getRecordsPerImage(), 1, fields); 
				
		database.startTransaction();
		database.getBatchesDAO().delete(batch);
		database.getProjectsDAO().delete(project);
		database.getUsersDAO().delete(user);
		database.getFieldsDAO().delete(field);
		database.endTransaction(true);
			
		assertEquals(correct, result);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#submitBatch(java.util.LinkedList)}.
	 */
	@Test
	public void testSubmitBatch()
	{
		Field field = new Field(-1, -1, "FielTitle", 10, 10, "help", "known", 1);
		database.startTransaction();
		Project project = new Project(1, "TestProject", 1, 1, 1);
		User user = new User(1, "TestUserName", "TestPassword", "TestFName", "TestLName",  "TestEmail",  1, null);
		database.getUsersDAO().add(user);
		database.getProjectsDAO().add(project);
		field.setProjectID(project.getID());
		Batch batch = new Batch(1, project.getID(), "TestImagePath");
		database.getFieldsDAO().add(field);
		database.getBatchesDAO().add(batch);
		user.setCurrentBatch("1");
		Record record = new Record(batch.getID(), 1);
		database.getRecordsDAO().add(record);
		database.endTransaction(true);
		
		LinkedList<FieldValue> fieldValues = new LinkedList<FieldValue>(); 
		FieldValue value1 = new FieldValue(-1, 1, 1, "TestRecordText1");
		FieldValue value2 = new FieldValue(-1, 1, 1, "TestRecordText2");
		value1.setFieldID(1);
		value1.setFieldsGivenByUser(1);
		value1.setFieldID(2);
		value2.setFieldsGivenByUser(1);
		
		fieldValues.add(value1);
		fieldValues.add(value2);
		ServerFacade.submitBatch(fieldValues, batch.getID(), user);
		
		database.startTransaction();
		FieldValue result1 = database.getFieldValuesDAO().getFieldValue(value1.getID());
		FieldValue result2 = database.getFieldValuesDAO().getFieldValue(value2.getID());
		int updatedNumIndexed = database.getUsersDAO().getUser(user.getID()).getRecordsIndexed();
		
		database.getFieldValuesDAO().delete(value1);
		database.getFieldValuesDAO().delete(value2);
		database.getBatchesDAO().delete(batch);
		database.getUsersDAO().delete(user);
		database.getProjectsDAO().delete(project);
		database.getRecordsDAO().delete(record);
		database.getFieldsDAO().delete(field);
		database.endTransaction(true);
		
		assertEquals(value1, result1);
		assertEquals(value2, result2);
		assertEquals(user.getRecordsIndexed(), updatedNumIndexed);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#getFields(int)}.
	 */
	@Test
	public void testGetFieldsByProjectID()
	{
		Field field1 = new Field(1, 11, "TestTitle1", 10, 10, "TestHelpFileName1", "TestDataFileName1", 1);
		Field field2 = new Field(1, 10, "TestTitle2", 10, 10, "TestHelpFileName2", "TestDataFileName2", 1);
		ArrayList<Field> correctFields = new ArrayList<Field>();
				
		correctFields.add(field1);
		
		database.startTransaction();
		database.getFieldsDAO().add(field1);
		database.getFieldsDAO().add(field2);
		database.endTransaction(true);
		
		field1.setFieldHelpFileName("http://host:portTestHelpFileName1" );
		field1.setKnownDataFileName(("http://host:portTestDataFileName1" ));
		
		ArrayList<Field> resultFields = ServerFacade.getFields(field1.getProjectID(), "host", "port");

		database.startTransaction();
		database.getFieldsDAO().delete(field1);
		database.getFieldsDAO().delete(field2);
		database.endTransaction(true);
		
		assertEquals(correctFields, resultFields);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#getFields()}.
	 */
	@Test
	public void testGetFields()
	{
		Field field1 = new Field(1, 11, "TestTitle1", 10, 10, "TestHelpFileName1", "TestDataFileName1", 1);
		Field field2 = new Field(1, 10, "TestTitle2", 10, 10, "TestHelpFileName2", "TestDataFileName2", 1);
		ArrayList<Field> correctFields = new ArrayList<Field>();
		correctFields.add(field1);
		correctFields.add(field2);
		
		database.startTransaction();
		database.getFieldsDAO().add(field1);
		database.getFieldsDAO().add(field2);
		database.endTransaction(true);
		
		ArrayList<Field> resultFields = ServerFacade.getFields("host", "port");
		
		field1.setFieldHelpFileName("http://host:portTestHelpFileName1" );
		field1.setKnownDataFileName(("http://host:portTestDataFileName1" ));
		field2.setFieldHelpFileName("http://host:portTestHelpFileName2" );
		field2.setKnownDataFileName(("http://host:portTestDataFileName2" ));
		
		database.startTransaction();
		database.getFieldsDAO().delete(field1);
		database.getFieldsDAO().delete(field2);
		database.endTransaction(true);
		
		assertEquals(correctFields, resultFields);
	}

	/**
	 * Test method for {@link server.facade.ServerFacade#search()}.
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
		fieldValue.setFieldsGivenByUser(2);
		fieldValue2.setFieldsGivenByUser(2);
		Field field = new Field(1, 1, "TestFieldTitle1", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);
		Field field2 = new Field(1, 1, "TestFieldTitle2", 10, 10, "TestFieldHelpFileName", "TestKnownDataFileName", 1);

		database.getFieldsDAO().add(field);
		database.getFieldsDAO().add(field2);
		fieldValue.setFieldID(field.getID());
		fieldValue2.setFieldID(field2.getID());
		database.getFieldValuesDAO().add(fieldValue);
		database.getFieldValuesDAO().add(fieldValue2);
		database.endTransaction(true);
		
		field.setFieldHelpFileName("http://host:portTestHelpFileName1" );
		field.setKnownDataFileName(("http://host:portTestDataFileName1" ));
		field2.setFieldHelpFileName("http://host:portTestHelpFileName2" );
		field2.setKnownDataFileName(("http://host:portTestDataFileName2" ));
		batch.setImagePath("http://host:port" + batch.getImagePath());
		
		LinkedList<String> fieldIDs = new LinkedList<String>();
		fieldIDs.add(Integer.toString(field.getID()));
		fieldIDs.add(Integer.toString(field2.getID()));
		LinkedList<String> searchTerms = new LinkedList<String>();
		searchTerms.add("TestRecordText1");
		searchTerms.add("TestRecordText2");
		
		SearchResult retrieved = new SearchResult(ServerFacade.search(fieldIDs, searchTerms, "host", "port"));
		
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
}
