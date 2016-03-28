package server.database;

import java.util.ArrayList;
import shared.model.Project;

/** Database access class for Project objects
 * 
 * @author jbelyeu
 *
 */
public class ProjectsDAO 
{
	private Database dataBase;
	
	/**Database access class constructor for Project objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	ProjectsDAO (Database DB)
	{}
	
	/** gets all Projects from database
	 * 
	 * @return list of Projects
	 * @throws DatabaseException
	 */
	public ArrayList<Project> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds Project to database
	 * 
	 * @param Project
	 * @throws DatabaseException
	 */
	public void add(Project Project) throws DatabaseException
	{}
	 
	/** Updates Project in database
	 * 
	 * @param Project
	 * @throws DatabaseException
	 */
	public void update (Project Project)
	{}
	
	/** Deletes Project from database
	 * 
	 * @param Project
	 * @throws DatabaseException
	 */
	public void delete(Project Project) throws DatabaseException 
	{}
}
