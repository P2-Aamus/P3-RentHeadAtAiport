package org.example;

<<<<<<< Updated upstream
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;


public class Database {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Airhead";
        String user = "root";
        String password = "Sodabobs123?";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a name to insert: ");
            String name = scanner.nextLine();

            String sql = "INSERT INTO Names (name) VALUES (?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, name);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection failed. Check your server and credentials.");
=======
import java.sql.*;

public class Database {
    private String url = "jdbc:mysql://localhost:3306/main_DB";
    private String user = "root";
    private String password = "alexale9";

    public static void connect() throws SQLException {
        try (Connection conn = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/main_DB",
                        "root", "alexale9")) {

            PreparedStatement selectStatement = conn.prepareStatement("select * from kiosk");
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) { // will traverse through all rows
                int id = rs.getInt("id");
                String firstName = rs.getString("airport");
                int lastName = rs.getInt("numOfAvailableHP");

                System.out.println(id + firstName + lastName);

            }

>>>>>>> Stashed changes
        }
    }
}