package shared.communication;

/**Wrapper class for results of ValidateUser() method
 * 
 * @author jbelyeu
 *
 */
public class ValidateUserResult 
{
	private Boolean valid;
	private String firstName;
	private String lastName;
	private Integer numRecords = 0;	

	/** Creates fully initialized ValidateUserResult wrapper
	 * @param valid 
	 * @param firstName 
	 * @param lastName 
	 * @param numRecords 
	 */
	public ValidateUserResult(Boolean valid, String firstName, String lastName,	Integer numRecords) 
	{
		this.valid = valid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.numRecords = numRecords;
	}


	/**Creates new ValidateUserResult wrapper
	 * 
	 */
	public ValidateUserResult()
	{}


	/**
	 * @return the valid
	 */
	public Boolean getValid() 
	{
		return valid;
	}


	/**
	 * @param valid the valid to set
	 */
	public void setValid(Boolean valid) 
	{
		this.valid = valid;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() 
	{
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}


	/**
	 * @return the numRecords
	 */
	public Integer getNumRecords() 
	{
		return numRecords;
	}


	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(Integer numRecords)
	{
		this.numRecords = numRecords;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		if (this.valid)
		{
			return "TRUE\n" + this.firstName + "\n" + this.lastName + "\n" + this.numRecords; 
		}
		else
		{
			return "FALSE\n";
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((numRecords == null) ? 0 : numRecords.hashCode());
		result = prime * result + ((valid == null) ? 0 : valid.hashCode());
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
		ValidateUserResult other = (ValidateUserResult) obj;
		if (firstName == null)
		{
			if (other.firstName != null)
			{
				return false;
			}
		}
		else
			if (!firstName.equals(other.firstName))
			{	
				return false;
			}
		if (lastName == null)
		{
			if (other.lastName != null)
			{
				return false;
			}
		}
		else
			if (!lastName.equals(other.lastName))
				return false;
		if (numRecords == null)
		{
			if (other.numRecords != null)
			{
				return false;
			}
		}
		else
			if (!numRecords.equals(other.numRecords))
			{
				return false;
			}
		if (valid == null)
		{
			if (other.valid != null)
			{
				return false;
			}
		}
		else
		{
			if (!valid.equals(other.valid))
			{
				return false;
			}
		}
		return true;
	}
	

}
