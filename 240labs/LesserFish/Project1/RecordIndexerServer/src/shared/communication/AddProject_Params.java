package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to AddProject()
 * 
 * @author jbelyeu
 *
 */
public class AddProject_Params 
{
	private Project batch;

	/** Instantiates AddProject_Params object with null Project
	 * 
	 */
	public AddProject_Params() 
	{
		batch = null;
	}
	
	/**
	 * @return the batch
	 */
	public Project getProject() 
	{
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setProject(Project batch) 
	{
		this.batch = batch;
	}
}
