import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static void main(String[] args) {
        String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7800571";
        String username = "sql7800571";
        String password = "j3C8juCVPI";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            if (conn != null) {
                System.out.println("Connected to the remote database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}