/**
 * 
 */
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.communication.ClientCommunicator;

/**
 * @author jbelyeu
 *
 */
public class ProjectGetterFrame extends JFrame
{
	private MyController controller;
	private HashMap<String, String> IDsByProject;
	private JComboBox<String> projectList;
	private JComboBox<String> fieldList;
	private JPanel centerPiece;
	private String[] requestParams;
	
	public ProjectGetterFrame(MyController controller, String[] params)
	{
		super(params[4]);
		this.controller = controller;
		this.requestParams = params;
		
		this.setTitle("Projects");
		// Set the location of the window on the desktop
		this.setLocation(400, 250);
		this.setPreferredSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		centerPiece = new JPanel();
		this.add(centerPiece);
		projectList = new JComboBox<String>();
		projectList.setPreferredSize(new Dimension(400, 30));
		centerPiece.add(projectList);
		JButton fieldButton = new JButton("Get Associated Fields");
		
		fieldButton.addMouseListener(new MouseAdapter() 
		{
		      public void mouseClicked(MouseEvent e) 
		      {
		        String projectTitle = (String) projectList.getSelectedObjects()[0];
		        String[] fieldParams = new String[5];
		        fieldParams[0] = requestParams[0];
		        fieldParams[1] = requestParams[1];
		        fieldParams[2] = IDsByProject.get(projectTitle);
		        fieldParams[3] = requestParams[2];
		        fieldParams[4] = requestParams[3];
		        fieldList = new JComboBox<String>(); 
		        getFieldsFromServer(fieldParams, fieldList);
		        centerPiece.add(fieldList);
		        //TODO: can't get the new combo box to appear
		      }
		});
		centerPiece.add(fieldButton);
		this.getProjectsFromServer(params, projectList);
		

		// Size the window according to the preferred sizes and layout of its subcomponents
		this.pack();
		this.setVisible(true);
	}
	
	private void getProjectsFromServer(String[] params, JComboBox<String> comboBox)
	{
		String projects = controller.getProjects(params);
		String[] projectArray = projects.split("\n");
		this.IDsByProject = new HashMap<String, String>();
		
		int j = 0;
		for (int i = 0; i <= ((projectArray.length) / 2) + 1; i += 2)
		{
			this.IDsByProject.put(projectArray[i+1], projectArray[i]);
			comboBox.insertItemAt(projectArray[i+1], j);
			++j;
		}
	}
	
	private void getFieldsFromServer(String[] params, JComboBox<String> comboBox)
	{
		String fields = controller.getFields(params);
		String[] fieldsArray = fields.split("\n");
		int index = 0;
		
		for (int i = 0; i <= (fieldsArray.length / 3) + 1; i += 3)
		{
			comboBox.insertItemAt(fieldsArray[i +2], index);
			System.out.println(fieldsArray[i +2]);
			index++;
			//TODO: not getting all fields
		}
	}
}
