package server.database;

import java.util.ArrayList;
import shared.model.Record;

/** Database access class for Record objects
 * 
 * @author jbelyeu
 *
 */
public class RecordsDAO 
{
	
	private Database dataBase;
	
	/**Database access class constructor for Record objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	RecordsDAO (Database DB)
	{}
	
	/** gets all Records from database
	 * 
	 * @return list of Records
	 * @throws DatabaseException
	 */
	public ArrayList<Record> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds Record to database
	 * 
	 * @param Record
	 * @throws DatabaseException
	 */
	public void add(Record Record) throws DatabaseException
	{}
	 
	/** Updates Record in database
	 * 
	 * @param Record
	 * @throws DatabaseException
	 */
	public void update (Record Record)
	{}
	
	/** Deletes Record from database
	 * 
	 * @param Record
	 * @throws DatabaseException
	 */
	public void delete(Record Record) throws DatabaseException 
	{}
}
