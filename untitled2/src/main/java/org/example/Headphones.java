package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Properties;


public class Headphones {
    /**
     * These are the credentials for accessing the database. They are stored in another file for safe keeping.
     */
    private static String url = null;
    private static String user = null;
    private static String password = null;

    static {
        Properties props = new Properties();
        try (InputStream input = Admin.GUI.Database.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("config.properties not found in resources");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database configuration", e);
        }

        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
    }

    private final int HP_ID;
    private int status;
    private String location;
    private int battery;

    private Headphones(int HP_ID, int status, String location, int battery) {
        this.HP_ID = HP_ID;
        this.status = status;
        this.location = location;
        this.battery = battery;
    }

    public int getHP_ID() {
        return HP_ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int newStatus) {
        this.status = newStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int newBattery) {
        this.battery = newBattery;
    }


    public static Headphones fetchHeadphones(int HP_ID) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT ID, status, location, battery FROM headphones WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, HP_ID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Headphones(
                        rs.getInt("ID"),
                        rs.getInt("status"),
                        rs.getString("location"),
                        rs.getInt("battery"));
            } else {
                System.out.println("No headphones were found with the ID: " + HP_ID);
                return null;
            }
        }
    }

    public void updateDatabase() throws SQLException {
        this.updateDatabaseStatus();
        this.updateDatabaseLocation();
        this.updateDatabaseBattery();
    }

    public void updateDatabaseBattery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            //Update status
            String sql = "UPDATE headphones SET battery = ? WHERE ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, this.battery);
                pstmt.setInt(2, this.HP_ID);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Battery updated successfully!");
                }
            }
        }
    }

    public void updateDatabaseStatus() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            //Update status
            String sql = "UPDATE headphones SET status = ? WHERE ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, this.status);
                pstmt.setInt(2, this.HP_ID);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Status updated successfully!");
                }
            }
        }
    }

    public void updateDatabaseLocation() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            //Update status
            String sql = "UPDATE headphones SET location = ? WHERE ID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, this.location);
                pstmt.setInt(2, this.HP_ID);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Location updated successfully!");
                }
            }
        }
    }
}
