package shared;
import java.util.LinkedList;
import java.util.Queue;

public class CallBoard {
    private Queue<Integer> queue = new LinkedList<>();
    public synchronized void callOrder(int orderNo) { queue.offer(orderNo); notifyAll(); }
    public synchronized int getCallOrder() {
        while (queue.isEmpty()) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return -1; }
        }
        int no = queue.poll(); notifyAll(); return no;
    }
}