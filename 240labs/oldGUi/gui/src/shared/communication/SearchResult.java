package shared.communication;

import java.util.LinkedList;

import shared.model.Record;

/** Wrapper class for results from Search()
 * @author jbelyeu
 *
 */
public class SearchResult 
{
	LinkedList<Record> records;
	
	/**Creates new SearchResult wrapper object
	 *
	 */
	public SearchResult() 
	{
		records = new LinkedList<Record>();
	}

	/**
	 * @param records
	 */
	public SearchResult(LinkedList<Record> records)
	{
		this.records = records;
	}

	/**
	 * 
	 * @param rec
	 */
	public void addRecord(Record rec)
	{
		this.records.add(rec);
	}
	
	/**
	 * @return the records
	 */
	public LinkedList<Record> getRecords()
	{
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(LinkedList<Record> records)
	{
		this.records = records;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder response = new StringBuilder();
	
		for (Record rec : records)
		{
			response.append(rec.toString());
		}
		return response.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((records == null) ? 0 : records.hashCode());
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
		SearchResult other = (SearchResult) obj;
		if (records == null)
		{
			if (other.records != null)
			{
				return false;
			}
		}
		else
		{
			if (!records.equals(other.records))
			{
				return false;
			}
		}
		return true;
	}	
}
