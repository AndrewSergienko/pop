public class MinValue {
    int value = Integer.MAX_VALUE;
    boolean blocked = false;

    public synchronized void set(int value){
        try{
            if(blocked){
                wait();
            }
        }
        catch (InterruptedException ignored) {
        }
        blocked = true;
        this.value = Math.min(this.value, value);
        blocked = false;
        notify();
    }
}
