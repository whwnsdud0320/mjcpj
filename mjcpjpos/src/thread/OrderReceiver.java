package thread;

import shared.OrderBuffer;
import shared.OrderInfo;

public class OrderReceiver extends Thread {
    private OrderBuffer buffer;

    public OrderReceiver(OrderBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            OrderInfo order = buffer.getOrder();
            if (order != null) {
                System.out.println("POS 주문 수신: " + order.orderNo);
            }
        }
    }
}