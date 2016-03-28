/**
 * 
 */
package client.utilities;

/**
 * @author jon
 *
 */
public interface CustomStateListener
{
	public void cellSelectedChanged();
	
	public void batchDownloaded();
	
	public void cellValueChanged();	
	
	public void scaled();
	
	public void batchSubmitted();
	
	public void invertImage();
	
	public void loadedPositioningData();
	
	public void highlightsToggled();
}
