package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import shared.model.Project;

/** Database access class for Project objects
 * 
 * @author jbelyeu
 *
 */
public class ProjectsDAO extends DAOSuper
{	
	/**Database access class constructor for Project objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	ProjectsDAO (Database DB)
	{
		super(DB);
	}
	
	/** gets all Projects from database
	 * 
	 * @return list of Projects
	 */
	public ArrayList<Project> getAll()
	{
		ArrayList<Project> projectsToReturn = new ArrayList<Project>();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try
		{
			String query = "SELECT ID, Title, RecordsPerImage, FirstYCoordinate, RecordHeight FROM Projects";
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();

			while ( results.next() )
			{
				int id = results.getInt(1);
				String title = results.getString(2);
				int recsPerImage = results.getInt(3);
				int yCoord = results.getInt(4);
				int recHeight = results.getInt(5);
				
				Project projectToReturn = new Project(id, title, recsPerImage, yCoord, recHeight);
				projectsToReturn.add(projectToReturn);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to ProjectsDAO getAll()");
			e.printStackTrace();
		}

		return projectsToReturn;
	}
	
	/**Gets a single project from the database by ID
	 * 
	 * @param projectID
	 * @return Project
	 */
	public Project getProject(int projectID)// throws DatabaseException
	{
		Project projectToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try
		{
			String query = "SELECT Title, RecordsPerImage, FirstYCoordinate, RecordHeight FROM Projects WHERE ID = " + projectID;
			statement = this.database.getConnection().prepareStatement(query);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				String title = results.getString(1);
				int recordsPerImage = results.getInt(2);
				int firstYCoord = results.getInt(3);
				int recordHeight = results.getInt(4);
				projectToReturn = new Project(projectID, title, recordsPerImage, firstYCoord, recordHeight);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getProject()");
			e.printStackTrace();
		}
		return projectToReturn;
	}
	
	/**Gets a single project from the database by ID
	 * 
	 * @param projectID
	 * @return Project
	 */
	public Project getProject(String title)// throws DatabaseException
	{
		Project projectToReturn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try
		{
			String query = "SELECT ID, RecordsPerImage, FirstYCoordinate, RecordHeight FROM Projects WHERE Title = ?";
			statement = this.database.getConnection().prepareStatement(query);
			statement.setString(1, title);
			results = statement.executeQuery();
			
			while ( results.next() )
			{
				int ID = results.getInt(1);
				int recordsPerImage = results.getInt(2);
				int firstYCoord = results.getInt(3);
				int recordHeight = results.getInt(4);
				projectToReturn = new Project(ID, title, recordsPerImage, firstYCoord, recordHeight);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed call to getProject()");
			e.printStackTrace();
		}

		return projectToReturn;
	}
	
	/** Adds Project to database
	 * 
	 * @param Project
	 */
	public void add(Project project)
	{
		PreparedStatement statement = null;		

		try 
		{
			String sql = "INSERT INTO Projects (Title, RecordsPerImage, FirstYCoordinate, RecordHeight) VALUES (?, ?, ?, ?)";
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, project.getTitle());
			statement.setInt(2, project.getRecordsPerImage());
			statement.setInt(3, project.getFirstYCoordinate());
			statement.setInt(4, project.getRecordHeight());

			project.setID(this.addHelper(statement));
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in ProjectsDAO.add()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	/** Updates Project in database
	 * 
	 * @param Project
	 */
	public void update (Project project) //throws DatabaseException
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "UPDATE Projects SET Title = ?, RecordsPerImage = ?, FirstYCoordinate = ?, RecordHeight = ? WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setString(1, project.getTitle());
			statement.setInt(2, project.getRecordsPerImage());
			statement.setInt(3, project.getFirstYCoordinate());
			statement.setInt(4, project.getRecordHeight());
			statement.setInt(5, project.getID());
			
			this.modifyHelper(statement, "update");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in ProjectsDAO.update()");
			e.printStackTrace();
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	/** Deletes Project from database
	 * 
	 * @param Project
	 * @throws DatabaseException
	 */
	public void delete(Project project) //throws DatabaseException 
	{
		PreparedStatement statement = null;

		try 
		{
			String sql = "DELETE FROM Projects WHERE ID = ?";
			
			statement = this.database.getConnection().prepareStatement(sql);
			statement.setInt(1, project.getID());
			
			this.modifyHelper(statement, "delete");		
		}
		catch (SQLException e)
		{
			System.out.println("Error: Failed to prepare statement in ProjectsDAO.delete()");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
