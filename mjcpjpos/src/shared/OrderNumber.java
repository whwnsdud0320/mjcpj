package shared;
public class OrderNumber {
    private int currentNo = 1;
    public synchronized int getNextOrderNo() {
        if (currentNo > 999) currentNo = 1;
        return currentNo++;
    }
}