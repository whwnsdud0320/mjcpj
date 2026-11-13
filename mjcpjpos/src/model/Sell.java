package model;

public class Sell {
    private int sellId;
    private String sellTime;
    private Integer staffId, custId;
    private int orderNo;
    private String orderType;
    private int totalPrice, cardAmount, cashAmount, pointAmount;
    private String payStatus, orderStatus;

    public Sell(int sellId, String sellTime, Integer staffId, Integer custId, int orderNo, String orderType,
                int totalPrice, int cardAmount, int cashAmount, int pointAmount, String payStatus, String orderStatus) {
        this.sellId = sellId;
        this.sellTime = sellTime;
        this.staffId = staffId;
        this.custId = custId;
        this.orderNo = orderNo;
        this.orderType = orderType;
        this.totalPrice = totalPrice;
        this.cardAmount = cardAmount;
        this.cashAmount = cashAmount;
        this.pointAmount = pointAmount;
        this.payStatus = payStatus;
        this.orderStatus = orderStatus;
    }

    public int getSellId() { return sellId; }
    public int getOrderNo() { return orderNo; }
    public int getTotalPrice() { return totalPrice; }
}