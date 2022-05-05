import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private final int id;
    private final int dinner_numbers;
    private final int second_fork;
    private Semaphore[] forks;
    private Token token;

    public Philosopher(int id, int dinner_numbers, int ph_nums, Token token, Semaphore[] forks){
        this.id = id;
        this.dinner_numbers = dinner_numbers;
        this.token = token;
        this.forks = forks;
        second_fork = id == ph_nums-1 ? 0 : id + 1;
    }

    @Override
    public void run() {
        try{
            for(int i = 0; i < dinner_numbers; i++) {
                token.orderAccess.acquire();
                token.orders.add(id);
                token.orderNotEmpty.release();
                token.orderAccess.release();

                System.out.println("philosopher" + id + " await token");

                System.out.println(id +  " acquire ready dinner");
                token.readyDinner[id].acquire();
                token.less2dinners.acquire();
                System.out.println(id +  " acquire first fork");
                forks[id].acquire();
                System.out.println(id +  " acquire second fork");
                forks[second_fork].acquire();
                System.out.println("philosopher" + id + " eating " + i + " times");
                forks[id].release();
                forks[second_fork].release();
                token.less2dinners.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}