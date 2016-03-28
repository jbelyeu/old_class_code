
package client.userInterface;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import client.utilities.BatchState;
import client.utilities.BatchState.Cell;
import client.utilities.ImageInverter;

import java.util.*;
import java.io.*;

@SuppressWarnings("serial")
public class DrawingComponent extends JComponent
{
	public int w_originX;
	public int w_originY;
	public double scale;

	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	private AffineTransform dragTransform; 

	private ArrayList<DrawingShape> shapes;
	private Font font;
	private BasicStroke stroke;
	
	private BatchState batchstate;
	
	public double width;

	public DrawingComponent(BatchState state)
	{	
		this.batchstate = state;		
	}
	
	public void setUp()
	{
		scale = 0.8;
		this.batchstate.setScrollPosition(new Point(0, 0));
		this.batchstate.setZoomLevel(1.0, false);

		initDrag();

		shapes = new ArrayList<DrawingShape>();

		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addComponentListener(componentAdapter);

		Image notspongebob = loadImage();
		shapes.add(new DrawingImage(notspongebob, new Rectangle2D.Double(0, 0,
				notspongebob.getWidth(null), notspongebob.getHeight(null))));
		
		w_originX = notspongebob.getWidth(null) / 2;
		w_originY = notspongebob.getHeight(null) / 2;
		
		width = notspongebob.getWidth(null);
	}

	private void initDrag()
	{
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}

	private void updateTextShapes()
	{
		for (DrawingShape shape : shapes)
		{
			if (shape instanceof DrawingText)
			{
				DrawingText textShape = (DrawingText) shape;
				if (textShape.getText().startsWith("Width:"))
				{
					textShape.setText("Width: " + this.getWidth());
				}
				else if (textShape.getText().startsWith("Height:"))
				{
					textShape.setText("Height: " + this.getHeight());
				}
			}
		}
	}

	private Image loadImage()
	{
		return (this.batchstate.getImage());
	}

	public void setScale(double newScale)
	{		
		this.batchstate.setZoomLevel(newScale, true);
		scale = newScale;
		this.repaint();
	
	}

	public void setOrigin(int w_newOriginX, int w_newOriginY)
	{
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		drawBackground(g2);

		g2.translate(getWidth() / 2.0, getHeight() / 2.0);
		g2.scale(scale, scale);
		g2.translate(-w_originX, -w_originY);

		drawShapes(g2);
		
		Cell cell = this.batchstate.getSelectedCell();		
		
		//must convert world coordinates to device coordinates
		if (cell != null && this.batchstate.isHighlightsOn())
		{
			g2.setColor(new Color(0, 161, 206, 100));
			Point2D w_Pt = new Point2D.Double(cell.getFirstX(), cell.getFirstY());		
			
			g2.fillRect ((int)w_Pt.getX(), (int)w_Pt.getY(), (int)(cell.getWidth()), (int)(cell.getHeight()));
		}
		
	}

	private void drawBackground(Graphics2D g2)
	{
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2)
	{
		for (DrawingShape shape : shapes)
		{
			shape.draw(g2);
		}
	}
	
	public void selectedCellChanged()
	{		
		this.paintComponent(getGraphics());
	}

	private MouseAdapter mouseAdapter = new MouseAdapter()
	{

		@Override
		public void mousePressed(MouseEvent e)
		{
			int d_X = e.getX();
			int d_Y = e.getY();

			dragTransform = new AffineTransform();
			
			dragTransform.translate(getWidth() / 2.0, getHeight() / 2.0);
			dragTransform.scale(scale, scale);
			dragTransform.translate(-w_originX, -w_originY);

			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				dragTransform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex)
			{
				return;
			}
			int w_X = (int) w_Pt.getX();
			int w_Y = (int) w_Pt.getY();
			
			Cell cell = batchstate.getCell(w_X, w_Y);
			batchstate.setSelectedCell(cell);
			selectedCellChanged();
			
			boolean hitShape = false;

			Graphics2D g2 = (Graphics2D) getGraphics();
			for (DrawingShape shape : shapes)
			{
				if (shape.contains(g2, w_X, w_Y))
				{
					hitShape = true;
					break;
				}
			}

			if (hitShape)
			{
				dragging = true;
				w_dragStartX = w_X;
				w_dragStartY = w_Y;
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e)
		{
			if (dragging)
			{
				int d_X = e.getX();
				int d_Y = e.getY();

				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex)
				{
					return;
				}
				int w_X = (int) w_Pt.getX();
				int w_Y = (int) w_Pt.getY();

				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;

				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				batchstate.setScrollPosition(new Point(w_originX, w_originY));
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			return;
		}
	};

	private ComponentAdapter componentAdapter = new ComponentAdapter()
	{

		@Override
		public void componentHidden(ComponentEvent e)
		{
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e)
		{
			return;
		}

		@Override
		public void componentResized(ComponentEvent e)
		{
			updateTextShapes();
		}

		@Override
		public void componentShown(ComponentEvent e)
		{
			return;
		}
	};
	
	public void invertImage()
	{
		ImageInverter.invert(this.batchstate.getImage());
		this.repaint();
	}

	// ///////////////
	// Drawing Shape
	// ///////////////

	interface DrawingShape
	{
		boolean contains(Graphics2D g2, double x, double y);

		void draw(Graphics2D g2);

		Rectangle2D getBounds(Graphics2D g2);
	}

	class DrawingRect implements DrawingShape
	{

		private Rectangle2D rect;
		private Color color;

		public DrawingRect(Rectangle2D rect, Color color)
		{
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y)
		{
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2)
		{
			g2.setColor(color);
			g2.fill(rect);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2)
		{
			return rect.getBounds2D();
		}
	}

	class DrawingLine implements DrawingShape
	{

		private Line2D line;
		private Color color;

		public DrawingLine(Line2D rect, Color color)
		{
			this.line = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y)
		{

			final double TOLERANCE = 5.0;

			Point2D p1 = line.getP1();
			Point2D p2 = line.getP2();
			Point2D p3 = new Point2D.Double(x, y);

			double numerator = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX())
					+ (p3.getY() - p1.getY()) * (p2.getY() - p1.getY());
			double denominator = p2.distance(p1) * p2.distance(p1);
			double u = numerator / denominator;

			if (u >= 0.0 && u <= 1.0)
			{
				Point2D pIntersection = new Point2D.Double(p1.getX() + u * (p2.getX() - p1.getX()),
						p1.getY() + u * (p2.getY() - p1.getY()));

				double distance = pIntersection.distance(p3);

				return (distance <= TOLERANCE);
			}

			return false;
		}

		@Override
		public void draw(Graphics2D g2)
		{
			g2.setColor(color);
			g2.setStroke(stroke);
			g2.draw(line);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2)
		{
			return line.getBounds2D();
		}
	}

	class DrawingImage implements DrawingShape
	{

		private Image image;
		private Rectangle2D rect;

		public DrawingImage(Image image, Rectangle2D rect)
		{
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y)
		{
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2)
		{
			g2.drawImage(image, (int) rect.getMinX(), (int) rect.getMinY(), (int) rect.getMaxX(),
					(int) rect.getMaxY(), 0, 0, image.getWidth(null), image.getHeight(null), null);
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2)
		{
			return rect.getBounds2D();
		}
	}

	class DrawingText implements DrawingShape
	{

		private String text;
		private Color color;
		private Point2D location;

		public DrawingText(String text, Color color, Point2D location)
		{
			this.text = text;
			this.color = color;
			this.location = location;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y)
		{
			Rectangle2D bounds = getBounds(g2);
			return bounds.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2)
		{
			g2.setColor(color);
			g2.setFont(font);
			g2.drawString(text, (int) location.getX(), (int) location.getY());
		}

		@Override
		public Rectangle2D getBounds(Graphics2D g2)
		{
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(text, context);
			bounds.setRect(location.getX(), location.getY() + bounds.getY(), bounds.getWidth(),
					bounds.getHeight());
			return bounds;
		}

		public String getText()
		{
			return text;
		}

		public void setText(String value)
		{
			text = value;
		}
	}
}
