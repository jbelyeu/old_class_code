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

/**
 * @author jon
 *
 */
public class IndexerMenu extends MenuBar
{
	private static final long serialVersionUID = 1L;
	private BatchState batchState;
	
	public IndexerMenu (BatchState batchState)
	{
		this.batchState = batchState;
		Menu file = new Menu("File");
		DownloadItem download = new DownloadItem("Download Batch");
		LogoutItem logout = new LogoutItem("Logout");
		ExitItem exit = new ExitItem("Exit");
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
	}
	
	private void logoutWindow()
	{
		Login login = new Login(batchState.getHost(), batchState.getPort());
		this.exitWindow();
		login.setVisible(true);
	}
	
	public void downloadBatch()
	{
		BatchDownloader loader = new BatchDownloader(batchState);
	}
}
