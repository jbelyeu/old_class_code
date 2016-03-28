/**
 * 
 */
package server.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Project;

/**
 * @author jbelyeu
 *
 */
public class ProjectsDAOTest
{
	private static Database database;
	private ProjectsDAO dao;
	
	
	@BeforeClass
	public static void setUpClass()
	{
		Database.initialize();
	}
	
	@AfterClass
	public static void tearDownClass()
	{
		Database.cleanDatabase();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		database = new Database();
		dao = new ProjectsDAO(database);
		database.startTransaction();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		dao = null;
		database = null;
	}

	/**
	 * Test method for {@link server.database.ProjectsDAO#getAll()}.
	 */
	@Test
	public void testGetAll()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject", 1, 10, 10);
			dao.add(project);
			ArrayList<Project> correctList = new ArrayList<Project>();
			correctList.add(project);
			ArrayList<Project> trialList = dao.getAll();
			assertEquals(trialList, correctList);
			dao.delete(project);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.ProjectsDAO#getProject(int)}.
	 */
	@Test
	public void testGetProject()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject", 1, 10, 10);
			dao.add(project);
			Project retrievedProject = dao.getProject(project.getID());
			assertEquals(retrievedProject, project);
			dao.delete(project);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}	
	}
	
	/**
	 * Test method for {@link server.database.ProjectsDAO#getProject(int)}.
	 */
	@Test
	public void testGetProjectByTitle()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject", 1, 10, 10);
			dao.add(project);
			Project retrievedProject = dao.getProject(project.getTitle());
			assertEquals(retrievedProject, project);
			dao.delete(project);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}	
	}

	/**
	 * Test method for {@link server.database.ProjectsDAO#add(shared.model.Project, boolean)}.
	 */
	@Test
	public void testAdd()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject1", 1, 10, 10);
			dao.add(project);
			Project retrievedProject = dao.getProject( project.getID());
			if (retrievedProject != null)
			{
				assertEquals(project, retrievedProject);
				dao.delete(retrievedProject);
			}	
			else
			{
				fail("Added batch not found");
			}
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.ProjectsDAO#update(shared.model.Project, boolean)}.
	 */
	@Test
	public void testUpdate()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject", 1, 10, 10);
			dao.add(project);
			project.setFirstYCoordinate(500);
			project.setTitle("TestUpdate");
			dao.update(project);
			Project retrievedProject = dao.getProject(project.getID());
			assertEquals(project, retrievedProject);
			dao.delete(retrievedProject);
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}

	/**
	 * Test method for {@link server.database.ProjectsDAO#delete(shared.model.Project, boolean)}.
	 */
	@Test
	public void testDelete()
	{
		boolean commit = true;
		try
		{
			Project project = new Project(1, "TestProject", 1, 10, 10);
			dao.add(project);
			dao.delete(project);
			Project retrievedProject = dao.getProject(project.getID());
			assertNull(retrievedProject);	
		}
		catch (Exception e)
		{
			commit = false;
		}
		finally
		{
			database.endTransaction(commit);
		}
	}
}
