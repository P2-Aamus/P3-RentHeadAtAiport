package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Allows to create boarding pass objects
 */
public class BoardingPass{
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

    //Attributes
    /** Unique number for each boarding pass*/
     private int BPNumber;
     /** 4-letter ICAO code of the origin airport */
     private String originAirport;
    /** 4-letter ICAO code of the destination airport */
     private String destinationAirport;
    /** The name of the passenger */
     private String psgName;
    /** The flight number given on the boarding pass */
     private String fltNr;

     //constructor
    /** Constructs a boarding pass object out of the given parameters */
    public BoardingPass(int BPN, String ori, String dest, String name, String flt){
        BPNumber = BPN;
        originAirport = ori;
        destinationAirport = dest;
        psgName = name;
        fltNr = flt;
    }

    //methods

    /**
     * Gets the boarding pass number
     * @return Boarding pass number
     */
    public int getBPNumber(){
        return BPNumber;
    }

    /**
     * Gets the origin airport
     * @return Origin airport
     */
    public String getOriginAirport(){
        return originAirport;
    }

    /**
     * Gets the destination airport
     * @return Destination airport
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * Gets the passenger's name
     * @return Passenger's name
     */
    public String getPsgName() {
        return psgName;
    }

    /**
     * Gets the flight number
     * @return Flight number
     */
    public String getfltNr(){
        return fltNr;
    }

    /**
     * Inserts the boarding pass into the database
     */
    public void ins_BP() {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");

            int BPN = getBPNumber();
            String oa = getOriginAirport();
            String da = getDestinationAirport();
            String name = getPsgName();
            String flt_rn = getfltNr();

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

    /**
     * Fetches a list of all boarding passes in the database.
     * @return A list of all boarding passes in the database.
     */
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

    /**
     * Deletes a boarding pass from the database.
     */
    public void deleteLastBP() throws SQLException {
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");



            String sql1 = "DELETE FROM transactions WHERE BPN = ?";
            String sql2 = "DELETE FROM boarding_pass WHERE BPN = ?";

            try (PreparedStatement stmt1 = con.prepareStatement(sql1);
                 PreparedStatement stmt2 = con.prepareStatement(sql2)) {

                stmt1.setInt(1, getBPNumber());
                int rows1 = stmt1.executeUpdate();

                stmt2.setInt(1, getBPNumber());
                int rows2 = stmt2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}