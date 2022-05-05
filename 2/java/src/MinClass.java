import java.util.Random;

public class MinClass {
    public int minValue = Integer.MAX_VALUE;
    public int minIndex = 0;
    public int[] arr;

    public MinClass(int arrLen) {
        Random random = new Random();
        arr = new int[arrLen];
        for(int i = 0; i < arrLen; i++){
            arr[i] = random.nextInt(-100000, 100000);
        }
    }

    public int[] partMin(int startIndex, int finishIndex){
        int minValue = Integer.MAX_VALUE;
        int minIndex = 0;
        for (int i = startIndex; i < finishIndex; i++) {
            if(arr[i] < minValue){
                minValue = arr[i];
                minIndex = i;
            }
        }
        int[] result = new int[2];
        result[0] = minValue;
        result[1] = minIndex;
        return result;
    }

    synchronized public void setMin(int value, int index) {
        if(value < minValue){
            minValue = value;
            minIndex = index;
        }
    }

    public Thread[] threadsMin(int num){
        Thread[] threads = new Thread[num];
        int partLen = arr.length/num;
        for (int i = 0; i < num-1; i++) {
            threads[i] = new Thread(new FindMinThread(i*partLen, (i+1)*partLen, this));
            threads[i].start();
        }
        threads[num-1] = new Thread(new FindMinThread((num-1)*partLen, arr.length-1, this));
        threads[num-1].start();
        return threads;
    }
}
