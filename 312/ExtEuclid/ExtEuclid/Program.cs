using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ExtEuclidSpace
{
    class ExtEuclid
    {
        private Double[] Euclid(Double a, Double b)
        {
            Double [] retvals = new Double[3]{1, 0, a};

            if (b == 0)
            {
                return retvals;
            }
            Double[] temp = Euclid(b, a % b);
            return new Double[3] {temp[1], temp[0] - (a/b) * temp[1] , temp[2]};
        }

        static void Main(string[] args)
        {
            ExtEuclid euclid = new ExtEuclid();
            Double[] answer = euclid.Euclid(160, 640);
            System.Console.WriteLine(answer[2]);
            System.Console.ReadLine();
        }
    }
}
