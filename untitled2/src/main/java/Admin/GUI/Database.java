package Admin.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;

public class Database {

    /**
     * These are the static attributes from the config.properties file from the database
     * that contains the database URL, the user and password.
     */
    private static String URL = null;
    private static String USER = null;
    private static String PASS = null;

    /**
     * The properties are then made into an object that gets loads the contents that are attached to
     * the config file
     */
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

        URL = props.getProperty("db.url");
        USER = props.getProperty("db.user");
        PASS = props.getProperty("db.password");
    }


    /**
     *
     * @return the method that fetches the boarding pass data form the database
     */
    public static List<String[]> getAllBoardingPasses() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT BPN, origin_airport, dest_airport, psg_name, flt_nr FROM boarding_pass";

        /**
         * Fetches the boarding pass table from the database in a try-catch
         */
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            /**
             * Adds the fetched data in a while loop into the rows
             */
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

        /**
         * Fetches the kiosk table from the database in a try-catch
         */
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            /**
             * Adds the fetched data in a while loop into the rows
             */
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

        /**
         * Fetches the transaction table from the database in a try-catch
         */
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            /**
             * Adds the fetched data in a while loop into the rows
             */
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

        /**
         * Fetches the headphone table from the database in a try-catch
         */
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            /**
             * Adds the fetched data in a while loop into the rows
             */
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





