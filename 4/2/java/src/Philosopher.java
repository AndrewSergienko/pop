import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private int id;
    private Semaphore[] forks;
    private Semaphore canTakeFork;
    private int dinner_numbers;
    private int second_fork;

    public Philosopher(int id, int dinner_numbers, int ph_nums, Semaphore[] forks, Semaphore canTakeFork){
        this.id = id;
        this.forks = forks;
        this.canTakeFork = canTakeFork;
        this.dinner_numbers = dinner_numbers;
        second_fork = id % (ph_nums-1) + 1;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < dinner_numbers; i++) {
                System.out.println("philosopher" + id + " thinking");
                canTakeFork.acquire();
                forks[id].acquire();
                System.out.println("philosopher" + id + " await " + second_fork + " fork");
                forks[second_fork].acquire();
                System.out.println("philosopher" + id + " eating " + i + " times");
                forks[id].release();
                forks[second_fork].release();
                canTakeFork.release();
            }
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }
}
