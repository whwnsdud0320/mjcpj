package shared;

import exception.OutOfStockException;
import java.sql.*;
import java.util.Map;

public class StockManager {
    public synchronized void checkAndDecreaseStock(Connection conn, Map<Integer, Integer> cartMap) 
            throws OutOfStockException, SQLException {
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int menuId = entry.getKey();
            int qty = entry.getValue();
            String sql = "SELECT S.STOCK_ID, S.ITEM_NAME, S.QUANTITY, R.USE_AMOUNT FROM RECIPE R " +
                         "JOIN STOCK S ON R.STOCK_ID = S.STOCK_ID WHERE R.MENU_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, menuId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String itemName = rs.getString("ITEM_NAME");
                        int required = rs.getInt("USE_AMOUNT") * qty;
                        int current = rs.getInt("QUANTITY");
                        if (current < required) {
                            throw new OutOfStockException("[" + itemName + "] 재고가 부족하여 결제할 수 없습니다.");
                        }
                    }
                }
            }
        }
        for (Map.Entry<Integer, Integer> entry : cartMap.entrySet()) {
            int menuId = entry.getKey();
            int qty = entry.getValue();
            String sql = "SELECT STOCK_ID, USE_AMOUNT FROM RECIPE WHERE MENU_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, menuId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int stockId = rs.getInt("STOCK_ID");
                        int required = rs.getInt("USE_AMOUNT") * qty;
                        String update = "UPDATE STOCK SET QUANTITY = QUANTITY - ?, LAST_UPDATE = NOW() WHERE STOCK_ID = ?";
                        try (PreparedStatement upd = conn.prepareStatement(update)) {
                            upd.setInt(1, required); upd.setInt(2, stockId); upd.executeUpdate();
                        }
                    }
                }
            }
        }
    }
}