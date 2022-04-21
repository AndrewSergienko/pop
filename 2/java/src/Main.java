import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int len = random.nextInt(1, 10) * 100;

        int[] nums = new int[len];
        for(int i = 0; i < len; i++){
            nums[i] = random.nextInt(-100000, 100000);
        }

        Thread[] threads = new Thread[len/100];
        MinValue minValue = new MinValue();
        for(int i = 0; i < len/100; i++){
            threads[i] = new Thread(new FindMinThread(Arrays.copyOfRange(nums, i*100, (i+1)*100-1), minValue));
            threads[i].start();
        }

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
        int checkMin = Integer.MAX_VALUE;
        for(int i : nums){
            checkMin = Math.min(checkMin, i);
        }
        System.out.println("Результат: " + minValue.value + "; Перевірка: " + checkMin);
    }
}
