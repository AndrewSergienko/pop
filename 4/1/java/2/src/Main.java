import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Token token = new Token(5, 5);
        Semaphore[] forks = new Semaphore[5];
        for(int i = 0; i < 5; i++){
            forks[i] = new Semaphore(1);
        }
        new Thread(token).start();
        for(int i = 0; i < 5; i++){
            new Thread(new Philosopher(i, 5, 5, token, forks)).start();
        }
    }
}
