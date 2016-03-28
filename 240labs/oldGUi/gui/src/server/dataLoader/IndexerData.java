/**
 * 
 */
package server.dataLoader;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import shared.model.*;
import server.database.*;

/**
 * @author jbelyeu
 *
 */
public class IndexerData
{
	Database database = new Database();
	private int projectID;
	
	public IndexerData (Element root)
	{
		ArrayList<Element> rootElements = DataImporter.getChildElements(root);
		ArrayList<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
		
		for ( Element userElement : userElements ) 
		{
			boolean commit = true;
			try 
			{
				database.startTransaction();
				database.getUsersDAO().add(new User(userElement));
			}
			catch (Exception e)
			{
				commit = false;
				System.out.println("Error: Failed to add new User");
				e.printStackTrace();
			}
			finally
			{
				database.endTransaction(commit);
			}
		}
		initializeProjectData(rootElements.get(1));		
	}
	
	private void initializeProjectData (Element projectsElement)
	{
		ArrayList<Element> projectElements = DataImporter.getChildElements(projectsElement);
		
		for ( Element projectElement : projectElements )
		{
			Project newProject = new Project(projectElement);

			boolean commit = true;
			try 
			{
				database.startTransaction();
				database.getProjectsDAO().add(newProject);
			}
			catch (Exception e)
			{
				commit = false;
				System.out.println("Error: Failed to add new Project");
				e.printStackTrace();
			}
			finally
			{
				database.endTransaction(commit);
			}
			this.projectID = newProject.getID();
			
			initializeFieldData(projectElement);
			initializeBatchData(projectElement);
		}
	}
	
	private void initializeFieldData (Element projectElement)
	{
		Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		ArrayList<Element> fieldElements = DataImporter.getChildElements(fieldsElement);
		
		for (int i = 0; i < fieldElements.size(); ++i )
		{
			Element fieldElement = fieldElements.get(i);
			Field newField = new Field(fieldElement, projectID, i+1);
			
			File fieldHelp = new File(DataImporter.givenPath + newField.getFieldHelpFileName());
			newField.setFieldHelpFileName ( DataImporter.storeFiles( fieldHelp, DataImporter.FIELDHELPFOLDER ) );
			
			if (newField.getKnownDataFileName() != null && !newField.getKnownDataFileName().equals(""))
			{
				File known = new File(DataImporter.givenPath + newField.getKnownDataFileName());
				newField.setKnownDataFileName(DataImporter.storeFiles(known, DataImporter.KNOWNDATAFOLDER));
			}
			
			
			boolean commit = true;
			try 
			{
				database.startTransaction();
				database.getFieldsDAO().add(newField);
			}
			catch (Exception e)
			{
				commit = false;
				System.out.println("Error: Failed to add new Field");
				e.printStackTrace();
			}
			finally
			{
				database.endTransaction(commit);
			}
		}		
	}
	
	private void initializeBatchData (Element projectElement)
	{
		Element imagesElement = (Element) projectElement.getElementsByTagName("images").item(0);
		ArrayList<Element> imagesArray = DataImporter.getChildElements(imagesElement);
		
		for (Element imageElement : imagesArray )
		{
			Batch newbatch = new Batch(imageElement, projectID);
			File image = new File(DataImporter.givenPath + newbatch.getImagePath());
			newbatch.setImagePath(DataImporter.storeFiles(image, DataImporter.IMAGEFOLDER));
			
			
			boolean commit = true;
			try 
			{
				database.startTransaction();
				database.getBatchesDAO().add(newbatch);
			}
			catch (Exception e)
			{
				commit = false;
				System.out.println("Error: Failed to add new Field");
				e.printStackTrace();
			}
			finally
			{
				database.endTransaction(commit);
			}
			
			initializeRecordData(imageElement, newbatch.getID());
		}
	}
	
	private void initializeRecordData (Element imageElement, int batchID )
	{
		
		Element recordsElement = (Element) imageElement.getElementsByTagName("records").item(0);
		if (recordsElement != null)
		{	
			ArrayList<Element> recordsArray = DataImporter.getChildElements(recordsElement);
			
			for (int i = 0; i < recordsArray.size(); ++i)
			{
				Record newRecord = new Record(batchID, i+1);
				
				boolean commit = true;
				try 
				{
					database.startTransaction();
					database.getRecordsDAO().add(newRecord);
				}
				catch (Exception e)
				{
					commit = false;
					System.out.println("Error: Failed to add new Field");
					e.printStackTrace();
				}
				finally
				{
					database.endTransaction(commit);
				}

				initializeFieldValueData(recordsArray.get(i), newRecord.getID(), i+1);
			}
		}
	}
	
	private void initializeFieldValueData (Element recordElement, int recordID, int recordNumber)
	{
		Element valuesElement = (Element) recordElement.getElementsByTagName("values").item(0);
		ArrayList<Element> valuesArray = DataImporter.getChildElements(valuesElement);	
		database.startTransaction();
		ArrayList<Field> fields = database.getFieldsDAO().getAll(projectID);
		database.endTransaction(true);
		
		for (int i = 0; i < valuesArray.size(); ++i)
		{
			Element valueElement = valuesArray.get(i);
			FieldValue value = new FieldValue(valueElement, recordID, recordNumber);
			value.setFieldID(fields.get(i%fields.size()).getID());
			if (value.getRecordText() != null && !value.getRecordText().equals(""))
			{	
				boolean commit = true;
				try 
				{
					database.startTransaction();
					database.getFieldValuesDAO().add(value);
				}
				catch (Exception e)
				{
					commit = false;
					System.out.println("Error: Failed to add new Field");
					e.printStackTrace();
				}
				finally
				{
					database.endTransaction(commit);
				}	
			}
		} 
	}
		
}
