package db;

import java.sql.*;

public class SellDB {
    public boolean insertSell(Connection conn, Integer staffId, Integer custId, int orderNo, 
                              String orderType, int totalPrice, int cardAmount, int cashAmount, int pointAmount) {
        String sql = "INSERT INTO SELL (SELL_TIME, STAFF_ID, CUST_ID, ORDER_NO, ORDER_TYPE, " +
                     "TOTAL_PRICE, CARD_AMOUNT, CASH_AMOUNT, POINT_AMOUNT, PAY_STATUS, ORDER_STATUS) " +
                     "VALUES (NOW(), ?, ?, ?, ?, ?, ?, ?, ?, '승인', '대기')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (staffId == null) pstmt.setNull(1, Types.INTEGER); else pstmt.setInt(1, staffId);
            if (custId == null) pstmt.setNull(2, Types.INTEGER); else pstmt.setInt(2, custId);
            pstmt.setInt(3, orderNo);
            pstmt.setString(4, orderType);
            pstmt.setInt(5, totalPrice);
            pstmt.setInt(6, cardAmount);
            pstmt.setInt(7, cashAmount);
            pstmt.setInt(8, pointAmount);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}