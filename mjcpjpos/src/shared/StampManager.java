package shared;
import java.sql.*;

public class StampManager {
    public synchronized void addStamps(Connection conn, int custId, int count) throws SQLException {
        String sql = "UPDATE CUSTOMER SET STAMP_COUNT = STAMP_COUNT + ? WHERE CUST_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, count); pstmt.setInt(2, custId); pstmt.executeUpdate();
        }
    }
}