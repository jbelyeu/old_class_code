using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Numerics;

namespace FermatPrimality
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Solve_Click(object sender, EventArgs e)
        {
            BigInteger N = BigInteger.Parse(Input.Text);
            double prob = FermatTest(N);
            if ( prob == 0)
            {
                Output.Text = "No";
            }
            else
            {
                Output.Text = "Yes with probability " + prob; 
            }
        }

        private double FermatTest(BigInteger N)
        {
            //pick 20 random values for a to test with
            HashSet<BigInteger> randomAVals = this.getRandomNumbers(N);

            //keep track of probability of nonprimality
            double probOfNonPrimality = 1;

            //loop through all a values and test primality with each
            foreach (BigInteger a in randomAVals)
            {
                if (ModularExponentiator(a, N - 1, N) == 1)
                {
                    //if d == 1, then probability of primality for this value of a is 1/2.
                    //The equation for computing correctness id 1 - 1/(2^k). This multiplication effectively increments k
                    probOfNonPrimality *= .5;
                }
                else
                {
                    probOfNonPrimality = 1;
                    break;
                }
            }
            //1- probability of nonprimality is primality
            return 1 - probOfNonPrimality;
        }

        private BigInteger ModularExponentiator(BigInteger x, BigInteger y, BigInteger N)
        {
            //base case
            if (y == 0) return 1;

            //recurse
            BigInteger z = ModularExponentiator(x, (y / 2), N);


            if (y.IsEven)
            {
                return (z * z % N);
            }
            else
            {
                return (x * z * z) % N;
            }
        }

        private HashSet<BigInteger> getRandomNumbers(BigInteger N)
        {
            HashSet<BigInteger> numbers = new HashSet<BigInteger>();

            Random seed = new System.Random();
            BigInteger randNum = 0;
            int length = (int)(BigInteger.Log(N, 2) + 1.5);

            for (int j = 0; j < 20; ++j)
            {
                //generate random number
                for (int i = 0; i < length / 32; ++i)
                {
                    randNum = (randNum << 32) + seed.Next();
                }

                //make sure the random number is in the desired range
                if (randNum < 1)
                {
                    --j;
                    continue;
                }
                randNum %= N;
                numbers.Add(randNum);
            }
            return numbers;
        }
    }
}
