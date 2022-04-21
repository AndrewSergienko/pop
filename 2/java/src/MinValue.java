public class MinValue {
    int value = Integer.MAX_VALUE;

    public synchronized void set(int value){
        this.value = Math.min(this.value, value);
    }
}
