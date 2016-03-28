using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ModularExponentiation
{
    class Program
    {
        static int modExp(int x, int y, int N)
        {
            if (y == 0) return 1;

            int z = modExp(x, (y / 2), N);

            if (y % 2 == 0)
            {
                return (z * z % N);
            }
            else
            {
                return (x * z * z) % N;
            }
        }

        static void Main()
        {
            int res = modExp(5, 125, 12);

            Console.Write(res);
            Console.ReadKey();
        }
    }
}
