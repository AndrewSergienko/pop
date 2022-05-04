import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Token implements Runnable{
    public Semaphore orderNotEmpty = new Semaphore(0);
    public Semaphore orderAccess = new Semaphore(1);
    public Semaphore less2dinners = new Semaphore(2);
    public Semaphore[] readyDinner;
    public ArrayList<Integer> orders = new ArrayList<Integer>();
    private final int dinner_nums;

    public Token(int philosophers, int dinner_nums){
        this.dinner_nums = dinner_nums;
        readyDinner = new Semaphore[philosophers];
        for (int i = 0; i < philosophers; i++) {
            readyDinner[i] = new Semaphore(0);
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < readyDinner.length * dinner_nums; i++) {
                orderNotEmpty.acquire();
                orderAccess.acquire();
                int phil_id = orders.get(0);
                readyDinner[phil_id].release();
                orders.remove(0);
                orderAccess.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End token work");
    }
}
