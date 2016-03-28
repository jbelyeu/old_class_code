package server.facade;

import java.util.ArrayList;
import java.util.LinkedList;

import shared.communication.GetBatchResult;
import shared.model.*;
import server.database.Database;


/**
 * 
 * @author jbelyeu
 *
 */
public class ServerFacade
{
	public static void initialize()
	{
		try
		{
			Database.initialize();
		}
		catch (Exception e)
		{
			System.out.println("Error: Failed to initialize database");
			e.printStackTrace();
		}
	}
	
	public static User validateUser(String username, String password)
	{
		Database DB = new Database();
		User user = null;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			user = DB.getUsersDAO().getUser(username);
		}
		catch (Exception e)
		{
			commit = false;
			System.out.println("Error: Failed to validate user " + username);
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		if (user != null)
		{
			return (user.getPassword().equals(password)) ? user : null;		
		}
		else
		{
			return null;
		}
	}
	
	public static ArrayList<Project> getProjects()
	{
		Database DB = new Database();
		ArrayList<Project> projects = null;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			projects = DB.getProjectsDAO().getAll();
			
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get projects");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		return projects;
	}
	
	public static String getSampleImage(int projectID, String host, String port)
	{
		Database DB = new Database();
		String imagePath = null;;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			ArrayList<Batch> batches = DB.getBatchesDAO().getAll(projectID);
			imagePath = batches.size() > 0 ? "http://" + host + ":" + port + batches.get(0).getImagePath() : null;
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get test image");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		return imagePath;
	}
	
	public static GetBatchResult downloadBatch(int projectID, int userID, String host, String port)
	{
		Database DB = new Database();
		GetBatchResult result = null;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			result = DB.getBatchesDAO().getBatch(projectID, userID);
		
			if (result == null)
			{
				return null;
			}

			User user = DB.getUsersDAO().getUser(userID);
			user.setCurrentBatch(Integer.toString(result.getBatchID()));
			DB.getUsersDAO().update(user);
			Batch batch = DB.getBatchesDAO().getBatch(result.getBatchID());
			batch.setAssigned(1);
			DB.getBatchesDAO().update(batch);
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get batch");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		if (result != null && result.getNumRecords() != -1)
		{
			result.setImageURL("http://" + host + ":" + port + result.getImageURL());
			for (Field field : result.getFields())
			{
				field.setFieldHelpFileName("http://" + host + ":" + port + field.getFieldHelpFileName());
				if (field.getKnownDataFileName() != null && !field.getKnownDataFileName().equals(""))
				{
					field.setKnownDataFileName("http://" + host + ":" + port + field.getKnownDataFileName());
				}
			}
		}
		return result;
	}
	
	public static void submitBatch(LinkedList<FieldValue> fieldValues, int batchID, User user)
	{
		Database DB = new Database();
		boolean commit = true;

		try
		{
			DB.startTransaction();
			
			Batch batch = DB.getBatchesDAO().getBatch(batchID);
			ArrayList<Field> fields = DB.getFieldsDAO().getAll(batch.getProjectID());
			int correctNumFields = fields.size();

			Record record = DB.getRecordsDAO().getRecordByBatch(batchID);
			int indexed = DB.getProjectsDAO().getProject(batch.getProjectID()).getRecordsPerImage(); 
			
			user.setRecordsIndexed(user.getRecordsIndexed() + indexed);
			user.setCurrentBatch("0");
			DB.getUsersDAO().update(user);
			
			batch.setAssigned(-100);
			DB.getBatchesDAO().update(batch);
			int recordNum = 0;
			
			for (int i = 0; i < fieldValues.size(); ++i)
			{
				FieldValue fieldValue = fieldValues.get(i);
				if (fieldValue.getFieldsGivenByUser() == correctNumFields)
				{		
					fieldValue.setRecordID(record.getID());
					int fieldID = fields.get(i % fields.size()).getID();
					fieldValue.setFieldID(fieldID);
					if (i % correctNumFields == 0)
					{
						recordNum++;
					}
					fieldValue.setRecordNumber(recordNum);
					DB.getFieldValuesDAO().add(fieldValue);
				}
			}
		}
		catch (Exception e)
		{
			commit = false;
			System.out.println("Error: Failed to submit batch");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		
	}
	
	/**
	 * 
	 * @param stringID
	 * @return
	 */
	public static ArrayList<Field> getFields(int projectID, String host, String port)
	{
		Database DB = new Database();
		ArrayList<Field> fields = null;
		boolean commit = true;

		try
		{
			DB.startTransaction();
			fields = DB.getFieldsDAO().getAll(projectID);
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get fields");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		
		for (Field field : fields)
		{
			String fieldhelp = field.getFieldHelpFileName();
			String knownData = field.getKnownDataFileName();
			
			field.setFieldHelpFileName("http://" + host + ":" + port + fieldhelp);
			if (knownData != null && !knownData.equals(""))
			{
				field.setKnownDataFileName("http://" + host + ":" + port + knownData);
			}
		}
		
		return fields;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static ArrayList<Field> getFields(String host, String port)
	{
	
		Database DB = new Database();
		ArrayList<Field> fields = null;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			fields = DB.getFieldsDAO().getAll();
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get fields");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		
		for (Field field : fields)
		{
			String fieldhelp = field.getFieldHelpFileName();
			String knownData = field.getKnownDataFileName();
			
			field.setFieldHelpFileName("http://" + host + ":" + port + fieldhelp);
			if (knownData != null && knownData != "")
			{
				field.setKnownDataFileName("http://" + host + ":" + port + knownData);
			}
		}
		return fields;
	}
	
	public static LinkedList<Record> search(LinkedList<String> fieldIDs, LinkedList<String> searchTerms, String host, String port )
	{
		Database DB = new Database();
		LinkedList<Record> records = null;
		boolean commit = true;
		
		try
		{
			DB.startTransaction();
			
			for (String SID : fieldIDs)
			{
				int ID = Integer.parseInt(SID);
				Field field = DB.getFieldsDAO().getField(ID);
				if (field == null)
				{
					throw new Exception();
				}
			}
			
			records = DB.getFieldValuesDAO().search(fieldIDs, searchTerms);
		}
		catch (Exception e) 
		{
			commit = false;
			System.out.println("Error: Failed to get fieldValues");
			e.printStackTrace();
		}
		finally
		{
			DB.endTransaction(commit);
		}
		
		for (Record record : records)
		{
			String image = record.getImageURL();
			record.setImageURL("http://" + host + ":" + port + image);
		}
		return records;
	}
}
