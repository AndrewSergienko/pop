import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        Semaphore canTakeFork = new Semaphore(4);
        for(int i = 0; i < 5; i++){
            forks[i] = new Semaphore(1);
        }
        for(int i = 0; i < 4; i++){
            new Thread(new Philosopher(i, 5, 5, forks, canTakeFork)).start();
        }
    }
}
