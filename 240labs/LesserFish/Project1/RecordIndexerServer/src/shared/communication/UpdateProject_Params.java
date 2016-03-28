package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to UpdateProject()
 * 
 * @author jbelyeu
 *
 */
public class UpdateProject_Params 
{

	private Project project;
	
	/** Instantiates the UpdateProject_Params with null project
	 * 
	 */
	public UpdateProject_Params() 
	{
		project = null;
	}

	/**
	 * @return the project
	 */
	public Project getProject() 
	{
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) 
	{
		this.project = project;
	}	
}
