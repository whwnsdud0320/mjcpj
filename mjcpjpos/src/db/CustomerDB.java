package db;

import model.Customer;
import java.sql.*;

public class CustomerDB {
    public Customer getCustomer(String p1, String p2, String p3) {
        String sql = "SELECT * FROM CUSTOMER WHERE PHONE_1 = ? AND PHONE_2 = ? AND PHONE_3 = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p1); pstmt.setString(2, p2); pstmt.setString(3, p3);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getInt("CUST_ID"), rs.getString("PHONE_1"), rs.getString("PHONE_2"),
                            rs.getString("PHONE_3"), rs.getString("NAME"), rs.getInt("STAMP_COUNT"));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}