package util;	

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.util.ArrayList;
	import java.util.List;

	public class MySqlDBUtil {

	    private static final String DB_URL = "jdbc:mysql://localhost:3306/world";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "root";

	    /**
	     * Fetches login credentials from the database.
	     * @return A 2D Object array for TestNG DataProvider.
	     */
	    public static Object[][] getDatabaseTestData() {
	        List<Object[]> data = new ArrayList<>();
	        try {
	            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT userId, userPwd, url FROM login_data");

	            while (rs.next()) {
	                String userId = rs.getString("userId");
	                String userPwd = rs.getString("userPwd");
	                String url = rs.getString("url");
	                data.add(new Object[]{userId, userPwd, url});
	            }

	            rs.close();
	            stmt.close();
	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Database connection failed: " + e.getMessage());
	        }

	        return data.toArray(new Object[0][]);
	    }
	}

