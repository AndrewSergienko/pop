import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        for(int i = 0; i < 5; i++){
            forks[i] = new Semaphore(1);
        }
        for(int i = 0; i < 4; i++){
            new Thread(new Philosopher(i, 5, 5, false, forks)).start();
        }
        // reverse Philosopher
        new Thread(new Philosopher(4, 5, 5, true, forks)).start();
    }
}
