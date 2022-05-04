import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private final int id;
    private final int dinner_numbers;
    private final int second_fork;
    private final Semaphore[] forks;
    private final boolean reverse;

    public Philosopher(int id, int dinner_numbers, int ph_nums, boolean reverse, Semaphore[] forks){
        this.id = id;
        this.dinner_numbers = dinner_numbers;
        this.forks = forks;
        this.reverse = reverse;
        second_fork = id % (ph_nums-1) + 1;
    }

    @Override
    public void run() {
        try{
            for(int i = 0; i < dinner_numbers; i++) {
                if (!reverse) {
                    System.out.println("philosopher" + id + " thinking");
                    forks[id].acquire();
                    System.out.println("philosopher" + id + " await " + second_fork + " fork");
                    forks[second_fork].acquire();

                    System.out.println("philosopher" + id + " eating " + i + " times");

                    forks[id].release();
                    forks[second_fork].release();
                }
                else {
                    System.out.println("philosopher" + id + " thinking");
                    forks[second_fork].acquire();
                    System.out.println("philosopher" + id + " await " + second_fork + " fork");
                    forks[id].acquire();

                    System.out.println("philosopher" + id + " eating " + i + " times");

                    forks[id].release();
                    forks[second_fork].release();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
