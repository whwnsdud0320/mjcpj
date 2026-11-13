package db;

import model.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDB {
    public List<Menu> getMenuList(int categoryId) {
        List<Menu> list = new ArrayList<>();
        String sql = "SELECT * FROM MENU WHERE CATEGORY_ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Menu(rs.getInt("MENU_ID"), rs.getInt("CATEGORY_ID"),
                            rs.getString("MENU_NAME"), rs.getInt("PRICE"),
                            rs.getString("SEASON_TYPE"), rs.getBoolean("IS_SOLD_OUT")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}