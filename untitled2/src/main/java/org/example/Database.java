package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;


public class Database {
    private static String url = "jdbc:mysql://localhost:3306/main_DB";
    private static String user = "root";
    private static String password = "alexale9";

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
        }
    }

    public static void ins_BP(int BPN, String oa, String da, String name, String flt_rn) {


        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

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

            String sql = "INSERT INTO transactions (BPNumber, originKioskID, status) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, BPN);
                pstmt.setInt(2, kioskID);
                pstmt.setInt(3, 0);

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

    public static void transactionStatusUpdate(int BPN){
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            String sql = "UPDATE transactions SET status = 1 WHERE BPNumber =" + BPN;
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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

    public static void pickUp(int BPN, int kioskID){
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int availableHP = 0;
            String loc = "";
            int HP_ID = 0;

            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM kiosk WHERE ID =" + kioskID);
            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) { // will traverse through all rows
                availableHP = rs.getInt("numOfAvailableHP");
                loc = rs.getString("airport");
            }

            if(availableHP != 0){
                //Find a pair of headphones in the kiosk
                PreparedStatement selectStatement2 = con.prepareStatement("SELECT * FROM headphones WHERE location ='" + loc + "' & status = 1");
                ResultSet rs2 = selectStatement2.executeQuery();

                while (rs2.next()) { // will traverse through all rows
                    HP_ID = rs2.getInt("ID");
                }

                //Update NumAvailableHP
                String sql5 = "UPDATE kiosk SET numOfAvailableHP = numOfAvailableHP - 1 WHERE ID =" + kioskID;
                try (PreparedStatement pstmt = con.prepareStatement(sql5)) {
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name inserted successfully!");
                    }
                }

                //Update HP status
                String sql = "UPDATE headphones SET status = 2 WHERE ID =" + HP_ID;
                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name inserted successfully!");
                    }
                }

                //Update HP location
                String sql2 = "UPDATE headphones SET location = NULL WHERE ID =" + HP_ID;
                try (PreparedStatement pstmt = con.prepareStatement(sql2)) {
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name inserted successfully!");
                    }
                }

                //Update transaction status
                String sql3 = "UPDATE transactions SET status = 1 WHERE BPNumber =" + BPN;
                try (PreparedStatement pstmt = con.prepareStatement(sql3)) {
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name inserted successfully!");
                    }
                }

                //Insert transaction HP
                String sql4 = "UPDATE transactions SET headphonesID = "+ HP_ID +" WHERE BPNumber =" + BPN;
                try (PreparedStatement pstmt = con.prepareStatement(sql4)) {
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Name inserted successfully!");
                    }
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
            String sql = "UPDATE transactions SET destKioskID ='"+kioskID+"' WHERE BPNumber =" + BPN;
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Name inserted successfully!");
                }
            }

            //get HP_ID from transaction
            PreparedStatement selectStatement = con.prepareStatement("SELECT * FROM transactions WHERE BPNumber =" + BPN);
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