/**
 * 
 */
package client.userInterface.login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.communication.GetProjectsParams;
import shared.communication.GetProjectsResult;
import shared.communication.GetSampleImageParams;
import client.facade.ClientFacade;
import client.userInterface.SampleImageViewer;
import client.utilities.BatchState;

/**
 * @author jon
 *
 */
public class BatchDownloader extends JDialog
{
	static final long serialVersionUID = 1L;
	protected ClientFacade facade;
	protected BatchState batchState;
	protected HashMap<String, Integer> IdsByProject;
	protected JComboBox<String> projects;
	
	public BatchDownloader(BatchState batchState)
	{
		this.batchState = batchState;
		facade = new ClientFacade();
		this.setTitle("Download Batch");
		this.setPreferredSize(new Dimension(325, 70));
		this.setResizable(false);
		this.setModal(true);
		DownloaderPanel panel = new DownloaderPanel();
		
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	class DownloaderPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;

		public DownloaderPanel()
		{
			this.setLayout(new BorderLayout());
			
			JLabel label = new JLabel("Project: ");
			label.setMinimumSize(new Dimension (120, 20));
			JPanel topRow = new JPanel();
			topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
			topRow.add(Box.createRigidArea(new Dimension(4, 20)));
			topRow.add(label);
			
			projects = new JComboBox<String>();			
			loadProjects();

			topRow.add(projects);
			projects.setMaximumSize(new Dimension(125, 25));
			
			JButton sampleImageButton = new JButton("View Sample");
			sampleImageButton.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					getSampleImage();
				}
			});
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					cancel();
				}
			});
			
			JButton downloadButton = new JButton("Download");
			downloadButton.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked (MouseEvent event)
				{
					downloadBatch();
				}
			});
			
			topRow.add(Box.createRigidArea(new Dimension(8, 20)));
			topRow.add(sampleImageButton);

			
			JPanel bottomRow = new JPanel();
			bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.LINE_AXIS));
			bottomRow.add(Box.createRigidArea(new Dimension(63, 20)));
			bottomRow.add(cancelButton);
			bottomRow.add(Box.createRigidArea(new Dimension(4, 20)));
			bottomRow.add(downloadButton);

			this.add(topRow, BorderLayout.CENTER);
			this.add(bottomRow, BorderLayout.SOUTH);
			bottomRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		}
		
		private void loadProjects()
		{
			GetProjectsParams params = new GetProjectsParams (batchState.getUsername(), batchState.getPassword());
			GetProjectsResult result = facade.getProjects(params, batchState.getHost(), batchState.getPort());
			IdsByProject = new HashMap<String, Integer>();

			Set<Integer> IDs = result.getProjects().keySet();

			for (Integer i : IDs)
			{
				String projectName = result.getProjects().get(i);
				projects.addItem(projectName);
				IdsByProject.put(projectName, i);
			}
		}
	}
	
	protected void getSampleImage()
	{
		ClientFacade facade = new ClientFacade();
		String projectTitle = (String) projects.getSelectedItem();
		Integer ID = this.IdsByProject.get(projectTitle);
		GetSampleImageParams params = new GetSampleImageParams(batchState.getUsername(), batchState.getPassword(), ID);
		params.setHost(batchState.getHost());
		params.setPort(batchState.getPort() + "/ServerData/");
		BufferedImage image = facade.getSampleImage(params, batchState.getHost(), batchState.getPort());
		String title = "Sample image from   " + projectTitle;
		SampleImageViewer viewer = new SampleImageViewer(image, title);
	}
	
	protected void cancel()
	{
		this.dispose();
	}
	
	protected void downloadBatch()
	{
		System.out.println("Batchdownloader 144ish");
	}
	
	
}
