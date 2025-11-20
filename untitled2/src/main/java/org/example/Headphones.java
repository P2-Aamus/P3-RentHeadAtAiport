package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Headphones {

    private static String url;
    private static String user;
    private static String password;

    static {
        Properties props = new Properties();
        try (InputStream input = Admin.GUI.Database.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IOException("config.properties not found");
            }
            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        url = props.getProperty("db.url");
        user = props.getProperty("db.user");
        password = props.getProperty("db.password");
    }

    private static Headphones instance;

    public static Headphones getInstance() {
        if (instance == null) {
            try {
                instance = loadFirstHeadphones();
            } catch (SQLException e) {
                throw new RuntimeException("Unable to lazy-load first headphones row", e);
            }
        }
        return instance;
    }

    private static Headphones loadFirstHeadphones() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String query = "SELECT ID, status, location, battery FROM headphones ORDER BY ID LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Headphones(
                        rs.getInt("ID"),
                        rs.getInt("status"),
                        rs.getString("location"),
                        rs.getInt("battery")
                );
            } else {
                throw new SQLException("No headphone rows exist in the database.");
            }
        }
    }

    public static Headphones fetchHeadphones(int HP_ID) throws SQLException {
        if (instance != null) {
            return instance;
        }

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String query = "SELECT ID, status, location, battery FROM headphones WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, HP_ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                instance = new Headphones(
                        rs.getInt("ID"),
                        rs.getInt("status"),
                        rs.getString("location"),
                        rs.getInt("battery")
                );
                return instance;
            } else {
                System.out.println("No headphones found with ID: " + HP_ID);
                return null;
            }
        }
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

    public int getHP_ID() { return HP_ID; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getBattery() { return battery; }
    public void setBattery(int battery) { this.battery = battery; }

    public void updateDatabase() throws SQLException {
        updateDatabaseStatus();
        updateDatabaseLocation();
        updateDatabaseBattery();
    }

    private void updateDatabaseStatus() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE headphones SET status = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.status);
            pstmt.setInt(2, this.HP_ID);
            pstmt.executeUpdate();
        }
    }

    private void updateDatabaseLocation() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE headphones SET location = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, this.location);
            pstmt.setInt(2, this.HP_ID);
            pstmt.executeUpdate();
        }
    }

    private void updateDatabaseBattery() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE headphones SET battery = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, this.battery);
            pstmt.setInt(2, this.HP_ID);
            pstmt.executeUpdate();
        }
    }
}
