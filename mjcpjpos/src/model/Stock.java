package model;

public class Stock {
    private int stockId, quantity, safeStock;
    private String itemName, category, unit;

    public Stock(int stockId, String itemName, String category, int quantity, int safeStock, String unit) {
        this.stockId = stockId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.safeStock = safeStock;
        this.unit = unit;
    }

    public int getStockId() { return stockId; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public int getSafeStock() { return safeStock; }
}