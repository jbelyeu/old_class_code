package server.database;

import java.util.ArrayList;

import shared.model.Batch;

/** Database access class for Batch objects
 * 
 * @author jbelyeu
 *
 */
public class BatchesDAO 
{
	private Database dataBase;
	
	/**Database access class constructor for Batch objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	BatchesDAO (Database DB)
	{}
	
	/** gets all batches from database
	 * 
	 * @return list of batches
	 * @throws DatabaseException
	 */
	public ArrayList<Batch> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds batch to database
	 * 
	 * @param batch
	 * @throws DatabaseException
	 */
	public void add(Batch batch) throws DatabaseException
	{}
	 
	/** Updates batch in database
	 * 
	 * @param batch
	 * @throws DatabaseException
	 */
	public void update (Batch Batch)
	{}
	
	/** Deletes batch from database
	 * 
	 * @param Batch
	 * @throws DatabaseException
	 */
	public void delete(Batch Batch) throws DatabaseException 
	{}
}
