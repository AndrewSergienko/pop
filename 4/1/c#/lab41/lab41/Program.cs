namespace lab4
{
    class Program
    {
        static void Main()
        {
            Semaphore[] forks = new Semaphore[5];
            for(int i = 0; i < forks.Length; i++)
            {
                forks[i] = new Semaphore(1, 1);
            }
            for(int i = 0; i < 4; i++)
            {
                new Thread(() => philosopher(i, 5, 5, false, forks)).Start();
            }
            new Thread(() => philosopher(4, 5, 5, true, forks)).Start();

        }
        static void philosopher(int id, int dinnerNums,
            int phNums, bool reverse, Semaphore[] forks)
        {
            int second_fork = id == phNums-1 ? 0 : id + 1;
            for (int i = 0; i < dinnerNums; i++)
            {
                if (!reverse)
                {
                    //Console.WriteLine("philosopher {0} thinking", id);
                    forks[id].WaitOne();
                    //Console.WriteLine("philosopher {0} await {1} fork", id, second_fork);
                    forks[second_fork].WaitOne();
                    //Console.WriteLine("philosopher {0} eating {1} times", id, i);
                    forks[id].Release();
                    forks[second_fork].Release();
                }
                else
                {
                    //Console.WriteLine("philosopher {0} thinking", id);
                    forks[second_fork].WaitOne();
                    //Console.WriteLine("philosopher {0} await {1} fork", id, id);
                    forks[id].WaitOne();
                    //Console.WriteLine("philosopher {0} eating {1} times", id, i);
                    forks[second_fork].Release();
                    forks[id].Release();
                }
            }
        }
    }
}
