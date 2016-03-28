using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace TSP
{
    class State
    {
        public State()
        {
            lowerBound = double.PositiveInfinity;
            routeSoFar = new TSPSolution();
            visitedCityIndices = new HashSet<int>();
            orderedVisitedCityIndices = new List<int>();
        }


        private Double[,] reducedMatrix;
        private Double lowerBound;
        private TSPSolution routeSoFar;
        private HashSet<int> visitedCityIndices;
        private double costSoFar;
        private List<int> orderedVisitedCityIndices;

        public List<int> OrderedVisitedCityIndices
        {
            get { return orderedVisitedCityIndices; }
            set { orderedVisitedCityIndices = value; }
        }

        public Double[,] ReducedMatrix
        {
            get { return reducedMatrix; }
            set { reducedMatrix = value; }
        }

        public Double LowerBound
        {
            get { return lowerBound; }
            set { lowerBound = value; }
        }

        public TSPSolution RouteSoFar
        {
            get { return routeSoFar; }
            set { routeSoFar = value; }
        }

        public HashSet<int> VisitedCityIndices
        {
            get { return visitedCityIndices; }
            set { visitedCityIndices = value; }
        }
        public double CostSoFar
        {
            get { return costSoFar; }
            set { costSoFar = value; }
        }

        /// <summary>
        /// This function will find a reduced cost matrix for the state 
        /// with bound of cost of route. Does not handle case of substate, only init
        /// </summary>
        /// <param name="state"></param>
        /// <returns></returns>
        public void buildMatrix(City[] Cities)
        {
            double[,] reducedMatrix = new double[Cities.Length, Cities.Length]; //init matrix
            double bound = 0;

            for (int i = 0; i < Cities.Length; ++i)
            {
                
                for (int j = 0; j < Cities.Length; ++j)
                {
                    if (i == j)
                    {
                        reducedMatrix[i, j] = Double.PositiveInfinity;
                        continue; //it's the same city, infinite distance to itself
                    }
                    reducedMatrix[i, j] = Cities[i].costToGetTo(Cities[j]);
                }
            }

            for (int i = 0; i < Cities.Length; ++i)
            {
                double minDist = Double.PositiveInfinity;

                for (int j = 0; j < Cities.Length; ++j)
                {              
                    //loop through the Cities array ^2 times and find the distance between the higher level city (i)
                    //and the lower level city (j). Put the distance into the reduced matrix in the array at i.
                    if (i == j)
                    {
                        reducedMatrix[i, j] = Double.PositiveInfinity;
                        continue; //it's the same city, infinite distance to itself
                    }
                    reducedMatrix[i, j] = Cities[i].costToGetTo(Cities[j]); //put the distance i-j in the matrix
                    if (reducedMatrix[i, j] < minDist) //if this is the smallest distance from i so far
                    {
                        minDist = reducedMatrix[i, j]; //remember the smallest distance so far
                    }
                }

                //once we have the min distance from city i, if it's not 0, reloop through the matrix and reduce 
                //all distances by the minDist, then add to bound
                if (minDist > 0 && minDist < Double.PositiveInfinity)
                {
                    //For each j, remember the cost and loop back through after finishing the initial n loops of j
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (this.VisitedCityIndices.Contains(j) || this.VisitedCityIndices.Contains(i))
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[i, j] -= minDist;
                    }
                    bound += minDist;
                }
            }
            //to subtract the smallest cost from i to j from all i to j values. Store all of these min values
            //in a bound variable.

            //TODO: There may be a faster way to do this, but for now, once that is done (all rows are reduced), 
            //loop back through the matrix and find all columns without a 0 value (find min of column and if not 0,
            //it needs to be reduced further). Loop back through if necessary and subtract min from all distances,
            //add that min to the bound.
            for (int i = 0; i < Cities.Length; ++i)
            {
                if (this.VisitedCityIndices.Contains(i))
                {
                    continue; //this city has been settled
                }
                double minDist = Double.PositiveInfinity;
                for (int j = 0; j < Cities.Length; ++j)
                {
                    if (VisitedCityIndices.Contains(j))
                    {
                        continue; //this city has been settled
                    }
                    if (reducedMatrix[j, i] < minDist)  //reversing the indices makes the loop traverse down the columns
                    {
                        minDist = reducedMatrix[j, i];
                        if (minDist <= 0)
                        {
                            break; //already reached a zero, no need to keep looking
                        }
                    }
                }
                if (minDist > 0)
                {
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (this.VisitedCityIndices.Contains(j) || this.VisitedCityIndices.Contains(i))
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[j, i] -= minDist; //actually perform the reduction
                    }
                    bound += minDist;
                }
            }

            this.LowerBound = bound;
            this.ReducedMatrix = reducedMatrix;
            //Now we have the reduced matrix and the bound is set
        }

        public void newbuildMatrix(City[] Cities)
        {
            double[,] reducedMatrix = new double[Cities.Length, Cities.Length]; //init matrix
            double bound = 0;



            for (int i = 0; i < Cities.Length; ++i)
            {
                double minDist = Double.PositiveInfinity;

                for (int j = 0; j < Cities.Length; ++j)
                {
                    //loop through the Cities array ^2 times and find the distance between the higher level city (i)
                    //and the lower level city (j). Put the distance into the reduced matrix in the array at i.
                    if (i == j)
                    {
                        reducedMatrix[i, j] = Double.PositiveInfinity;
                        continue; //it's the same city, infinite distance to itself
                    }
                    reducedMatrix[i, j] = Cities[i].costToGetTo(Cities[j]); //put the distance i-j in the matrix
                    if (reducedMatrix[i, j] < minDist) //if this is the smallest distance from i so far
                    {
                        minDist = reducedMatrix[i, j]; //remember the smallest distance so far
                    }
                }

                //once we have the min distance from city i, if it's not 0, reloop through the matrix and reduce 
                //all distances by the minDist, then add to bound
                if (minDist > 0 && minDist < Double.PositiveInfinity)
                {
                    //For each j, remember the cost and loop back through after finishing the initial n loops of j
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (this.VisitedCityIndices.Contains(j) || this.VisitedCityIndices.Contains(i))
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[i, j] -= minDist;
                    }
                    bound += minDist;
                }
            }
            //to subtract the smallest cost from i to j from all i to j values. Store all of these min values
            //in a bound variable.

            //TODO: There may be a faster way to do this, but for now, once that is done (all rows are reduced), 
            //loop back through the matrix and find all columns without a 0 value (find min of column and if not 0,
            //it needs to be reduced further). Loop back through if necessary and subtract min from all distances,
            //add that min to the bound.
            for (int i = 0; i < Cities.Length; ++i)
            {
                if (this.VisitedCityIndices.Contains(i))
                {
                    continue; //this city has been settled
                }
                double minDist = Double.PositiveInfinity;
                for (int j = 0; j < Cities.Length; ++j)
                {
                    if (VisitedCityIndices.Contains(j))
                    {
                        continue; //this city has been settled
                    }
                    if (reducedMatrix[j, i] < minDist)  //reversing the indices makes the loop traverse down the columns
                    {
                        minDist = reducedMatrix[j, i];
                        if (minDist <= 0)
                        {
                            break; //already reached a zero, no need to keep looking
                        }
                    }
                }
                if (minDist > 0)
                {
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (this.VisitedCityIndices.Contains(j) || this.VisitedCityIndices.Contains(i))
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[j, i] -= minDist; //actually perform the reduction
                    }
                    bound += minDist;
                }
            }

            this.LowerBound = bound;
            this.ReducedMatrix = reducedMatrix;
            //Now we have the reduced matrix and the bound is set
        }

        public void buildChildMatrix(City[] Cities, State oldState, int newCityIndex)
        {
            
            double[,] reducedMatrix = new double[Cities.Length, Cities.Length]; //init matrix
            double bound = 0;
            int lastIndexSoFar = -1;

            costSoFar = oldState.costSoFar;

            if (oldState.orderedVisitedCityIndices.Count > 0)
            {
                lastIndexSoFar = oldState.orderedVisitedCityIndices[oldState.orderedVisitedCityIndices.Count - 1];
                costSoFar += oldState.reducedMatrix[lastIndexSoFar, newCityIndex];
            }
            if ( costSoFar > 0)
            {
                int fish = 3;
            }

            this.visitedCityIndices = new HashSet<int>();
            foreach (int index in oldState.visitedCityIndices)
            {
                this.visitedCityIndices.Add(index);
            }
            this.visitedCityIndices.Add(newCityIndex); //this is now the set used by the old state plus one new city
            this.routeSoFar.Route = new ArrayList();
            foreach (City city in oldState.routeSoFar.Route)
            {
                this.routeSoFar.Route.Add(city);
            }
            this.routeSoFar.Route.Add(Cities[newCityIndex]); //this is the old list plus the new city

            this.orderedVisitedCityIndices = new List<int>();
            foreach (int index in oldState.orderedVisitedCityIndices)
            {
                this.orderedVisitedCityIndices.Add(index);
            }
            this.orderedVisitedCityIndices.Add(newCityIndex); //this is the ordered set plus the new city

            if (this.visitedCityIndices.Count <= 1) //can't make a logical distance from a city to itself
            {
                this.LowerBound = oldState.lowerBound;
                this.ReducedMatrix = oldState.reducedMatrix;
                return;
            }

            for (int i = 0; i < Cities.Length; ++i)
            {
                double minDist = Double.PositiveInfinity;

                for (int j = 0; j < Cities.Length; ++j)
                {
                    if (i == this.orderedVisitedCityIndices[orderedVisitedCityIndices.Count -2] || j == newCityIndex)
                    {
                        reducedMatrix[i, j] = Double.PositiveInfinity;
                        continue; //this city has been settled
                    }
                    
                    //loop through the Cities array ^2 times and find the distance between the higher level city (i)
                    //and the lower level city (j). Put the distance into the reduced matrix in the array at i.
                    //if (i == j)
                    //{
                    //    reducedMatrix[i, j] = Double.PositiveInfinity;
                    //    continue; //it's the same city, infinite distance to itself
                    //}

                    reducedMatrix[i, j] = oldState.reducedMatrix[i, j]; //put the distance i-j in the matrix
                    if (reducedMatrix[i, j] < minDist) //if this is the smallest distance from i so far
                    {
                        minDist = reducedMatrix[i, j]; //remember the smallest distance so far
                    }
                }
                //once we have the min distance from city i, if it's not 0, reloop through the matrix and reduce 
                //all distances by the minDist, then add to bound
                if (minDist > 0 && minDist < Double.PositiveInfinity)
                {
                    //For each j, remember the cost and loop back through after finishing the initial n loops of j
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (this.VisitedCityIndices.Contains(j) || this.VisitedCityIndices.Contains(i))
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[i, j] -= minDist;
                    }
                    bound += minDist;
                }
            }
            //to subtract the smallest cost from i to j from all i to j values. Store all of these min values
            //in a bound variable.

            //TODO: There may be a faster way to do this, but for now, once that is done (all rows are reduced), 
            //loop back through the matrix and find all columns without a 0 value (find min of column and if not 0,
            //it needs to be reduced further). Loop back through if necessary and subtract min from all distances,
            //add that min to the bound.
            for (int i = 0; i < Cities.Length; ++i)
            {
                double minDist = Double.PositiveInfinity;
                for (int j = 0; j < Cities.Length; ++j)
                {
                    if (j == this.orderedVisitedCityIndices[orderedVisitedCityIndices.Count - 2] || i == newCityIndex)
                    {
                        continue; //this city has been settled
                    }
                    if (reducedMatrix[j, i] < minDist)  //reversing the indices makes the loop traverse down the columns
                    {
                        minDist = reducedMatrix[j, i];
                        if (minDist <= 0)
                        {
                            break; //already reached a zero, no need to keep looking
                        }
                    }
                }
                if (minDist > 0 && minDist < Double.PositiveInfinity)
                {
                    for (int j = 0; j < Cities.Length; ++j)
                    {
                        if (j == this.orderedVisitedCityIndices[orderedVisitedCityIndices.Count - 2] || i == newCityIndex)
                        {
                            continue; //this city has been settled
                        }
                        reducedMatrix[j, i] -= minDist; //actually perform the reduction
                    }
                    bound += minDist;
                }
            }

            this.LowerBound =  bound + oldState.lowerBound + costSoFar;
            this.ReducedMatrix = reducedMatrix;
            //Now we have the reduced matrix and the bound is set
        }
    }
}
