package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Properties;
import org.example.Headphones;

public class Transactions {

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

    public enum transactionID {
        TRANSACTION_START, TRANSACTION_UPDATE, TRANSACTION_END
    }

    public Headphones headphoneID;

    public BoardingPass boardingPassNumber;

    public String originKiosk;

    public String destinationKiosk;

    public int status;


    private Transactions(int statusID, BoardingPass BPN, String oriKiosk, String destKiosk) {
        this.status = statusID;
        this.boardingPassNumber = BPN;
        this.originKiosk = oriKiosk;
        this.destinationKiosk = destKiosk;
    }


    public void setHeadphoneID(Headphones headphoneID) {
        this.headphoneID = headphoneID;
    }

    public int setStatus() {
        return status;
    }

    public BoardingPass getBoardingPassNumber() {
        return boardingPassNumber;
    }

    public String getOriginKiosk() {
        return originKiosk;
    }

    public String getDestinationKiosk() {
        return destinationKiosk;
    }


    public void updateDatabase() throws SQLException {
        this.updateDatabaseStatus();
        this.updateDatabaseLocation();
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


        }
