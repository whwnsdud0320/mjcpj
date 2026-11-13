package model;

public class Menu {
    private int menuId, categoryId, price;
    private String menuName, seasonType;
    private boolean isSoldOut;

    public Menu(int menuId, int categoryId, String menuName, int price, String seasonType, boolean isSoldOut) {
        this.menuId = menuId;
        this.categoryId = categoryId;
        this.menuName = menuName;
        this.price = price;
        this.seasonType = seasonType;
        this.isSoldOut = isSoldOut;
    }

    public int getMenuId() { return menuId; }
    public String getMenuName() { return menuName; }
    public int getPrice() { return price; }
    public boolean isSoldOut() { return isSoldOut; }
}