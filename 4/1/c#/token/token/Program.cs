namespace lab42
{
    class Program
    {
        static Semaphore orderNotEmpty = new Semaphore(0, 5);
        static Semaphore orderAccess = new Semaphore(1, 1);
        static Semaphore less2dinners = new Semaphore(2, 2);
        static Semaphore[] readyDinner = new Semaphore[5];
        static Semaphore[] forks = new Semaphore[5];
        static List<int> orders = new List<int>();
        static void Main()
        {
            Console.WriteLine("aaa");
            for(int i = 0; i < 5; i++)
            {
                readyDinner[i] = new Semaphore(0, 100);
                forks[i] = new Semaphore(1, 1);
            }

            new Thread(() => token()).Start();
            for (int i = 0; i < 5; i++)
            {
                Console.WriteLine("new thread" + i);
                new Thread(() => philosopher(i)).Start();
            }
        }
        static void token()
        {
            Console.WriteLine("Token start");
            for(int i = 0; i < 25; i++)
            {
                orderNotEmpty.WaitOne();
                orderAccess.WaitOne();
                int ph_id = orders[0];
                Console.WriteLine(ph_id);
                readyDinner[ph_id].Release();
                orders.RemoveAt(0);
                orderAccess.Release();
            }
        }
        static void philosopher(int id)
        {
            int second_fork = 0;
            if(id == 4)
            {
                second_fork = 0;
            }
            else
            {
                second_fork = id + 1;
            }
            for (int i = 0; i < 5; i++)
            {
                orderAccess.WaitOne();
                orders.Add(id);
                orderNotEmpty.Release();
                orderAccess.Release();

                readyDinner[id].WaitOne();
                less2dinners.WaitOne();
                forks[id].WaitOne();
                forks[second_fork].WaitOne();
                forks[id].Release();
                forks[second_fork].Release();
                less2dinners.Release();
            }
        }
    }
}
