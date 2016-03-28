using System;
using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace VisualIntelligentScissors
{
    public class Vertex
    {
        private bool visited;
        private int distance;
        private Point point;
        private Vertex prevPoint;


        public Vertex()
        { }

        public Vertex(Point p)
        {
            this.visited = false;
            this.point = p;
            this.distance = int.MaxValue; //basically inf.
        }

        public bool Visited
        {
            get { return this.visited; }
            set { this.visited = value; }
        }
        
        public Point Point
        {
            get {return point; }
            set { point = value; }
        }

        public int Distance
        {
            get { return this.distance; }
            set { this.distance = value; }            
        }

        public Vertex PrevPoint
        {
            get { return prevPoint; }
            set { prevPoint = value; }
        }
    }
}
