/** This test class tests the Batch model class, which will be treated as representative of all model classes
 * 
 */
package shared.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author jbelyeu
 *
 */
public class BatchTest
{
	private Batch batch;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		batch = new Batch();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		batch = null;
	}

	/**
	 * Test method for {@link shared.model.Batch#Batch(int, int, java.lang.String)}.
	 */
	@Test
	public void testBatchIntString()
	{
		batch = null;
		batch = new Batch(1, 1, "TestImagePath");
	}

	/**
	 * Test method for {@link shared.model.Batch#getProjectID()}.
	 */
	@Test
	public void testGetProjectID()
	{
		assertEquals(batch.getProjectID(), -1);
	}

	/**
	 * Test method for {@link shared.model.Batch#setProjectID(int)}.
	 */
	@Test
	public void testSetProjectID()
	{
		batch.setProjectID(1);
		assertEquals(batch.getProjectID(), 1);
	}

	/**
	 * Test method for {@link shared.model.Batch#getImagePath()}.
	 */
	@Test
	public void testGetImagePath()
	{
		assertEquals(batch.getImagePath(), "nullpath");
	}

	/**
	 * Test method for {@link shared.model.Batch#setImagePath(java.lang.String)}.
	 */
	@Test
	public void testSetImagePath()
	{
		batch.setImagePath("TestImagePath");
		assertEquals(batch.getImagePath(), "TestImagePath");
	}

	/**
	 * Test method for {@link shared.model.ModelSuper#getID()}.
	 */
	@Test
	public void testGetID()
	{
		assertEquals(batch.getID(), -1);
	}

	/**
	 * Test method for {@link shared.model.ModelSuper#setID(int)}.
	 */
	@Test
	public void testSetID()
	{
		batch.setID(1);
		assertEquals(batch.getID(), 1);
	}

	/**
	 * Test method for {@link shared.model.ModelSuper#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject()
	{
		Batch newBatch = new Batch();
		assertTrue(newBatch.equals(batch));
		
		newBatch = new Batch(1, 1, "TestImagePath");
		assertTrue(! newBatch.equals(batch));
		
		batch = new Batch(1, 1, "TestImagePath");
		assertTrue(newBatch.equals(batch));
	}
}
