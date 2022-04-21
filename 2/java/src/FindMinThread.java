public class FindMinThread implements Runnable {
    int[] array;
    MinValue minValue;
    public FindMinThread(int[] array, MinValue minValue){
        this.array = array;
        this.minValue = minValue;
    }

    @Override
    public void run() {
        int min = Integer.MAX_VALUE;
        for (int j : array) {
            min = Math.min(j, min);
        }
        minValue.set(min);
    }
}
