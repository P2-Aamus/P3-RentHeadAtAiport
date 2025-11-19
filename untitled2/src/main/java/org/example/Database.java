package org.example;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * The class Database allows the system to comunicate with the database
 * @author Group 3
 */

public class Database {
    Kiosk kiosk = new Kiosk("EKBI");
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

    /**
     * Finds an available pair of headphones, and registers them as taken. It also updates the transaction and kiosk tables accordingly.
     * @param BPN is the unique boarding pass number.
     * @param kioskID is the ID of the kiosk the user is interacting with.
     */
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

    /**
     * Registers the headphones as dropped off and charging. It also updates the transaction and kiosk tables accordingly.
     * @param BPN is the unique boarding pass number.
     * @param kioskID is the ID of the kiosk the user is interacting with.
     */
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
}