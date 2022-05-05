public class FindMinThread implements Runnable {
    private int startIndex, endIndex;
    private MinClass minClass;

    public FindMinThread(int startIndex, int endIndex, MinClass minClass){
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.minClass = minClass;
    }

    @Override
    public void run() {
        int[] min = minClass.partMin(startIndex, endIndex);
        minClass.setMin(min[0], min[1]);
    }
}
