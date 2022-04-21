import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int len = random.nextInt(1, 10) * 100;

        System.out.println(len);
        int[] nums = new int[len];
        for(int i = 0; i < len; i++){
            nums[i] = random.nextInt(-100000, 100000);
        }

        System.out.println(len/100);
        MinValue minValue = new MinValue();
        for(int i = 0; i < len/100; i++){
            new Thread(new FindMinThread(Arrays.copyOfRange(nums, i*100, (i+1)*100-1), minValue)).start();
        }

        // Перевірка в межах одного потоку
        int checkMin = Integer.MAX_VALUE;
        for(int i : nums){
            checkMin = Math.min(checkMin, i);
        }
        System.out.println("Результат: " + minValue.value + "; Перевірка: " + checkMin);
    }
}
