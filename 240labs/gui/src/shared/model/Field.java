package shared.model;

import org.w3c.dom.Element;

import server.dataLoader.DataImporter;

/**Models data for field (from batch) from database
 * 
 * @author jbelyeu
 *
 */
public class Field extends ModelSuper
{
	private int projectID;
	private String fieldTitle;
	private int XCoordinate;
	private int width;
	private String fieldHelpFileName;
	private String knownDataFileName;
	private int fieldNumber;
	
	/**  Constructor to create Field 
	 * 
	 */
	public Field () 
	{}
	
	/**Constructor to fully initialize Field. I'm not sure what ProjectNumber is, so it is inactive for now
	 * 
	 * @param iD
	 * @param projectID
	 * @param title
	 * @param xcoord 
	 * @param width
	 * @param fieldHelpFileName
	 * @param knownDataFileName
	 * @param projectNumber
	 */
	public Field (int iD, int projectID, String title, int xcoord, int width, String fieldHelpFileName, String knownDataFileName, int fieldNumber) 
	{
		super(iD);
		this.projectID = projectID;
		this.fieldTitle = title;
		this.XCoordinate = xcoord;
		this.width = width;
		this.fieldHelpFileName = fieldHelpFileName;
		this.knownDataFileName = knownDataFileName;
		this.fieldNumber = fieldNumber;
	}
	
	/**Extracts data from Element object to initialize class fields.
	 * 
	 * @param element
	 */
	public Field(Element element, int projectID, int fieldNumber)
	{
		this.projectID = projectID;
		this.fieldNumber = fieldNumber;
		fieldTitle = DataImporter.getValue((Element)element.getElementsByTagName("title").item(0));
		XCoordinate = Integer.parseInt ( DataImporter.getValue ( ( Element)element.getElementsByTagName("xcoord").item(0 ) ) );
		width = Integer.parseInt ( DataImporter.getValue ((Element ) element.getElementsByTagName("width").item(0 ) ) );
		fieldHelpFileName = DataImporter.getValue( ( Element ) element.getElementsByTagName("helphtml").item(0 ) );
		knownDataFileName = DataImporter.getValue ( ( Element ) element.getElementsByTagName("knowndata").item(0 ) );
	}
	
	/**
	 * @return the projectID
	 */
	public int getProjectID() 
	{
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) 
	{
		this.projectID = projectID;
	}

	/**
	 * @return the title
	 */
	public String getTitle() 
	{
		return fieldTitle;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) 
	{
		this.fieldTitle = title;
	}

	/**
	 * @return the width
	 */
	public int getWidth() 
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) 
	{
		this.width = width;
	}

	/**
	 * @return the fieldHelpFileName
	 */
	public String getFieldHelpFileName() 
	{
		return this.fieldHelpFileName;
	}

	/**
	 * @param fieldHelpFileName the fieldHelpFileName to set
	 */
	public void setFieldHelpFileName(String fieldHelpFileName) 
	{
		this.fieldHelpFileName = fieldHelpFileName;
	}

	/**
	 * @return the projectNumber
	 */
	public int getFieldNumber() 
	{
		return fieldNumber;
	}

	/**
	 * @param projectNumber the projectNumber to set
	 */
	public void setFieldNumber(int projectNumber) 
	{
		this.fieldNumber = projectNumber;
	}

	/**
	 * @return the fieldTitle
	 */
	public String getFieldTitle() 
	{
		return fieldTitle;
	}

	/**
	 * @param fieldTitle the fieldTitle to set
	 */
	public void setFieldTitle(String fieldTitle) 
	{
		this.fieldTitle = fieldTitle;
	}

	/**
	 * @return the xCoordinate
	 */
	public int getXCoordinate() 
	{
		return XCoordinate;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setXCoordinate(int xCoordinate) 
	{
		this.XCoordinate = xCoordinate;
	}

	/**
	 * @return the knownDataFileName
	 */
	public String getKnownDataFileName() 
	{
		return knownDataFileName;
	}

	/**
	 * @param knownDataFileName the knownDataFileName to set
	 */
	public void setKnownDataFileName(String knownDataFileName) 
	{
		this.knownDataFileName = knownDataFileName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + XCoordinate;
		result = prime
				* result
				+ ((fieldHelpFileName == null) ? 0 : fieldHelpFileName
						.hashCode());
		result = prime * result + fieldNumber;
		result = prime * result
				+ ((fieldTitle == null) ? 0 : fieldTitle.hashCode());
		result = prime
				* result
				+ ((knownDataFileName == null) ? 0 : knownDataFileName
						.hashCode());
		result = prime * result + projectID;
		result = prime * result + width;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (XCoordinate != other.XCoordinate)
			return false;
		if (fieldHelpFileName == null)
		{
			if (other.fieldHelpFileName != null)
				return false;
		}
		else
			if (!fieldHelpFileName.equals(other.fieldHelpFileName))
				return false;
		if (fieldNumber != other.fieldNumber)
			return false;
		if (fieldTitle == null)
		{
			if (other.fieldTitle != null)
				return false;
		}
		else
			if (!fieldTitle.equals(other.fieldTitle))
				return false;
		if (knownDataFileName == null)
		{
			if (other.knownDataFileName != null)
				return false;
		}
		else
			if (!knownDataFileName.equals(other.knownDataFileName))
				return false;
		if (projectID != other.projectID)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Field [projectID=" + projectID + ", fieldTitle=" + fieldTitle
				+ ", XCoordinate=" + XCoordinate + ", width=" + width
				+ ", fieldHelpFileName=" + fieldHelpFileName
				+ ", knownDataFileName=" + knownDataFileName + ", fieldNumber="
				+ fieldNumber + ", ID=" + ID + "]";
	}
	
	

}
