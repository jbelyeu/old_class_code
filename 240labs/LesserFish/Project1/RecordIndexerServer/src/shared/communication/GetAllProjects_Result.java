package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllProjects()
 * 
 * @author jbelyeu
 *
 */
public class GetAllProjects_Result 
{
	private ArrayList<Project> projects;
	
	/**Instantiates the GetAllProjects_Result class with null List<Project> projects
	 * 
	 */
	public GetAllProjects_Result() 
	{
		projects = null;
	}

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() 
	{
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(ArrayList<Project> projects) 
	{
		this.projects = projects;
	}	
}
