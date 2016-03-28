package shared.communication;

import shared.model.*;

/** Wrapper class for parameters passed to DeleteProject()
 * 
 * @author jbelyeu
 *
 */
public class DeleteProject_Params 
{
	private Project batch;

	/** Instantiates DeleteProject_Params object with null Project
	 * 
	 */
	public DeleteProject_Params() 
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
