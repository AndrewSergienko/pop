import java.util.Random;

public class MainThread extends Thread{
    private Random r = new Random();

    public void run() {
        for (int i = 0; i < r.nextInt(3, 10); i++) {
            new Thread(new SumThread(i, r.nextInt(5000, 10000), r.nextInt(1, 5))).start();
        }
    }
}
