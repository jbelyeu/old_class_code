using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Drawing;


namespace VisualIntelligentScissors
{
    public class SimpleScissors : Scissors
    {
        public SimpleScissors() { }

        private Pen yellowPen = new Pen(Color.Yellow);
        private List<Point> settledPoints = new List<Point>();
        private HashSet<Point> visitedPoints = new HashSet<Point>();


        /// <summary>
        /// constructor for SimpleScissors. 
        /// </summary>
        /// <param name="image">the image you are going to segment including methods for getting gradients.</param>
        /// <param name="overlay">a bitmap on which you can draw stuff.</param>
        public SimpleScissors(GrayBitmap image, Bitmap overlay) : base(image, overlay) { }

        // this is a class you need to implement in CS 312. 

        /// <summary>
        ///  this is the class to implement for CS 312. 
        /// </summary>
        /// <param name="points">the list of segmentation points parsed from the pgm file</param>
        /// <param name="pen">a pen for writing on the overlay if you want to use it.</param>
        public override void FindSegmentation(IList<Point> points, Pen pen)
        {
            // this is the entry point for this class when the button is clicked for 
            // segmenting the image using the simple greedy algorithm. 
            // the points

            if (Image == null) throw new InvalidOperationException("Set Image property first.");

            for (int i = 0; i < points.Count ; ++i)
            {
                this.settledPoints.Add(points[i]);
                this.visitedPoints.Add(points[i]);
                stepIntoImage(points[i], points[(i + 1) % points.Count]);
            }

            this.draw(points);            
        }

        private void stepIntoImage(Point entryPoint, Point exitPoint)
        {
            //need to loop through the points in the bitmap starting from the first point in points
            //from that entry point, find the weights of the neighboring points (left, right, up, down)
            //use provided getPixelWeight function
            //determine lowest weight of the four points. Resolve ties clockwise
            //once lowest is determined, recurse with that point as entry. 
            //Maintain same exit. When entry == exit, return

            if (entryPoint == exitPoint) { return;  }
            this.settledPoints.Add(entryPoint);
            //this.draw();

            Vertex northPoint = this.nextChild(entryPoint.X, entryPoint.Y - 1),
               eastPoint = this.nextChild(entryPoint.X + 1, entryPoint.Y),
               southPoint = this.nextChild(entryPoint.X, entryPoint.Y + 1),
               westPoint = this.nextChild(entryPoint.X - 1, entryPoint.Y);


            Dictionary<int, Point> sorter = new Dictionary<int, Point>();

            //sorts the points by highest pixel weight
            //if a pixel weight is repeated, the key is overwritten by the higher priority pixel
            //if a pixel has already been settled, it is not added
            if (westPoint != null && !this.visitedPoints.Contains(westPoint.Point)) 
            {
                sorter[this.GetPixelWeight(westPoint.Point)] = westPoint.Point;
                this.visitedPoints.Add(westPoint.Point);
            }
            if (southPoint != null && !this.visitedPoints.Contains(southPoint.Point)) 
            {
                sorter[this.GetPixelWeight(southPoint.Point)] = southPoint.Point;
                this.visitedPoints.Add(southPoint.Point);
            }
            if (eastPoint != null && !this.visitedPoints.Contains(eastPoint.Point))
            {
                sorter[this.GetPixelWeight(eastPoint.Point)] = eastPoint.Point;
                this.visitedPoints.Add(eastPoint.Point);
            }
            if (northPoint != null && !this.visitedPoints.Contains(northPoint.Point)) 
            {
                sorter[this.GetPixelWeight(northPoint.Point)] = northPoint.Point;
                this.visitedPoints.Add(northPoint.Point);
            }

            //find smallest key in the dictionary
            int minKey = int.MaxValue;
            foreach (int key in sorter.Keys)
            {
                if (key < minKey)
                {
                    minKey = key;
                }
            }

            //no neighbors left unvisited
            if (minKey == int.MaxValue)
            {
                return;
            }
            //otherwise recurse
            this.stepIntoImage(sorter[minKey], exitPoint);         
        }

        private void draw(IList<Point> points) 
        {
            //from straight scissors, used to draw the outline
            using (Graphics g = Graphics.FromImage(Overlay))
            {
                for (int i = 0; i < settledPoints.Count; i++)
                {
                    Point start = settledPoints[i];
                    Overlay.SetPixel(start.X, start.Y, Color.Red);
                }

                //foreach (Point p in points)
                //{
                //    g.DrawEllipse(yellowPen, p.X, p.Y, 5, 5);

                //}
                Program.MainForm.RefreshImage();
            }
        }
        
        private void draw()
        {
            //from straight scissors, used to draw the outline
            using (Graphics g = Graphics.FromImage(Overlay))
            {
                for (int i = 0; i < settledPoints.Count; i++)
                {
                    Point start = settledPoints[i];
                    Overlay.SetPixel(start.X, start.Y, Color.Red);
                }
                Program.MainForm.RefreshImage();
            }
        }

        private Vertex nextChild(int x, int y)
        {    
            if (x < 1 || y < 1 || x > Overlay.Width - 2 || y > Overlay.Height - 2)
            {
                return null;
            }

            return new Vertex(new Point(x, y));
        }
    }   
}
