package server.database;

import java.util.ArrayList;
import shared.model.Field;

/** Database access class for Field objects
 * 
 * @author jbelyeu
 *
 */
public class FieldsDAO 
{
	
	private Database dataBase;
	
	/**Database access class constructor for Field objects
	 * 
	 * @param DB (instance of direct database access class)
	 */
	FieldsDAO (Database DB)
	{}
	
	/** gets all Fields from database
	 * 
	 * @return list of Fields
	 * @throws DatabaseException
	 */
	public ArrayList<Field> getAll() throws DatabaseException
	{
		return null;
	}
	
	/** Adds Field to database
	 * 
	 * @param Field
	 * @throws DatabaseException
	 */
	public void add(Field Field) throws DatabaseException
	{}
	 
	/** Updates Field in database
	 * 
	 * @param Field
	 * @throws DatabaseException
	 */
	public void update (Field Field)
	{}
	
	/** Deletes Field from database
	 * 
	 * @param Field
	 * @throws DatabaseException
	 */
	public void delete(Field Field) throws DatabaseException 
	{}
}
