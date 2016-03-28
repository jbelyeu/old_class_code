using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace _2_convex_hull
{
    class ConvexHullSolver
    {

        public void Solve(Graphics g, List<PointF> pointList)
        {
            // Insert your code here.
            List<PointF> polygonSides = this.quickHull(pointList);
            g.DrawPolygon(new Pen(Color.Red), polygonSides.ToArray());
        }

        /* This function finds the convex hull using the quickhull algorithm through recursive subcalls.
         * Requires a set of points to find the hull of.
         * Returns the list of points on the polygon that describes the convex hull of the starting set of points (organized clockwise).
         */
        private List<PointF> quickHull( List<PointF> pointList )
        {
            //if there are only three points, the convex hull is the triangle they form
            if (pointList.Count <= 3)
            {
                return pointList;
            }
            //find leftmost and rightmost (lowest x-value, highest x-value)
            //use those two points to draw the line of reference (in my head)
            //call function to loop through the points to separate into two sets (above and below the line) 
            //call topHull for the above set, bottomHull for the below set
            //join the sets of returned points
                //each set will be a clockwise set of points. 
                //the first will be A...B
                //the second will be B...Z
                //to join them, pop B off of the first list, concatenate lists

            PointF A = pointList[0];
            PointF B = pointList[1];
            //find A and B
            foreach (PointF point in pointList)
            {
                if (point.X < A.X)
                {
                    A = point;
                    continue;
                }
                if (point.X > B.X)
                {
                    B = point;
                }
            }

            //first list in retLists is top list, second is bottom list
            List<List<PointF>> retLists = this.splitPointList(pointList, A, B);
            List<PointF> topHullList = this.topHull(retLists[0], A, B);
            List<PointF> bottomHullList = this.bottomHull(retLists[1], A, B);
            topHullList.RemoveAt(topHullList.Count -1); //remove last item, B
            bottomHullList.RemoveAt(bottomHullList.Count - 1); //remove last item, A
            topHullList.AddRange(bottomHullList);
            return topHullList;
        }

        private List<PointF> topHull(List<PointF> pointList, PointF A, PointF B)
        {
            List<PointF> orderedList = new List<PointF>();

            //If there are no points in the set of points, return a list of A, B (base case, ensures clockwise)
            //If there is only 1 point in the set of points, return a list of A, C, B (base case, ensures clockwise)
            if (pointList.Count == 0)
            {
                orderedList.Add(A);
                orderedList.Add(B);
                return orderedList;
            }
            if (pointList.Count == 1)
            {
                orderedList.Add(A);
                orderedList.Add(pointList[0]);
                orderedList.Add(B);
                return orderedList;
            }

            //Find point C farthest from A and B (triangle created from those points has the longest legs)
            PointF C = this.findPointC(pointList, A, B);      
            
            //separate into two sets of points above and below the line AC
            //first list in retLists is top list, second is bottom list
            List<List<PointF>> retLists = this.splitPointList(pointList, A, C);

            //call topHull with the points above AC, A, and C. TopList will be filled with the points found, 
            //which will then be stored in ACHullist while the topList is used for the next call
            List<PointF> ACHullList = this.topHull(retLists[0], A, C);

            //separate into two sets of points above and below the line CB
            retLists = this.splitPointList(pointList, C, B);

            //call topHull with the points above CB, C, and B
            List<PointF> CBHullList = this.topHull(retLists[0], C, B);

            //join results
            //results will be a list of points in clockwise order from each call
                //for the first call, first point will be A, followed by a list of points, then C
                //for the second call, first point will be C, followed by a list of points, then B
                //To join sets, pop C off of the first list, then concatenate lists
            ACHullList.RemoveAt(ACHullList.Count - 1);
            ACHullList.AddRange(CBHullList);

            //return them
            return ACHullList;
        }

        private List<PointF> bottomHull(List<PointF> pointList, PointF A, PointF B)
        {
            List<PointF> orderedList = new List<PointF>();

            //If there are no points in the set of points, return a list of A, B (base case, ensures clockwise)
            //If there is only 1 point in the set of points, return a list of A, C, B (base case, ensures clockwise)
            if (pointList.Count == 0)
            {                
                orderedList.Add(B);
                orderedList.Add(A);
                return orderedList;
            }
            if (pointList.Count == 1)
            {             
                orderedList.Add(B);
                orderedList.Add(pointList[0]);
                orderedList.Add(A);
                return orderedList;
            }
            //Find point C farthest from A and B (triangle created from those points has the longest legs)
            PointF C = this.findPointC(pointList, A, B);      

            //separate into two sets of points above and below the line BC
            //first list in retLists is top list, second is bottom list
            List<List<PointF>> retLists = this.splitPointList(pointList, B, C);

            //call bottomHull with the points below BC, B, and C
            List<PointF> BCHullList = this.bottomHull(retLists[1], C, B);

            //separate into two sets of points above and below the line CA
            retLists = this.splitPointList(pointList, C, A );

            //call bottomHUll with the points below CA, C, and A
            List<PointF> CAHullList = this.bottomHull(retLists[1], A, C);

            //join results
                //results will be a list of points in clockwise order from each call
                //for the first call, first point will be B, followed by a list of points, then C
                //for the second call, first point will be C, followed by a list of points, then A
                //To join sets, pop C off of the first list, then concatenate lists
            BCHullList.RemoveAt(BCHullList.Count -1);
            BCHullList.AddRange(CAHullList);
            //return them
            return BCHullList;
        }

        private PointF findPointC(List<PointF> pointList, PointF A, PointF B)
        {
            //To find C: loop through the list and call findDistance for each, 
            //with A and B. Store longest distance and update each time
            //Return C farthest from A and B
            double maxDist = 0;
            PointF C = new PointF();
            foreach (PointF point in pointList)
            {
                double distance = findDistance(A, B, point);
                if (distance > maxDist)
                {
                    C = point;
                    maxDist = distance;
                }
            }
            return C;
        }

        private double findDistance(PointF A, PointF B, PointF C)
        {
            //find length of line AC, length of line CB, add lengths, return them
            double ACLen = this.findLineLen(A, C);
            double CBLen = this.findLineLen(C, B);

            //return len AC plus len CB
            return ACLen + CBLen;
        }

        private double findLineLen(PointF A, PointF C)
        {
            //length of line AC = sqrt((|A[x] - C[x]|)^2 + (|A[y] - C[y]|)^2)
            double ACLen = Math.Sqrt(Math.Pow(Math.Abs(A.X - C.X), 2) + (Math.Pow(Math.Abs(A.Y - C.Y), 2)));
            return ACLen;
        }

        private List<List<PointF>> splitPointList(List<PointF> pointList, PointF A, PointF B)
        {
            List<List<PointF>> retLists = new List<List<PointF>>();
            
            retLists.Add(new List<PointF>());
            retLists.Add(new List<PointF>());

            //find which list to go into by finding the slope m of AB ((m = A[y] - B[y]) / (A[x] - B[x]))
            double m = (A.Y - B.Y) / (A.X - B.X);
            //find offset (b in y = mx + b)
            double offset = A.Y - m * A.X;
            
            //loop through the starting list and find whether each point C is above or below the line AB
            foreach (PointF point in pointList)
            {
                //if A or B, don't include them
                if (point.Equals(A) || point.Equals(B))
                {
                    continue;
                }

                //if C[y] < m(C[x] + b) then C belongs to the top set
                if (point.Y < (m * point.X ) + offset )
                {
                    retLists[0].Add(point);
                }
                //or if C[y] > m(C[x] + b) then C belongs to the bottom set
                else if (point.Y > (m * point.X) + offset)
                {
                    retLists[1].Add(point);
                }
                //else C lies on the line and may be safely dropped
            }
            return retLists; 
        }
    }
}