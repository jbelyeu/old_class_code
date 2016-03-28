/**
 * 
 */
package shared.model;

/**Abstract class to relate models
 * @author jbelyeu
 *
 */
public abstract class ModelSuper 
{
	protected int ID = -1;
		
	/**
	 * 
	 * @param id
	 */
	ModelSuper()
	{}
	
	/**
	 * 
	 * @param id
	 */
	ModelSuper(int id)
	{
		this.ID = id;
	}

	/**
	 * @return the iD
	 */
	public int getID() 
	{
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) 
	{
		ID = iD;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
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
		ModelSuper other = (ModelSuper) obj;
		if (ID != other.ID)
		{
			return false;
		}
		return true;
	}
}
