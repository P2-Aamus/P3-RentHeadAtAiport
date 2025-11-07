package org.example;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class Database {
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


    public static void ins_BP(BoardingPass BP) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int BPN = BP.getBPNumber();
            String oa = BP.getOriginAirport();
            String da = BP.getDestinationAirport();
            String name = BP.getPsgName();
            String flt_rn = BP.getfltNr();

            String sql = "INSERT INTO boarding_pass (BPN, origin_airport, dest_airport, psg_name, flt_nr) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, BPN);
                pstmt.setString(2, oa);
                pstmt.setString(3, da);
                pstmt.setString(4, name);
                pstmt.setString(5, flt_rn);
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

    public static void transactionStart (int BPN, int kioskID){

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            String sql = "INSERT INTO transactions (BPN, originKioskID, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, BPN);
                pstmt.setInt(2, kioskID);
                pstmt.setInt(3, 0);
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Transaction inserted successfully!");
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection failed. Check your server and credentials.");

        }
    }

    public static void pickUp(int BPN, int kioskID){
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int availableHP = 0;
            String loc = "";
            int HP_ID = 0;

            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM kiosk WHERE ID = ?");
            selectStatement.setInt(1, kioskID);
            ResultSet rs = selectStatement.executeQuery();
            while (rs.next()) { // will traverse through all rows
                availableHP = rs.getInt("numOfAvailableHP");
                loc = rs.getString("airport");
            }

            if(availableHP != 0){
                //Find a pair of headphones in the kiosk
                PreparedStatement selectStatement2 = con.prepareStatement("SELECT ID FROM headphones WHERE location = ? AND status = 1 LIMIT 1");
                selectStatement2.setString(1, loc);
                ResultSet rs2 = selectStatement2.executeQuery();
                if(rs2.next()) {
                    HP_ID = rs2.getInt("ID");
                } else {
                    System.err.println("No headphones available at this kiosk!");
                    return; // or handle error
                }

                //Update NumAvailableHP
                PreparedStatement selectStatement3 = con.prepareStatement("UPDATE kiosk SET numOfAvailableHP = numOfAvailableHP - 1 WHERE ID = ?");
                selectStatement3.setInt(1, kioskID);
                int rowsInserted3 = selectStatement3.executeUpdate();
                if (rowsInserted3 > 0) {
                    System.out.println("NumAvailableHP updated!");
                }


                //Update HP status
                PreparedStatement selectStatement4 = con.prepareStatement("UPDATE headphones SET status = 2 WHERE ID = ?");
                selectStatement4.setInt(1, HP_ID);
                int rowsInserted4 = selectStatement4.executeUpdate();
                if (rowsInserted4 > 0) {
                    System.out.println("HP status updated to 2 (in use)!");
                }

                //Update HP location
                PreparedStatement selectStatement5 = con.prepareStatement("UPDATE headphones SET location = NULL WHERE ID = ?");
                selectStatement5.setInt(1, HP_ID);
                int rowsInserted5 = selectStatement5.executeUpdate();
                if (rowsInserted5 > 0) {
                    System.out.println("HP location updated to NULL!");
                }

                //Update transaction status
                PreparedStatement selectStatement6 = con.prepareStatement("UPDATE transactions SET status = 1 WHERE BPN = ?");
                selectStatement6.setInt(1, BPN);
                int rowsInserted6 = selectStatement6.executeUpdate();
                if (rowsInserted6 > 0) {
                    System.out.println("Transaction status updated to 1 (closed)!");
                }

                //Insert transaction HP
                PreparedStatement selectStatement7 = con.prepareStatement("UPDATE transactions SET headphonesID = ? WHERE BPN = ?");
                selectStatement7.setInt(1, HP_ID);
                selectStatement7.setInt(2, BPN);
                int rowsInserted7 = selectStatement7.executeUpdate();
                if (rowsInserted7 > 0) {
                    System.out.println("HP ID inserted into transaction!");
                }

            } else {
                //ERROR
            }






        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection failed. Check your server and credentials.");

        }
    }

    public static void dropOff(int BPN, int kioskID){
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int HP_ID = 0;
            String loc = "";

            //Update transaction kiosk
            String sql = "UPDATE transactions SET destKioskID ='"+kioskID+"' WHERE BPN =" + BPN;
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //get HP_ID from transaction
            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM transactions WHERE BPN =" + BPN);
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) { // will traverse through all rows
                HP_ID = rs.getInt("headphonesID");
            }

           //get kiosk loc
            PreparedStatement selectStatement2 = con.prepareStatement("SELECT * FROM kiosk WHERE ID =" + kioskID);
            ResultSet rs2 = selectStatement2.executeQuery();

            while (rs2.next()) { // will traverse through all rows
                loc = rs2.getString("airport");
            }

            //Update HP location
            String sql2 = "UPDATE headphones SET location ='"+loc+"' WHERE ID =" + HP_ID;
            try (PreparedStatement pstmt = con.prepareStatement(sql2)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //Update HP battery
            String sql3 = "UPDATE headphones SET battery = 25 WHERE ID =" + HP_ID;
            try (PreparedStatement pstmt = con.prepareStatement(sql3)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //Update HP status
            String sql4 = "UPDATE headphones SET status = 0 WHERE ID =" + HP_ID;
            try (PreparedStatement pstmt = con.prepareStatement(sql4)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //Update HP loc
            String sql6 = "UPDATE headphones SET location ='"+loc+"'WHERE ID =" + HP_ID;
            try (PreparedStatement pstmt = con.prepareStatement(sql6)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //Add HP to kiosk
            String sql5 = "UPDATE kiosk SET numOfAvailableHP = numOfAvailableHP +1  WHERE ID =" + kioskID;
            try (PreparedStatement pstmt = con.prepareStatement(sql5)) {
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


    public static String getNameFromICAO(String ICAO) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            String fullName = "";

            String sql = "SELECT * FROM kiosk WHERE airport = ?";
            try (PreparedStatement selectStatement = con.prepareStatement(sql)) {
                selectStatement.setString(1, ICAO);
                try (ResultSet rs = selectStatement.executeQuery()) {
                    while (rs.next()) {
                        fullName = rs.getString("airport_name");
                    }
                }
            }
            return fullName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getIDFromICAO(String ICAO) {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int fullName = 0;

            String sql = "SELECT * FROM kiosk WHERE airport = ?";
            try (PreparedStatement selectStatement = con.prepareStatement(sql)) {
                selectStatement.setString(1, ICAO);
                try (ResultSet rs = selectStatement.executeQuery()) {
                    while (rs.next()) {
                        fullName = rs.getInt("ID");
                    }
                }
            }
            return fullName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Integer> getBPN() throws SQLException {
        ArrayList<Integer> BPNArray = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

        String sql = "SELECT BPN FROM boarding_pass";
        try (PreparedStatement selectStatement = con.prepareStatement(sql);
             ResultSet rs = selectStatement.executeQuery()) {
            while (rs.next()) {
                BPNArray.add(rs.getInt("BPN"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
        return BPNArray;
    }

    public static void deleteLastBP(BoardingPass BP) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int BPN = BP.getBPNumber();

            String sql1 = "DELETE FROM transactions WHERE BPN = ?";
            String sql2 = "DELETE FROM boarding_pass WHERE BPN = ?";

            try (PreparedStatement stmt1 = con.prepareStatement(sql1);
                 PreparedStatement stmt2 = con.prepareStatement(sql2)) {

                stmt1.setInt(1, BPN);
                int rows1 = stmt1.executeUpdate();

                stmt2.setInt(1, BPN);
                int rows2 = stmt2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Checks if a single airport exists in the kiosk table.
    static boolean isValidAirport(String airportCode) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement("SELECT airport FROM kiosk WHERE airport = ?")) {

            ps.setString(1, airportCode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Database error while validating airport: " + airportCode);
            e.printStackTrace();
            return false;
        }
    }


}