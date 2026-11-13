package db;

import model.Staff;
import java.sql.*;

public class StaffDB {
    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM STAFF WHERE STAFF_ID = ? AND STATUS = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, staffId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getInt("STAFF_ID"), rs.getString("NAME"), rs.getString("PASSWORD"),
                        rs.getString("PHONE"), rs.getString("ROLE"), rs.getInt("HOURLY_WAGE"), rs.getInt("STATUS")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}