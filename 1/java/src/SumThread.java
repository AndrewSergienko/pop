import java.util.Random;

public class SumThread implements Runnable{
    private final int id;
    private final int end;
    private final int step;
    private int result = 0;
    private int elemCount = 0;

    public SumThread(int id, int end, int step){
        this.id = id;
        this.end = end;
        this.step = step;
    }

    @Override
    public void run() {
        Random r = new Random();
        for(int i = 1; i <= end; i += step)
        {
            result += i;
            elemCount++;
        }
        System.out.println(this);
    }

    @Override
    public String toString(){
        return String.format("id: %1s; Sum: %2s; Addition count: %3s",
                id, result, elemCount);
    }
}
