using System;
using System.Collections.Generic;
using System.Text;

namespace GeneticsLab
{
    class PairWiseAlign
    {
        
        /// <summary>
        /// Align only 5000 characters in each sequence.
        /// </summary>
        private const int MaxCharactersToAlign = 5000;
        private const int MATCH = -3;
        private const int SUB = 1;
        private const int INDEL = 5;
        private const char SPACER = '-';

        /// <summary>
        /// this is the function you implement.
        /// </summary>
        /// <param name="sequenceA">the first sequence</param>
        /// <param name="sequenceB">the second sequence, may have length not equal to the length of the first seq.</param>
        /// <param name="resultTableSoFar">the table of alignment results that has been generated so far using pair-wise alignment</param>
        /// <param name="rowInTable">this particular alignment problem will occupy a cell in this row the result table.</param>
        /// <param name="columnInTable">this particular alignment will occupy a cell in this column of the result table.</param>
        /// <returns>the alignment score for sequenceA and sequenceB.  The calling function places the result in entry rowInTable,columnInTable
        /// of the ResultTable</returns>
        public int Align(GeneSequence sequenceA, GeneSequence sequenceB,
            ResultTable resultTableSoFar, int rowInTable, int columnInTable)
        {
            //if the mirror of the cell referenced is already set, set this one to match
            if (resultTableSoFar.GetCell(columnInTable, rowInTable) != 0.0)
            {
                return (int)resultTableSoFar.GetCell(columnInTable, rowInTable);
            }
            else if (sequenceA.Equals(sequenceB) )
            {
                return 0;
            }
            //start with memory inefficient method, then streamline. 
            //This should be the scoring function only
            return this.score(sequenceA.Sequence, sequenceB.Sequence);
        }

        private int score(String sequenceA, String sequenceB)
        {
            //if the sequence is less than 5000, don't even go that far
            int lengthA = Math.Min(sequenceA.Length, MaxCharactersToAlign);
            int lengthB = Math.Min(sequenceB.Length, MaxCharactersToAlign);

            //create array of ints size 2 by min ( length of b,  5000)
            int[,] dpTable = new int[2, lengthB + 1];

            //for i to length of seqA or 5000 
            for (int i = 0; i < lengthA  +1;  ++i)
            {
                //for j to length of seqB or 5000
                for (int j = 0; j < lengthB + 1; ++j)
                {
                    //if i == 0 and j == 0 -> save 0 in (i % 2; j)
                    if (i == 0 && j == 0)
                    {
                        dpTable[i, j] = 0;
                    }
                    //if i == 0 and j > 0 -> save cost from array at ((i % 2), j-1) + 
                    //INDEL modding by 2 keeps it within the two index array heightwise
                    else if (i == 0 && j > 0)
                    {
                        dpTable[i %2, j] = (dpTable[i % 2, j - 1]) + INDEL;
                    }
                    //if i > 0 and j == 0 -> save cost from array at (i-1 % 2), j + INDEL
                    else if (i > 0 && j == 0)
                    {
                        dpTable[i % 2, j] = (dpTable[(i -1) % 2, j]) + INDEL;
                    }
                    else
                    {
                        //when i > 0 and j > 0, generate 3 ints

                        //1st = saved value at ((i-1 % 2), j-1) + : 
                        //MATCH if strA[i] == strB[j], else SUB
                        int cost1 = dpTable[(i - 1) % 2, j - 1];
                        cost1 += (sequenceA[i - 1] == sequenceB[j - 1]) ? MATCH : SUB;
                        //2nd = saved value at ((i-1 % 2), j) + INDEL
                        int cost2 = dpTable[(i - 1) % 2, j] + INDEL;
                        //3nd = saved value at ((i % 2), j-1) + INDEL
                        int cost3 = dpTable[(i % 2), j - 1] + INDEL;
                        //save at ((i % 2),j) min of the three
                        dpTable[i % 2, j] = Math.Min(cost1, Math.Min ( cost2, cost3 ) );
                    }
                }
            }
            //return array index ((i % 2),j)
            return dpTable[(lengthA ) % 2, lengthB];
        }

        public string getAlignment(string sequenceA, string sequenceB)
        {
            //if the sequence is less than 100, don't even go that far
            int lengthA = Math.Min(100, sequenceA.Length);
            int lengthB = Math.Min(100, sequenceB.Length);

            //create array of ints size length A by length B
            Index[,] dpTable = new Index[lengthA + 1, lengthB + 1];

            //for i to length of seqA or 5000 
            for (int i = 0; i < lengthA + 1; ++i)
            {
                //for j to length of seqB or 5000
                for (int j = 0; j < lengthB + 1; ++j)
                {
                    //if i == 0 and j == 0 -> save 0 in (i; j)
                    if (i == 0 && j == 0)
                    {
                        dpTable[i, j] = new Index(0, null, ' ', ' ');
                    }
                    //if i == 0 and j > 0 -> save cost from array at (i, j-1) + INDEL
                    //don't mod here, needs to have all indices for lookup
                    else if (i == 0 && j > 0)
                    {
                        Index previous = dpTable[i, j - 1];
                        Index nextIndex = new Index(previous.cost + INDEL, 
                            previous, SPACER, sequenceB[j - 1]);
                        dpTable[i, j] = nextIndex;
                    }
                    //if i > 0 and j == 0 -> save cost from array at (i-1), j + INDEL
                    else if (i > 0 && j == 0)
                    {
                        Index previous = dpTable[(i - 1), j];
                        Index next = new Index(previous.cost + INDEL, previous,
                            sequenceA[i - 1], SPACER);
                        dpTable[i, j] = next;
                    }
                    else
                    {
                        //when i > 0 and j > 0, generate 3 ints

                        //1st = saved value at ((i-1), j-1) + : 
                        //MATCH if strA[i] == strB[j], else SUB
                        int cost1 = dpTable[(i - 1), j - 1].cost;
                        cost1 += (sequenceA[i - 1] == sequenceB[j - 1]) ? MATCH : SUB;

                        //2nd = saved value at ((i-1), j) + INDEL
                        int cost2 = dpTable[(i - 1), j].cost + INDEL;

                        //3nd = saved value at (i, j-1) + INDEL
                        int cost3 = dpTable[i, j - 1].cost + INDEL;

                        //save at (i,j) min of the three
                        if ( cost1 < cost2 && cost1 < cost3)
                        {
                            //if it was a match, pass in the same char twice
                            //to cover match and sub, pass in the char from the 
                            //correct index of both
                            dpTable[i, j] = new Index(cost1, dpTable[(i - 1), j - 1], 
                                sequenceA[i - 1], sequenceB[j - 1]);

                        }
                        else if ( cost2 < cost1 && cost2 < cost3)
                        {
                            //indel with dash in string B
                            dpTable[i, j] = new Index(cost2, dpTable[(i - 1), j],
                                sequenceA[i - 1], SPACER);
                        }
                        else
                        {
                            //indel with dash in string A
                            dpTable[i, j] = new Index(cost3, dpTable[i, j - 1],
                                SPACER, sequenceB[j - 1]);
                        }
                    }
                }
            }

            //loop back through the pointers to find the alignment
            Index finalPoint = dpTable[(lengthA), lengthB];
            string aStr = "";
            string bStr = "";

            while ( finalPoint.prev != null)
            {
                aStr = finalPoint.strAChar + aStr;
                bStr = finalPoint.strBChar + bStr;
                finalPoint = finalPoint.prev;
            }
            //return the extracted strings
            return "\n" +  aStr + "\n" + bStr;
        }
    }

    class Index
    {
        public int cost;
        public Index prev;
        public char strAChar;
        public char strBChar;

        public Index(int c, Index p, char chA, char chB)
        {
            this.cost = c;
            this.prev = p;
            this.strAChar = chA;
            this.strBChar = chB;
        }
    }
}