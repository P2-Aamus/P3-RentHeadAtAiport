package org.example;

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
        }
    }
}