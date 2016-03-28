/**
 * 
 */
package client.userInterface;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.userInterface.login.BatchDownloader;
import client.userInterface.login.Login;
import client.utilities.BatchState;
import client.utilities.CustomStateListener;

/**
 * @author jon
 *
 */
public class IndexerMenu extends MenuBar implements CustomStateListener
{
	private static final long serialVersionUID = 1L;
	private BatchState batchState;
	private DownloadItem download;
	private LogoutItem logout;
	private ExitItem exit;
	
	public IndexerMenu (BatchState batchState)
	{
		this.batchState = batchState;
		batchState.addListener(this);
		Menu file = new Menu("File");
		download = new DownloadItem("Download Batch");
		logout = new LogoutItem("Logout");
		exit = new ExitItem("Exit");
		file.add(download);
		file.add(logout);
		file.add(exit);
		this.add(file);	
	}
	
	class DownloadItem extends MenuItem
	{

		private static final long serialVersionUID = 1L;

		public DownloadItem (String title)
		{
			super(title);
			this.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					downloadBatch();				
				}				
			});			
		}
	}
	
	class LogoutItem extends MenuItem
	{
		private static final long serialVersionUID = 1L;

		public LogoutItem (String title)
		{
			super(title);
			this.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{	
					logoutWindow();
				}				
			});		
		}
	}
	
	class ExitItem extends MenuItem
	{
		private static final long serialVersionUID = 1L;

		public ExitItem (String title)
		{
			super(title);
			
			this.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{	
					exitWindow();
				}				
			});	
		}
	}
	
	private void exitWindow()
	{
		batchState.getIndexerWindow().exitSession();
		System.exit(0);
	}
	
	private void logoutWindow()
	{
		batchState.getIndexerWindow().exitSession();
		Login login = new Login(batchState.getHost(), batchState.getPort());
		login.setVisible(true);
	}
	
	public void downloadBatch()
	{
		BatchDownloader loader = new BatchDownloader(batchState);
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellSelectedChanged()
	 */
	@Override
	public void cellSelectedChanged()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchDownloaded()
	 */
	@Override
	public void batchDownloaded()
	{
		this.download.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#cellValueChanged()
	 */
	@Override
	public void cellValueChanged()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#scaled()
	 */
	@Override
	public void scaled()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#batchSubmitted()
	 */
	@Override
	public void batchSubmitted()
	{
		this.download.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#invertImage()
	 */
	@Override
	public void invertImage()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#loadedPositioningData()
	 */
	@Override
	public void loadedPositioningData()
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see client.utilities.CustomStateListener#highlightsToggled()
	 */
	@Override
	public void highlightsToggled()
	{
		// TODO Auto-generated method stub
		
	}
}
