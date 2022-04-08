using System.Threading;

namespace Lab1
{
    class Program
    {
        static private Random rnd = new Random();
        static void Main(string[] args)
        {
            new Thread(()=>MainThread()).Start();
        }
        static void MainThread(){
            for(int i = 0; i < rnd.Next(5, 10); i++){
                new Thread(()=>Sum(i, rnd.Next(1000, 10000), rnd.Next(2, 10))).Start();
            }
        }
        static void Sum(int id, int end, int step){
            int result = 0;
            int elemCount = 0;
            for(int i = 0; i < end; i += step){
                result += i;
                elemCount++;
            }
            Console.WriteLine("id: {0}; Sum: {1}; Addition count: {2}", id, result, elemCount);
        }
    }
}