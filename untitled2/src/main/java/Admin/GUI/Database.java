package Admin.GUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/main";
    private static final String USER = "root";
    private static final String PASS = "alexale9";


    public static List<String[]> getAllBoardingPasses() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT BPN, origin_airport, dest_airport, psg_name, flt_nr FROM boarding_pass";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[5];
                row[0] = String.valueOf(rs.getInt("BPN"));
                row[1] = rs.getString("origin_airport");
                row[2] = rs.getString("dest_airport");
                row[3] = rs.getString("psg_name");
                row[4] = rs.getString("flt_nr");
                data.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching boarding pass data: " + e.getMessage());
        }

        return data;
    }


    public static List<String[]> getKioskData() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT ID, Airport, numOfAvailableHP, airport_name FROM kiosk";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("ID"));
                row[1] = rs.getString("Airport");
                row[2] = rs.getString("numOfAvailableHP");
                row[3] = rs.getString("airport_name");
                data.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching kiosk data: " + e.getMessage());
        }

        return data;
    }

    public static List<String[]> getTransactions() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT BPN, headphonesID, originKioskID, destKioskID, status FROM transactions";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[5];
                row[0] = String.valueOf(rs.getInt("BPN"));
                row[1] = rs.getString("headphonesID");
                row[2] = rs.getString("originKioskID");
                row[3] = rs.getString("destKioskID");
                row[4] = rs.getString("status");
                data.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching kiosk data: " + e.getMessage());
        }

        return data;
    }

    public static List<String[]> getHeadphonesData() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT ID, status, location, battery FROM headphones";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = String.valueOf(rs.getInt("ID"));
                row[1] = rs.getString("status");
                row[2] = rs.getString("location");
                row[3] = rs.getString("battery");
                data.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching kiosk data: " + e.getMessage());
        }

        return data;
    }
}





