import java.sql.*;

public class DBConnection {

    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                    "root",
                    "alianjum888@"   // apna password yahan likho
            );

        } catch (Exception e) {
            System.out.println("✗ Driver Error: " + e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
                System.out.println("✓ Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        Connection test = getConnection();
        try {
            return (test != null && !test.isClosed());
        } catch (SQLException e) {
            return false;
        }
    }
}