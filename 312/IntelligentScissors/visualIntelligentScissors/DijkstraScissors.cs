using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Drawing;
using System.Collections; 

namespace VisualIntelligentScissors
{
    //source: http://www.codeproject.com/Articles/126751/Priority-queue-in-C-with-the-help-of-heap-data-str
    class VertexComparer : IComparer<int>
    {
        public int Compare(Vertex a, Vertex b)
        {
            DijkstraScissors compareScissors = new DijkstraScissors();
            int aWeight = compareScissors.getWeight(a.Point);
            int bWeight = compareScissors.getWeight(b.Point);

            return aWeight - bWeight;
        }
        public int Compare(int a, int b)
        {
            return a - b;
        }
    }


    public class DijkstraScissors : Scissors
	{
        private Pen yellowpen = new Pen(Color.Yellow);
        private MinPQ<int, Vertex> queue;
        private List<Vertex> endPoints = new List<Vertex>();
        private Vertex[,] allPoints;

		public DijkstraScissors() {}
        /// <summary>
        /// constructor for intelligent scissors. 
        /// </summary>
        /// <param name="image">the image you are going to segment.  Has methods for getting gradient information</param>
        /// <param name="overlay">an overlay on which you can draw stuff by setting pixels.</param>
		public DijkstraScissors(GrayBitmap image, Bitmap overlay) : base(image, overlay) 
        {}

        /// <summary>
        /// this is the class you implement in CS 312. 
        /// </summary>
        /// <param name="points">these are the segmentation points from the pgm file.</param>
        /// <param name="pen">this is a pen you can use to draw on the overlay</param>
		public override void FindSegmentation(IList<Point> points, Pen pen)
		{
            VertexComparer comparer = new VertexComparer();

			if (Image == null) throw new InvalidOperationException("Set Image property first.");
            // this is the entry point for this class when the button is clicked
            // to do the image segmentation with intelligent scissors.
            Program.MainForm.RefreshImage();

            //loop through the points in the points list and call dijkstra starting with 0, 1
            for (int i = 0; i < points.Count ; ++i)
            {
                //this will automatically set distance to inf, visited to false for all verteces
                this.resetAllPoints();

                this.queue = new MinPQ<int, Vertex>(comparer);
                int xStartVal = points[i].X;
                int yStartVal = points[i].Y;
                int xGoalVal = points[(i + 1) % points.Count].X;
                int yGoalVal = points[(i + 1) % points.Count].Y;

                Vertex start = this.allPoints[xStartVal, yStartVal];
                Vertex goal = this.allPoints[xGoalVal, yGoalVal];

                //distance = 0, visited true
                start.Distance = 0;
                start.Visited = true;

                //put the first point from the points list in the queue as a vertex 
                this.queue.enqueue(0, start);
                this.Dijkstra(goal);
                this.draw(endPoints[endPoints.Count -1]);
            }
        }

        private void Dijkstra(Vertex goal)
        {

            bool foundGoal = false;
            Vertex next = goal;

            //loop while still stuff in queue. Start alredy there
            while ( (! this.queue.isEmpty) && !foundGoal)
            {
                //assign the minvalue of the queue to the next vertex, removing it from the queue
                KeyValuePair<int, Vertex> nextPair = queue.seeNext();
                int nextKey = nextPair.Key;
                next = nextPair.Value;
                this.queue.dequeue(nextKey, next);

                //starting with this next point, generate four child vertices
                List<Vertex> nextChildren = new List<Vertex>();
                nextChildren.Add(this.getNewPoint(next.Point.X, next.Point.Y -1)); //north
                nextChildren.Add(this.getNewPoint(next.Point.X + 1, next.Point.Y)); //east
                nextChildren.Add(this.getNewPoint(next.Point.X, next.Point.Y + 1)); //south
                nextChildren.Add(this.getNewPoint(next.Point.X - 1, next.Point.Y)); //west            

                foreach (Vertex u in nextChildren)
                {
                    if (u.Point == null) { continue; }


                    int uWeight = this.GetPixelWeight(u.Point);
                    //check them to see if one is the goal. If so, foundGoal is set to true (which breaks the loop)
                    if ( u.Point.X == goal.Point.X && u.Point.Y == goal.Point.Y)
                    {
                        foundGoal = true;
                        u.PrevPoint = next;
                        this.endPoints.Add(u);
                        break;
                    }                   
                    else if (! u.Visited)
                    {
                        //else if not in either settled or queue

                        //set distance to next.distance + weight of u
                        u.Distance = next.Distance + uWeight;

                        //visited to true
                        u.Visited = true;

                        //set prev pointer to next
                        u.PrevPoint = next;

                        //and enqueue it
                        queue.enqueue(uWeight, u);
                    }
                }
            }
        }

        public int getWeight(Point p)
        {
            return this.GetPixelWeight(p);
        }

        private void draw(Vertex endgoal)
        {

            using (Graphics g = Graphics.FromImage(Overlay))
            {

                while (endgoal.PrevPoint != null)
                {
                    Overlay.SetPixel(endgoal.Point.X, endgoal.Point.Y, Color.Red);
                    endgoal = endgoal.PrevPoint;
                }

                Program.MainForm.RefreshImage();
            }
        }

        private Vertex nextChild(List<Vertex> verteces, int x, int y)
        {
            if (x < 1 || y < 1 || x > Overlay.Width -2 || y > Overlay.Height -2)
            {
                return new Vertex();
            }
            return new Vertex(new Point(x, y));

        }

        private void resetAllPoints()
        {
            //resets the 2D array of verteces 
            this.allPoints = new Vertex[Overlay.Width - 2, Overlay.Height - 2];

            for (int i = 0; i < Overlay.Width - 2; ++i)
            {
                for (int j = 0; j < Overlay.Height -2 ; ++j)
                {
                    Vertex v = new Vertex(new Point(i, j));
                    allPoints[i, j] = v;
                }
            }
        }

        private Vertex getNewPoint(int x, int y)
        {
            //makes sure points generated are valid
            if (x > this.Overlay.Width - 2 || x < 1 || 
                y > this.Overlay.Height - 2 || y < 1)
            {
                return new Vertex();
            }
            else
            {
                return this.allPoints[x, y];
            }
        }
	}
}
