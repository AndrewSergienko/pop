public class FindMinThread implements Runnable {
    private int startIndex, endIndex;
    private int[] array;

    public FindMinThread(int startIndex, int endIndex, int[] array){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.array = array;
    }

    @Override
    public void run() {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = startIndex; i < endIndex; i++) {
            if(array[i] < min){
                min = array[i];
                index = i;
            }
        }
        Main.setMin(min, index);
    }
}
