/**
 * 
 */
package server.dataLoader;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import server.database.Database;

/**
 * @author jbelyeu
 *
 */
public class DataImporter 
{
	@SuppressWarnings("unused")
	private IndexerData indexerData;
	
	public static final String DATAFOLDER = "serverData/";
	public static final String IMAGEFOLDER = DATAFOLDER + "images/";
	public static final String KNOWNDATAFOLDER = DATAFOLDER + "knownData/";
	public static final String FIELDHELPFOLDER = DATAFOLDER + "fieldHelp/";
	public static String givenPath;
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		DataImporter importer = new DataImporter(args[0]);
	}
	
	public DataImporter(String xmlFileName) 
	{
		File xmlFile = new File(xmlFileName);
		
		int nameLength = xmlFile.getName().length();
		givenPath = xmlFileName.substring(0, xmlFileName.length() - nameLength );
		
		new File(IMAGEFOLDER).mkdirs();
		new File(KNOWNDATAFOLDER).mkdir();
		new File(FIELDHELPFOLDER).mkdir();
		importData(xmlFile);	
	}
	
	public static ArrayList<Element> getChildElements(Node node)
	{
		ArrayList<Element> result = new ArrayList<Element>();
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) 
		{
			Node child = children.item(i);
			
			if (child.getNodeType() == Node.ELEMENT_NODE )
			{
				result.add((Element)child);
			}
		}
		return result;			
	}
	
	public static String getValue (Element element)
	{
		String result = "";
		if (element != null)
		{
			Node child = element.getFirstChild();
			result = child.getNodeValue();

		}
		return result;
	}
	
	private void importData(File xmlFile)
	{
		Database.initialize();
		Database.cleanDatabase(); //wipes all data from database
		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
			Document doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();
			
			Element root = doc.getDocumentElement();
			indexerData = new IndexerData(root);
		} 
		catch (ParserConfigurationException e)
		{
			System.out.println("Data Import Error: Parser configuration");
			e.printStackTrace();
		}
		catch (SAXException | IOException e) 
		{
			System.out.println("Data Import Error");
			e.printStackTrace();
		}
	}
	
	public static String storeFiles(File file, String dirPath)
	{
		File newFile = new File( dirPath + file.getName() );
		try
		{
			InputStream in = new FileInputStream(file);
			OutputStream out = new FileOutputStream(newFile);
			
			byte[] buffer = new byte[1024];
			int length;
			while ( ( length = in.read(buffer)) > 0 )
			{
				out.write(buffer,  0,  length);
			}
			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return dirPath + file.getName();
	}

}