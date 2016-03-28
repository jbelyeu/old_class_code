package server.database;

import java.util.ArrayList;
import shared.model.FieldValue;

/** Database access class for FieldValue objects
 * 
 * @author jbelyeu
 *
 */
public class FieldValuesDAO 
{	
	private Database dataBase;
	
	/**Database access class constructor for FieldValue objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	FieldValuesDAO (Database DB)
	{}
	
	/** gets all FieldValues from database
	 * 
	 * @return list of FieldValues
	 * @throws DatabaseException
	 */
	public ArrayList<FieldValue> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds FieldValue to database
	 * 
	 * @param FieldValue
	 * @throws DatabaseException
	 */
	public void add(FieldValue FieldValue) throws DatabaseException
	{}
	 
	/** Updates FieldValue in database
	 * 
	 * @param FieldValue
	 * @throws DatabaseException
	 */
	public void update (FieldValue FieldValue)
	{}
	
	/** Deletes FieldValue from database
	 * 
	 * @param FieldValue
	 * @throws DatabaseException
	 */
	public void delete(FieldValue FieldValue) throws DatabaseException 
	{}
}
