package shared.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

import server.dataLoader.DataImporter;

/**Models data for indexing project from database
 * 
 * @author jbelyeu
 *
 */
public class Project extends ModelSuper
{
	private String title;
	private int recordsPerImage;
	private int firstYCoordinate;
	private int recordHeight;
	
	ArrayList<Field> fields = new ArrayList<Field>();
	ArrayList<Batch> batches = new ArrayList<Batch>();
	
	/** Constructor to create Project 
	 * 
	 */
	public Project() 
	{}
	
	
	/**Constructor to fully initialize Project 
	 * @param iD
	 * @param title
	 * @param recordsPerImage
	 * @param firstYCoordinate
	 * @param recordHeight
	 */
	public Project(int iD, String title, int recordsPerImage, int firstYCoordinate, int recordHeight) 
	{
		super(iD);
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoordinate = firstYCoordinate;
		this.recordHeight = recordHeight;
	}
	
	/**Extracts data from Element object to initialize class fields
	 * 
	 * @param element
	 */
	public Project(Element element)
	{
		title = DataImporter.getValue((Element)element.getElementsByTagName("title").item(0));
		recordsPerImage = Integer.parseInt(DataImporter.getValue((Element)element.getElementsByTagName("recordsperimage").item(0)));
		firstYCoordinate = Integer.parseInt(DataImporter.getValue((Element)element.getElementsByTagName("firstycoord").item(0)));
		recordHeight = Integer.parseInt(DataImporter.getValue((Element)element.getElementsByTagName("recordheight").item(0)));
	}

	/**
	 * @return the name
	 */
	public String getTitle() 
	{
		return this.title;
	}

	/**
	 * @param name the name to set
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}


	/**
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage()
	{
		return recordsPerImage;
	}


	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage)
	{
		this.recordsPerImage = recordsPerImage;
	}


	/**
	 * @return the firstYCoordinate
	 */
	public int getFirstYCoordinate()
	{
		return firstYCoordinate;
	}


	/**
	 * @param firstYCoordinate the firstYCoordinate to set
	 */
	public void setFirstYCoordinate(int firstYCoordinate)
	{
		this.firstYCoordinate = firstYCoordinate;
	}


	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() 
	{
		return recordHeight;
	}


	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) 
	{
		this.recordHeight = recordHeight;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((batches == null) ? 0 : batches.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		result = prime * result + firstYCoordinate;
		result = prime * result + recordHeight;
		result = prime * result + recordsPerImage;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Project other = (Project) obj;
		if (batches == null) 
		{
			if (other.batches != null)
			{
				return false;
			}
		} else if (!batches.equals(other.batches))
			return false;
		if (fields == null) 
		{
			if (other.fields != null)
				return false;
		} 
		else if (!fields.equals(other.fields))
		{
			return false;
		}
		if (firstYCoordinate != other.firstYCoordinate)
		{
			return false;
		}
		if (recordHeight != other.recordHeight)
		{
			return false;
		}
		if (recordsPerImage != other.recordsPerImage)
		{
			return false;
		}
		if (title == null) 
		{
			if (other.title != null)
			{
				return false;
			}
		}
		else if (!title.equals(other.title))
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
		return "Project [title=" + title + ", recordsPerImage="
				+ recordsPerImage + ", firstYCoordinate=" + firstYCoordinate
				+ ", recordHeight=" + recordHeight + ", fields=" + fields
				+ ", batches=" + batches + ", ID=" + ID + "]";
	}

		

}
