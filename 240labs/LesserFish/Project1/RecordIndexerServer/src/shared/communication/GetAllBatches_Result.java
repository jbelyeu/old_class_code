package shared.communication;

import java.util.*;

import shared.model.*;

/** Wrapper class for parameters passed to GetAllBatches()
 * 
 * @author jbelyeu
 *
 */
public class GetAllBatches_Result 
{
	private ArrayList<Batch> batches;
	
	/**Instantiates the GetAllBatches_Result class with null List<Batch> batches
	 * 
	 */
	public GetAllBatches_Result() 
	{
		batches = null;
	}

	/**
	 * @return the batches
	 */
	public List<Batch> getBatches() 
	{
		return batches;
	}

	/**
	 * @param batches the batches to set
	 */
	public void setBatches(ArrayList<Batch> batches) 
	{
		this.batches = batches;
	}	
}
