package shared;

import java.util.LinkedList;
import java.util.Queue;

public class OrderBuffer {
    private Queue<OrderInfo> queue = new LinkedList<>();
    private final int MAX_SIZE = 50;

    public synchronized void putOrder(OrderInfo order) {
        while (queue.size() >= MAX_SIZE) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        queue.offer(order);
        notifyAll();
    }

    public synchronized OrderInfo getOrder() {
        while (queue.isEmpty()) {
            try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); return null; }
        }
        OrderInfo order = queue.poll();
        notifyAll();
        return order;
    }
}