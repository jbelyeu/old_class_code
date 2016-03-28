package shared.model;

import org.w3c.dom.Element;

import server.dataLoader.DataImporter;

/**Models data for user from database
 * 
 * @author jbelyeu
 *
 */
public class User extends ModelSuper
{
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int recordsIndexed;
	private String currentBatch;
	
	/** Constructor to create User
	 * 
	 */
	public User()
	{}
	
		
	/** Constructor to fully initialize User
	 * @param iD
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param recordsIndexed
	 * @param currentBatch
	 */
	public User(int iD, String username, String password, String firstName,
			String lastName, String email, int recordsIndexed, String currentBatch) 
	{
		super(iD);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.recordsIndexed = recordsIndexed;
		this.currentBatch = currentBatch;
	}

	/**Extracts data from Element object to initialize class fields
	 * 
	 * @param element
	 */
	public User(Element element)
	{		
		username = DataImporter.getValue((Element)element.getElementsByTagName("username").item(0));
		password = DataImporter.getValue((Element)element.getElementsByTagName("password").item(0));
		firstName = DataImporter.getValue((Element)element.getElementsByTagName("firstname").item(0));
		lastName = DataImporter.getValue((Element)element.getElementsByTagName("lastname").item(0));
		email = DataImporter.getValue((Element)element.getElementsByTagName("email").item(0));
		recordsIndexed = Integer.parseInt(DataImporter.getValue((Element)element.getElementsByTagName("indexedrecords").item(0)));
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
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
	 * @return the recordsIndexed
	 */
	public int getRecordsIndexed() 
	{
		return recordsIndexed;
	}
	
	/**
	 * @param recordsIndexed the recordsIndexed to set
	 */
	public void setRecordsIndexed(int recordsIndexed) 
	{
		this.recordsIndexed = recordsIndexed;
	}
	
	/**
	 * @return the currentBatch
	 */
	public String getCurrentBatch() 
	{
		return currentBatch;
	}
	
	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setCurrentBatch(String currentBatch) 
	{
		this.currentBatch = currentBatch;
	}

	/**
	 * @return the email
	 */
	public String getEmail() 
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result	+ ((currentBatch == null) ? 0 : currentBatch.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result	+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result	+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result	+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + recordsIndexed;
		result = prime * result	+ ((username == null) ? 0 : username.hashCode());
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
		if ( ! super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		User other = (User) obj;
		if (currentBatch == null) 
		{
			if (other.currentBatch != null)
			{
				return false;
			}
		} 
		else if (!currentBatch.equals(other.currentBatch))
		{
			return false;
		}
		if (email == null)
		{
			if (other.email != null)
			{
				return false;
			}
		} 
		else if (!email.equals(other.email))
		{
			return false;
		}
		if (firstName == null) 
		{
			if (other.firstName != null)
			{
				return false;
			}
		}
		else if (!firstName.equals(other.firstName))
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
		else if (!lastName.equals(other.lastName))
		{
			return false;
		}
		if (password == null) 
		{
			if (other.password != null)
			{
				return false;
			}
		} 
		else if (!password.equals(other.password))
		{
			return false;
		}
		if (recordsIndexed != other.recordsIndexed)
		{
			return false;
		}
		if (username == null) 
		{
			if (other.username != null)
			{
				return false;
			}
		} 
		else if (!username.equals(other.username))
		{
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "User [username=" + username + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", recordsIndexed=" + recordsIndexed
				+ ", currentBatch=" + currentBatch + ", ID=" + ID + "]";
	}


	
	
	
}
