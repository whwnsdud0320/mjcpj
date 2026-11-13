package model;

public class SellDetail {
    private int detailId, sellId, menuId, quantity, unitPrice, subTotal, shotCount;
    private String temp, cupSize;

    public SellDetail(int detailId, int sellId, int menuId, int quantity, int unitPrice, int subTotal, String temp, String cupSize, int shotCount) {
        this.detailId = detailId;
        this.sellId = sellId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
        this.temp = temp;
        this.cupSize = cupSize;
        this.shotCount = shotCount;
    }
}