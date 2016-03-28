package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetProjects()
 * 
 * @author jbelyeu
 *
 */
public class GetProjectsResult 
{	
	/**
	 * Maps Project ID to Project Title
	 */
	private HashMap<Integer, String> projects;
	
	/**Instantiates the GetProjectsResult class with null private HashMap<Integer, String> projects
	 * 
	 */
	public GetProjectsResult() 
	{
		projects = null;
	}

	/**
	 * @return the projects
	 */
	public HashMap<Integer, String> getProjects() 
	{
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(HashMap<Integer, String> projects)
	{
		this.projects = projects;
	}
	
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(ArrayList<Project> projectArray)
	{
		this.projects = new HashMap<Integer, String>();
		
		for (Project project : projectArray)
		{
			projects.put(project.getID(), project.getTitle());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder();
		Set<Integer> keys = projects.keySet();
		
		for (Integer i : keys)
		{
			response.append(i.toString() + "\n" + projects.get(i) + "\n");
		}
		return response.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result	+ ((projects == null) ? 0 : projects.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		GetProjectsResult other = (GetProjectsResult) obj;
		if (projects == null)
		{
			if (other.projects != null)
			{
				return false;
			}
		}
		else
		{	if (!projects.equals(other.projects))
			{
				return false;
			}
		}
		return true;
	}
}
