import java.util.Objects;
import java.util.Random;

public class Main {
    static int minValue = Integer.MAX_VALUE;
    static int minIndex = 0;
    public static void main(String[] args) {
        Random random = new Random();
        int arrLen = 1000;
        int threadsNum = 10;

        int[] nums = new int[arrLen];
        Thread[] threads = new Thread[threadsNum];

        for(int i = 0; i < arrLen; i++){
            nums[i] = random.nextInt(-100000, 100000);
        }

        int partLen = arrLen/threadsNum;
        for(int i = 0; i < threadsNum-1; i++){
            threads[i] = new Thread(new FindMinThread(i*partLen, (i+1)*partLen, nums));
            threads[i].start();
        }
        threads[threads.length-1] = new Thread(new FindMinThread((threads.length-1)*partLen, arrLen, nums));
        threads[threads.length-1].start();

        // Очікування завершення всих потоків
        boolean notFinished = true;
        while (notFinished){
            for(Thread t : threads){
                if(!Objects.equals(t.getState().toString(), "TERMINATED")){
                    notFinished = true;
                    break;
                }
                notFinished = false;
            }
        }
        // Перевірка в межах одного потоку
        int checkMinValue = Integer.MAX_VALUE;
        int checkMinIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] < checkMinValue){
                checkMinValue = nums[i];
                checkMinIndex = i;
            }
        }
        System.out.println("Результат: nums[" + minIndex + "] = " + minValue + "; Перевірка: nums[" + checkMinIndex + "] = " + checkMinValue);
    }
    public static synchronized void setMin(int value, int index){
        if(value < minValue){
            minValue = value;
            minIndex = index;
        }
    }
}
