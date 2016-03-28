/**
 * 
 */

package client.utilities;

import shared.model.Field;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import shared.communication.GetBatchResult;
import client.facade.ClientFacade;
import client.userInterface.IndexerWindow;

/**
 * @author jon
 *
 */
public class BatchState
{
	//fields to keep track of just for saving batch state
	private double zoomLevel;
	private Point scrollPosition;
	private boolean highlightsOn;
	private boolean imageInverted;
	private Point windowPosition;
	private Dimension windowSize;
	private int verticalDividerLocation;
	private int horizontalDividerLocation;
	
	private String host;
	private String port;
	private String username;
	private String password;
	private IndexerWindow indexerWindow;
	private GetBatchResult batchInfo;
	private BufferedImage image;

	private ArrayList<CustomStateListener> listeners;
	private String[][] values;
	private Cell selectedCell;
	private boolean batchCheckedOut;

	public BatchState(String host, String port)
	{
		this.highlightsOn = true;
		this.host = host;
		this.port = port;
		this.zoomLevel = 1;
		this.batchCheckedOut = false;
		listeners = new ArrayList<CustomStateListener>();
	}

	public void submit()
	{
		for (CustomStateListener l : listeners)
		{
			l.batchSubmitted();
		}
	}

	public void save()
	{
		//System.outut.println("BatchState Save()");
		
		if (! this.batchCheckedOut)
		{
			return;
		}
		
		File file = new File(this.username);
		try
		{
			PrintWriter writer = new PrintWriter(file);
			
			//structure of the file will be markup
			writer.println("<batchInfo>" );
			writer.println(this.batchInfo.markup()); //works just like toString(), but adds tags to show beginning and end of fields
			writer.println("</batchInfo>");
			writer.println("<fieldVals>");
			for (String[] columns : this.values)
			{
				writer.println("<columnvals>");
				for (String cellVal : columns)
				{
					writer.println(cellVal); //using println() should keep spacing correct
				}
				writer.println("</columnvals>");
			}
			writer.println("</fieldVals>");
			
			writer.println("<zoomLevel>");
			writer.println(this.zoomLevel);
			writer.println("</zoomLevel>");
			
			writer.println("<scrollPositionx>");
			writer.println(this.scrollPosition.x);
			writer.println("</scrollPositionx>");
			
			writer.println("<scrollPositiony>");
			writer.println(this.scrollPosition.y);
			writer.println("</scrollPositiony>");
			
			writer.println("<highlightsVisible>");
			writer.println(this.highlightsOn);
			writer.println("</highlightsVisible>");
			
			writer.println("<imageInverted>");
			writer.println(this.imageInverted);
			writer.println("</imageInverted>");
			
			writer.println("<windowPosition>");
			writer.println(this.windowPosition.x + "\n" + this.windowPosition.y);
			writer.println("</windowPosition>");
			
			writer.println("<windowSize>");
			writer.println(this.windowSize.getWidth() + "\n" + this.windowSize.getHeight());
			writer.println("</windowSize>");
			
			writer.println("<vertDivPosition>");
			writer.println(this.verticalDividerLocation);
			writer.println("</vertDivPosition>");
			
			writer.println("<horizDivPosition>");
			writer.println(this.horizontalDividerLocation);
			writer.println("</horizDivPosition>");			
			
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens a file of user data and reads it into memory, If file exists, returns true, else, returns false
	 */
	public boolean loadOldBatch()
	{
		GetBatchResult info = new GetBatchResult();
		
		File file = new File(this.username);
		if (! file.exists())
		{
			return false;
		}
		
		try
		{
			Scanner scanner = new Scanner(file);
			
			scanner.nextLine(); //throw away the first tag (<batchInfo>), mostly for debugging
			info.setBatchID( Integer.parseInt( scanner.nextLine() ) ); 
			info.setProjectID( Integer.parseInt( scanner.nextLine() ) );
			info.setImageURL(scanner.nextLine()); //
			info.setFirstYCoord( Integer.parseInt( scanner.nextLine() ) );
			info.setRecordHeight( Integer.parseInt( scanner.nextLine() ) );
			info.setNumRecords( Integer.parseInt( scanner.nextLine() ) );
			info.setNumFields( Integer.parseInt( scanner.nextLine() ) );
			
			String tag = scanner.nextLine(); //transition to field data, throw away <fields> tag
			//System.outut.println(tag);
			
			addFieldsFromFile(scanner, info);
			
			String url = this.getBatchInfo().getImageURL();
			ClientFacade facade = new ClientFacade();
			BufferedImage image = facade.getImage(url, this.getHost(), this.getPort());
			this.setImage(image);
			
//			tag = scanner.nextLine(); //throw away </fields> tag
//			//System.outut.println(tag);
			tag = scanner.nextLine(); //throw away </batchInfo> tag
			//System.outut.println(tag);
			tag = scanner.nextLine(); //throw away <fieldVals> tag
			//System.outut.println(tag);
			
			this.setValuesFromFile(scanner);
			this.setpositioningDataFromFile(scanner);
			
			scanner.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return true;
	}
	

	private void addFieldsFromFile(Scanner scanner, GetBatchResult info)
	{
		ArrayList<Field> fields = new ArrayList<Field>(); 
		while ( scanner.hasNextLine() )
		{
			String nextVal = scanner.nextLine();
			if (nextVal.equals("</fields>") )
			{
				break;
			}
			
			Field field = new Field();
			field.setID( Integer.parseInt( nextVal ) );
			field.setFieldNumber( Integer.parseInt( scanner.nextLine() ) );
			field.setFieldTitle( scanner.nextLine() );
			field.setFieldHelpFileName(scanner.nextLine() );
			field.setXCoordinate( Integer.parseInt( scanner.nextLine() ) );
			field.setWidth( Integer.parseInt( scanner.nextLine() ) );
			field.setKnownDataFileName(scanner.nextLine());
						
			fields.add(field);
		}
		info.setFields(fields);
		this.setBatchInfo(info);
	}
	
	@SuppressWarnings("unused")
	private void setValuesFromFile(Scanner scanner)
	{
		for (int i = 0; i < this.values.length; ++i)
		{
			String tag = scanner.nextLine(); //throw away <columnvals> tag
			//System.outut.println(tag);
			for (int j = 0; j < this.values[i].length; ++j)
			{
				this.values[i][j] = scanner.nextLine();
				if (this.values[i][j].equals("null"))
				{
					this.values[i][j] = null;
				}

				//System.outut.println(this.values[i][j]);
			}
			tag = scanner.nextLine(); //throw away </columnvals> tag
			
		}
		
		for (CustomStateListener l : listeners)
		{
			l.cellValueChanged();
		}
	}
	
	
	private void setpositioningDataFromFile(Scanner scanner)
	{
		scanner.nextLine(); //throw away </fieldVals> tag
		scanner.nextLine(); //throw away <zoomLevel> tag
		this.zoomLevel = Double.parseDouble( scanner.nextLine() );
		scanner.nextLine(); //throw away </zoomLevel> tag
		scanner.nextLine(); //throw away <scrollPositionx> tag
		this.scrollPosition.x = Integer.parseInt( scanner.nextLine() );
		scanner.nextLine(); //throw away </scrollPositionx> tag
		scanner.nextLine(); //throw away <scrollPositiony> tag
		this.scrollPosition.y = Integer.parseInt( scanner.nextLine() );
		scanner.nextLine(); //throw away </scrollPositiony> tag
		
		scanner.nextLine(); //throw away <highlightsVisible> tag
		
		if (scanner.nextLine().equals("true"))
		{
			this.highlightsOn = true;
		}
		else
		{
			this.highlightsOn = false;
		}
		scanner.nextLine(); //throw away </highlightsVisible> tag
		scanner.nextLine(); //throw away <imageInverted> tag
		if (scanner.nextLine().equals("true"))
		{
			this.imageInverted = true;
		}
		else
		{
			this.imageInverted = false;
		}
		
		scanner.nextLine(); //throw away </imageInverted> tag
		scanner.nextLine(); //throw away <windowPosition> tag
		
		Point point = new Point();
		double xval = Double.parseDouble( scanner.nextLine() );
		double yval = Double.parseDouble( scanner.nextLine() );
		point.setLocation(xval, yval);
		this.setWindowPosition(point);

		scanner.nextLine(); //throw away </windowPosition> tag
		scanner.nextLine(); //throw away <windowSize> tag
		
		//parsing as doubles because of trailing decimals
		int width = (int)Double.parseDouble( scanner.nextLine() ); 
		int height = (int)Double.parseDouble( scanner.nextLine() );
		this.setWindowSize(new Dimension(width, height));
		
		scanner.nextLine(); //throw away </windowSize> tag
		scanner.nextLine(); //throw away <vertDivPosition> tag
		
		this.setVerticalDividerLocation(Integer.parseInt(scanner.nextLine() ) );
		
		scanner.nextLine(); //throw away </vertDivPosition> tag
		scanner.nextLine(); //throw away <horizDivPosition> tag
		
		this.setHorizontalDividerLocation(Integer.parseInt(scanner.nextLine() ) );	
		
		for (CustomStateListener l : listeners)
		{
			l.loadedPositioningData();
		}
	}
	
	/**
	 * gets cell selected by user in the click event, from x/ y coords passed in
	 * @return
	 */
	public Cell getCell(double clickX, double clickY)
	{
		this.selectedCell = new Cell();
		this.selectedCell.setHeight(this.batchInfo.getRecordHeight());
		
		int firstY = this.batchInfo.getFirstYCoord();
		int lastY = firstY + selectedCell.height * this.batchInfo.getNumRecords();
		
		if (clickY >= firstY && clickY <= lastY )
		{
			this.selectedCell.setRecord( (int) ( (clickY - firstY) / selectedCell.height) + 1);
			this.selectedCell.setFirstY( firstY + (selectedCell.getRecord() -1) * this.selectedCell.getHeight());
		}
		else
		{
			return null;
		}
		
	
		int leftSide = this.batchInfo.getFields().get(0).getXCoordinate();
		int width = 0;
		
		for (Field field : this.batchInfo.getFields())
		{
			width += field.getWidth();
		}
		
		int rightSide = leftSide + width;
		
		if (clickX < leftSide || clickX > rightSide)
		{
			return null;
		}
			
		int fieldend = leftSide; //keeps track of right side of cell
		int fieldStart = leftSide;  //keeps track of left side of cell
		
		for (int i = 0; i < this.batchInfo.getNumFields(); ++i)
		{
			fieldend += this.batchInfo.getFields().get(i).getWidth();
			
			if (clickX < fieldend)
			{
				this.selectedCell.setField(i + 1);
				this.selectedCell.setWidth(this.batchInfo.getFields().get(i).getWidth());
				this.selectedCell.setFirstX(fieldStart);
				break;
			}
			fieldStart += this.batchInfo.getFields().get(i).getWidth();
		}			
		return this.selectedCell;
	}
	
	/**
	 * @return the host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @param host
	 *  the host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * @return the indexerWindow
	 */
	public IndexerWindow getIndexerWindow()
	{
		return indexerWindow;
	}

	/**
	 * @param indexerWindow
	 *            the indexerWindow to set
	 */
	public void setIndexerWindow(IndexerWindow indexerWindow)
	{
		this.indexerWindow = indexerWindow;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @return the zoomLevel
	 */
	public double getZoomLevel()
	{
		return zoomLevel;
	}

	/**
	 * @param zoomLevel
	 *            the zoomLevel to set
	 */
	public void setZoomLevel(double zoomLevel, boolean listen)
	{
		this.zoomLevel = zoomLevel;

		if (listen)
		{
			for (CustomStateListener l : listeners)
			{
				l.scaled();
			}
		}
		
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the result
	 */
	public GetBatchResult getBatchInfo()
	{
		return batchInfo;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setBatchInfo(GetBatchResult result)
	{
		this.batchInfo = result;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage()
	{
		return image;
	}
	
	public String getImageTitle()
	{
		String[] fields = this.batchInfo.getImageURL().split("/");
		return fields[fields.length-1];
	}

	/**
	 * This function is the flag to say that a batch has been downloaded
	 * 
	 * @param image
	 *            the image to set
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;

		values = new String[this.batchInfo.getNumRecords()][this.batchInfo.getNumFields()];
		this.selectedCell = null;
		
		this.batchCheckedOut = true;
		for (CustomStateListener l : listeners)
		{
			l.batchDownloaded();
		}
	}

	public String getValueAt(int row, int column)
	{
		return this.values[row][column];
	}

	public void setValueAt(String value, int row, int column)
	{
		this.values[row][column] = value;
	}

	public void addListener(CustomStateListener l)
	{
		listeners.add(l);
	}

	public class Cell
	{
		private int record;
		private int field;
		private int width;
		private int height;
		private int firstX;
		private int firstY;
		
		public Cell()
		{}
		
		public Cell(int field, int record)
		{
			this.field = field;
			this.record = record;
			
			this.firstX = batchInfo.getFields().get(field-1).getXCoordinate();
			this.width = batchInfo.getFields().get(field-1).getWidth();
			this.height = batchInfo.getRecordHeight();
			
			this.firstY = batchInfo.getFirstYCoord() + (record -1) * this.height;
		}

		/**
		 * @return the record
		 */
		public int getRecord()
		{
			return record;
		}

		/**
		 * @param record
		 *            the record to set
		 */
		public void setRecord(int record)
		{
			this.record = record;
		}

		/**
		 * @return the field
		 */
		public int getField()
		{
			return field;
		}

		/**
		 * @param field
		 *            the field to set
		 */
		public void setField(int field)
		{
			this.field = field;
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
		 * @return the height
		 */
		public int getHeight()
		{
			return height;
		}

		/**
		 * @param height the height to set
		 */
		public void setHeight(int height)
		{
			this.height = height;
		}

		/**
		 * @return the firstx
		 */
		public int getFirstX()
		{
			return firstX;
		}

		/**
		 * @param firstx the firstx to set
		 */
		public void setFirstX(int firstx)
		{
			this.firstX = firstx;
		}

		/**
		 * @return the firstY
		 */
		public int getFirstY()
		{
			return firstY;
		}

		/**
		 * @param firstY the firstY to set
		 */
		public void setFirstY(int firstY)
		{
			this.firstY = firstY;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			return "Cell [record=" + record + ", field=" + field + ", width=" + width + ", height="
					+ height + ", firstX=" + firstX + ", firstY=" + firstY + "]";
		}
	}

	/**
	 * @return the scrollPosition
	 */
	public Point getScrollPosition()
	{
		return scrollPosition;
	}

	/**
	 * @param scrollPosition the scrollPosition to set
	 */
	public void setScrollPosition(Point scrollPosition)
	{
		this.scrollPosition = scrollPosition;
	}

	/**
	 * @return the highlightsOn
	 */
	public boolean isHighlightsOn()
	{
		return highlightsOn;
	}

	/**
	 * @param highlightsOn the highlightsOn to set
	 */
	public void setHighlightsOn()
	{
		if (this.highlightsOn)
		{
			this.highlightsOn = false;
		}
		else
		{
			this.highlightsOn = true;
		}
		
		for (CustomStateListener l : listeners)
		{
			l.highlightsToggled();
		}
	}

	/**
	 * @return the imageInverted
	 */
	public boolean isImageInverted()
	{
		return imageInverted;
	}

	/**
	 * @param imageInverted the imageInverted to set
	 */
	public void setImageInverted()
	{
		if (this.imageInverted)
		{
			this.imageInverted = false;
		}
		else
		{
			this.imageInverted = true;
		}
		
		for (CustomStateListener l : listeners)
		{
			l.invertImage();
		}
	}

	/**
	 * @return the listeners
	 */
	public ArrayList<CustomStateListener> getListeners()
	{
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(ArrayList<CustomStateListener> listeners)
	{
		this.listeners = listeners;
	}

	/**
	 * @return the selectedCell
	 */
	public Cell getSelectedCell()
	{
		return selectedCell;
	}

	/**
	 * @param selectedCell the selectedCell to set
	 */
	public void setSelectedCell(Cell selectedCell)
	{
		this.selectedCell = selectedCell;
		
		for (CustomStateListener l : listeners)
		{
			l.cellSelectedChanged();
		}
	}
	
	public void setSelectedCell(int field, int row)
	{
		if (this.selectedCell == null)
		{
			this.setSelectedCell(new Cell(field, row));
		}
		else if (this.selectedCell.field != field || this.selectedCell.record != row)
		{
			this.setSelectedCell(new Cell(field, row));
		}
	}

	/**
	 * @return the windowPosition
	 */
	public Point getWindowPosition()
	{
		return windowPosition;
	}

	/**
	 * @param windowPosition the windowPosition to set
	 */
	public void setWindowPosition(Point windowPosition)
	{
		this.windowPosition = windowPosition;
	}

	/**
	 * @return the windowSize
	 */
	public Dimension getWindowSize()
	{
		return windowSize;
	}

	/**
	 * @param windowSize the windowSize to set
	 */
	public void setWindowSize(Dimension windowSize)
	{
		this.windowSize = windowSize;
	}

	/**
	 * @return the verticalDividerLocation
	 */
	public int getVerticalDividerLocation()
	{
		return verticalDividerLocation;
	}

	/**
	 * @param verticalDividerLocation the verticalDividerLocation to set
	 */
	public void setVerticalDividerLocation(int verticalDividerLocation)
	{
		this.verticalDividerLocation = verticalDividerLocation;
	}

	/**
	 * @return the horizontalDividerLocation
	 */
	public int getHorizontalDividerLocation()
	{
		return horizontalDividerLocation;
	}

	/**
	 * @param horizontalDividerLocation the horizontalDividerLocation to set
	 */
	public void setHorizontalDividerLocation(int horizontalDividerLocation)
	{
		this.horizontalDividerLocation = horizontalDividerLocation;
	}
}
