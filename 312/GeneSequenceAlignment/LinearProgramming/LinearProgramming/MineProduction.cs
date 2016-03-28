using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LinearProgramming
{
    class MineProduction
    {      
        private double[,] A = new double[9, 9]
            {{0,    0,      0,      0,      0, 0, 0, 0, 0},
            {0,     0,      0,      0,      0, 0, 0, 0, 0},
            {0,     0,      0,      0,      0, 0, 0, 0, 0},
            {0,     0,      0,      0,      0, 0, 0, 0, 0},
            {0.1,   0.5,    0.333,  1,      0, 0, 0, 0, 0},
            {30,    15,     19,     12,     0, 0, 0, 0, 0},
            {1000,  6000,   4100,   9100,   0, 0, 0, 0, 0},
            {50,    20,     21,     10,     0, 0, 0, 0, 0},
            {4,     6,      16,     30,     0, 0, 0, 0, 0}};

        private double[] c = new double[9] { 10.20, 422.30, 6.91, 853, 0, 0, 0, 0, 0 };
        private double[] b = new double[9] { 0, 0, 0, 0, 2000, 1000, 1000000, 640, 432 };
        private int[] B = new int[5] { 4, 5, 6, 7, 8 };
        private int[] N = new int[4] { 0, 1, 2, 3 };
        

        private double[] answerArray = new double[9];

        static void Main(string[] args)
        {
            MineProduction programmer = new MineProduction();
            programmer.run();
            while (true) {}
        }

        public MineProduction(){}

        public void run()
        {
            double v = 0;
            v = this.simplex(v);

            Console.Write("Profit: \t$");
            Console.WriteLine(v);

            Console.Write("Copper: \t");
            Console.Write(this.answerArray[0]);
            Console.WriteLine(" oz.");

            Console.Write("Gold: \t\t");
            Console.Write(this.answerArray[1]);
            Console.WriteLine(" oz.");

            Console.Write("Silver: \t");
            Console.Write(this.answerArray[2]);
            Console.WriteLine(" oz.");

            Console.Write("Platinum: \t");
            Console.Write(this.answerArray[3]);
            Console.WriteLine(" oz.");
        }

        private double simplex(double v)
        {
            int e = -1;
            do
            {
                //if there exists e ∈ N such that ce > 0
                // e is the index of the entering variable
                e = findValidEnteringVariable(N, c);
                
                //if there is no valid entering variable, e will be -1
                if (e != -1)
                {
                    double deltaMin = double.MaxValue; //infinity for a double
                    int leavingVar = -1;

                    foreach (int i in B)
                    {
                        if (A[i, e] > 0)
                        {
                            double delta = b[i] / A[i, e];
                            //delta is the check ratio for the current constraint
                            if ( delta < deltaMin)
                            {
                                deltaMin = delta;
                                leavingVar = i;
                            }
                        }
                    }
                    if (deltaMin == double.MaxValue)
                    {
                        return double.MinValue;// deltaMin;
                    }
                    else
                    {
                        //pivot computes coefficients of constraints
                        //It modifies the values in class member variables directly
                        v = this.pivot(leavingVar, e, v);
                    }
                } //no else required because e is already set to -1 if not in if statement
            } while (e != -1);

            for (int i = 1; i < b.Length; ++i)
            {
                if (findInB(i) )
                {
                    this.answerArray[i] = b[i];
                }
                else
                {
                    this.answerArray[i] = 0;
                }
            }
            return v;
        }

        private int findValidEnteringVariable(int[] N, double[] c)
        {
            //if there is an int e in N such that c[e] is greater than 0, return e
            //otherwise, return -1 as fail flag

            foreach (int e in N)
            {
                if (c[e] > 0)
                {
                    return e;
                }
            }
            return -1;
        }

        private double pivot(int leavingVar, int e, double v)
        {
            double[,] newA = new double[9, 9];
            double[] newb = new double[9];
            double[] newc = new double[9];

            newb[e] = b[leavingVar] / A[leavingVar, e];

            //computes the coefficients of constrains for new basic variable  X[e]
            foreach (int j in N)
            {
                //actually N - e, skip that value
                if ( j == e)
                {
                    continue;
                }
                newA[e, j] = A[leavingVar, j] / A[leavingVar, e];
            }

            newA[e, leavingVar] = 1 / A[leavingVar, e];
     
            //and for the other constraints
            foreach( int i in B)
            {
                //actually B - l, skip that one, too
                if (i == leavingVar )
                {
                    continue;
                }
                newb[i] = b[i] - A[i, e] * newb[e];
                foreach ( int j in N)
                {
                    //and skip e
                    if ( j == e)
                    {
                        continue;
                    }
                    newA[i, j] = A[i, j] - A[i, e] * newA[e, j];
                }
                newA[i, leavingVar] = - (A[i, e] * newA[e, leavingVar]);
            }

            //compute new objective function
            double newV = v + (c[e] * newb[e]);

            foreach (int j in N)
            {
                if (j == e) //Skip it
                {
                    continue;
                }
                newc[j] = c[j] - c[e] * newA[e, j];
            }

            newc[leavingVar] = -(c[e] * newA[e, leavingVar]);

            //create new non-basic variable index set
            //by replacing old value of e with the leaving variable           
            for (int i = 0; i < N.Length; ++i)
            {
                if (N[i] == e)
                {
                    N[i] = leavingVar;
                    break;
                }
            }

            //create new basic variable index set
            //by replacing old leaving variable with value of e
            for (int i = 0; i < B.Length; ++i)
            {
                if (B[i] == leavingVar)
                {
                    B[i] = e;
                    break;
                }
            }

            this.A = newA;
            this.b = newb;
            this.c = newc;
            return newV;
        }

        private bool findInB(int i)
        {
            foreach (int j in this.B)
            {
                return true;
            }
            return false;
        }
    }
}
