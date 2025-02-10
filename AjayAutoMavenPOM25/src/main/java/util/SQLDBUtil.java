package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDBUtil {

    private static final String DB_URL = "jdbc:sqlserver://DESKTOP-RJ0D9O3:1433;databaseName=ajaymodels;encrypt=true;trustServerCertificate=true";

    private static final String DB_USER = "Aj_007";  // Replace with your SQL Server username
    private static final String DB_PASSWORD = "Ajay@8010505397";  // Replace with your SQL Server password

    /**
     * Fetch login credentials from the database.
     */
    public static Object[][] getLoginData() {
        String query = "SELECT userId, userPwd, url FROM login_data";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // Scrollable result set
             ResultSet rs = stmt.executeQuery(query)) {

            // Count rows
            rs.last(); 
            int rows = rs.getRow();
            rs.beforeFirst(); 

            Object[][] data = new Object[rows][3]; 
            int i = 0;
            
            while (rs.next()) {
                data[i][0] = rs.getString("userId");
                data[i][1] = rs.getString("userPwd");
                data[i][2] = rs.getString("url");
                i++;
            }

            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }
}