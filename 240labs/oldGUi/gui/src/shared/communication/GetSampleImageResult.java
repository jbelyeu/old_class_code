/**
 * 
 */
package shared.communication;

/**Wrapper class for results of GetSampleImage()
 * @author jbelyeu
 *
 */
public class GetSampleImageResult 
{
	private String imageURL;

	/** creates fully initialized GetSampleImageResult wrapper object
	 * @param imageURL
	 */
	public GetSampleImageResult(String imageURL) 
	{
		this.imageURL = imageURL;
	}
	
	/** creates new GetSampleImageResult wrapper object
	 * @param imageURL
	 */
	public GetSampleImageResult() 
	{}

	/**
	 * @return the imageURL
	 */
	public String getImageURL()
	{
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) 
	{
		this.imageURL = imageURL;
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
				+ ((imageURL == null) ? 0 : imageURL.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetSampleImageResult other = (GetSampleImageResult) obj;
		if (imageURL == null)
		{
			if (other.imageURL != null)
				return false;
		}
		else
			if (!imageURL.equals(other.imageURL))
				return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.imageURL;
	}
}
