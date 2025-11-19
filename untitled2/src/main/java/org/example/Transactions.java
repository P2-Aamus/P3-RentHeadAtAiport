package org.example;

import Admin.GUI.Database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class Transaction {

    private static String url = null;
    private static String user = null;
    private static String password = null;

    static {
        Properties props = new Properties();
        try (InputStream input = Database.class.getClassLoader().getResourceAsStream("config.properties")) {
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

    private final int BPN;
    private int HP_ID;
    private int originKioskID
    private int destKioskID;
    private int status;

    private Transaction(int BPN, int HP_ID, int origKioskID, int destKioskID, int status) {
        this.HP_ID = HP_ID;
        this.BPN = BPN;
        this.originKioskID = origKioskID;
        this.destKioskID = destKioskID;
        this.status = status;
    }

    public int getBPN(){
        return BPN;
    }

    public void setHP_id(int newHP_id){
        this.HP_ID = newHP_id;
    }

    public int getHP_ID(){
        return HP_ID;
    }

    public void setOriginKioskID(int newOriginKioskID){
        this.originKioskID = newOriginKioskID;
    }

    public int getOriginKioskID(){
        return originKioskID;
    }

    public void setDestKioskID(int newDestKioskID){
        this.destKioskID = newDestKioskID;
    }

    public int getDestKioskID(){
        return destKioskID;
    }

    public void setStatus(int newStatus){
        this.status = newStatus;
    }

    public int getStatus(){
        return status;
    }


    public static Transaction createTransactionObject(int BPN) throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)){
            String query = "SELECT BPN, headphonesID, originKioskID, destKioskID, status FROM transactions WHERE BPN = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, BPN);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Transaction(
                        rs.getInt("BPN"),
                        rs.getInt("HP_ID"),
                        rs.getInt("originKioskID"),
                        rs.getInt("destKioskID"),
                        rs.getInt("status"));

            } else {
                System.out.println("No boarding pass number was found"+ BPN);
                return null;
            }
        }
    }


    public void updateDestKioskID() throws SQLException{
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE transactions SET destKioskID = ? WHERE BPN = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, this.destKioskID);
            stmt.setInt(2, this.BPN);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Destination updated succesfully");
            }
        }
        }

        public void updateStatus() throws SQLException{
            try (Connection con = DriverManager.getConnection(url, user, password)) {
                String query = "UPDATE transactions SET status = ? WHERE BPN = ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, this.status);
                stmt.setInt(2, this.BPN);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Status updated succesfully");
                }
            }
            }

            public void updateHP_ID() throws SQLException{
                try (Connection con = DriverManager.getConnection(url, user, password)) {
                    String query = "UPDATE transactions SET HP_ID = ? WHERE BPN = ?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setInt(1, this.HP_ID);
                    stmt.setInt(2, this.BPN);
                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Headphone ID updated succesfully :D");
                    }
                }
                }





}


