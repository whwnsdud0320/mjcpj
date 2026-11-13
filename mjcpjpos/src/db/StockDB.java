package db;

import model.Stock;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockDB {
    public List<Stock> getAllStocks() {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM STOCK";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Stock(rs.getInt("STOCK_ID"), rs.getString("ITEM_NAME"),
                        rs.getString("CATEGORY"), rs.getInt("QUANTITY"),
                        rs.getInt("SAFE_STOCK"), rs.getString("UNIT")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}