namespace Program{
    class Program{
        private static Random rnd = new Random();
        static void Main(string[] args)
        {
            new Thread(()=>MainThread()).Start();
            
        }
        static void MainThread(){
            int arrayLen = rnd.Next(1, 10) * 100;
            int[] array = new int[arrayLen];
            Thread[] threads = new Thread[arrayLen/100];
            MinValue minValue = new MinValue();
            for(int i = 0; i < arrayLen; i++){
                array[i] = rnd.Next(-100000, 100000);
            }
            for(int i = 0; i < arrayLen/100; i++){
                int[] subarray = new int[100];
                Array.Copy(array, i*100, subarray, 0, 100);
                threads[i] = new Thread(()=>FindMind(subarray, minValue));
                threads[i].Start();
            }
            // Очікування завершення всіх потоків
            bool notFinished = true;
            while(notFinished){
                foreach(Thread t in threads){
                    if(t.ThreadState.ToString() != "Stopped"){
                        notFinished = true;
                        break;
                    }
                    notFinished = false;
                }
            }
            Console.WriteLine("Результат: " + minValue.min);
        }

        static void FindMind(int[] array, MinValue minValue){
            int min = Int32.MaxValue;
            for(int i = 0; i < array.Length; i++){
                //min = array[i] < min ? array[i] : min;
                if(array[i] < min){
                    min = array[i];
                }
            }
            minValue.set(min);
        }
    }
    class MinValue{
        public object locker = new();
        public int min = Int32.MaxValue;
        
        public void set(int value){
            lock(locker){
                min = value < min ? value : min;
            }
        }
        
    }
}

