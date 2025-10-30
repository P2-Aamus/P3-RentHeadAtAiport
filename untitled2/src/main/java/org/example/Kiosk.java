package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.*;
import org.example.GUI.Hello;

import java.awt.image.BufferedImage;
import java.sql.*;

public class Kiosk {

    public static String[] BP = new String[5];
    private final int id;
    private final String airport;
    public int numOfAvailabeHP;
    private final String locationAtAirport;

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/main";
    private static final String user = "root";
    private static final String password = "drkabellsvej3";

    // Constructor
    public Kiosk(int id, String airport, int numOfAvailabeHP, String locationAtAirport) {
        this.id = id;
        this.airport = airport;
        this.numOfAvailabeHP = numOfAvailabeHP;
        this.locationAtAirport = locationAtAirport;
    }

    /**
     * Handles the entire identification use case:
     * - Validates both airports
     * - Inserts boarding pass
     * - Executes database workflow
     */
    public void useCaseIdentification(int boardingPassNumber,
                                      String originAirport,
                                      String destinationAirportOnPass,
                                      String passengerName,
                                      String fltNr,
                                      OpenCVFrameGrabber grabber,
                                      int kioskLocation,
                                      CanvasFrame canvas){

        System.out.println("\n🧾 Use Case: Passenger Identification");
        System.out.println("Boarding Pass: " + boardingPassNumber);
        System.out.println("Origin: " + originAirport);
        System.out.println("Destination: " + destinationAirportOnPass);
        System.out.println("Passenger: " + passengerName);

        // --- Validates originAirport and destiinationAirport ---
        if (!validateAirports(originAirport, destinationAirportOnPass, grabber, canvas)) {
            return; // Errors are handled inside validateAirports()
        }

        System.out.println(" Airports verified successfully (" + originAirport + " → " + destinationAirportOnPass + ")");

        // ---  Database operations ---
        try {
            Database.ins_BP(boardingPassNumber, originAirport, destinationAirportOnPass, passengerName, fltNr);
            Database.transactionStart(boardingPassNumber, kioskLocation);
            //Database.pickUp(boardingPassNumber, kioskLocation);
            //Database.dropOff(boardingPassNumber, kioskLocation);

            BP[0] = ""+ boardingPassNumber;
            BP[1] = originAirport;
            BP[2] = destinationAirportOnPass;
            BP[3] = passengerName;
            BP[4] = fltNr;

            System.out.println(" Boarding pass processed successfully!");
            closeAndExit(grabber, canvas, 0);

        } catch (Exception e) {
            System.err.println(" ERROR: Failed to process boarding pass.");
            e.printStackTrace();
            closeAndExit(grabber, canvas, 1);
        }
    }

    /**
     * Validates the origin and destination of the airport.
     * Returns true if both are valid, otherwise prints error and the QR Scanner is closed.
     */
    private boolean validateAirports(String originAirport,
                                     String destinationAirport,
                                     OpenCVFrameGrabber grabber,
                                     CanvasFrame canvas){

        // Validate origin
        if (!isValidAirport(originAirport)) {
            System.err.println("ERROR: Origin airport '" + originAirport + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            closeAndExit(grabber, canvas, 1);
            return false;
        }

        // Validate destination
        if (!isValidAirport(destinationAirport)) {
            System.err.println(" ERROR: Destination airport '" + destinationAirport + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            closeAndExit(grabber, canvas, 1);
            return false;
        }

        return true;
    }

    //Checks if a single airport exists in the kiosk table.
    private boolean isValidAirport(String airportCode) {
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

    // Closes webcam and exits QR-SCAN
    private void closeAndExit(OpenCVFrameGrabber grabber, CanvasFrame canvas, int status) {
        try {
            if (grabber != null) grabber.stop();
            if (canvas != null) canvas.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {}
        System.exit(status);
    }

    // --- Main scanner ---
    public static void main(String[] args) throws FrameGrabber.Exception {
        Kiosk kiosk = new Kiosk(3, "EKAH", 10, "Terminal 1 - Gate 5");

        // Start webcam
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        while (canvas.isVisible()) {
            Frame frame = grabber.grab();
            if (frame == null) continue;

            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage img = converter.getBufferedImage(frame);

            try {
                LuminanceSource source = new BufferedImageLuminanceSource(img);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);

                String scannedText = result.getText();
                String[] lines = scannedText.split("\\R");

                if (lines.length < 4) {
                    System.err.println("PLEASE TRY AGAIN");
                    continue;
                }

                int BPN = Integer.parseInt(lines[0].trim());
                String origin = lines[1].trim();
                String destination = lines[2].trim();
                String passenger = lines[3].trim();
                String fltNr = lines[4];


                // Pass the scanned data to the use case handler
                kiosk.useCaseIdentification(BPN, origin, destination, passenger, fltNr, grabber, kiosk.getId(), canvas);

            } catch (NotFoundException e) {
                // No QR code found in this frame — continue scanning
            } catch (Exception e) {
                e.printStackTrace();
            }

            canvas.showImage(frame);
        }

        grabber.stop();
        canvas.dispose();
    }

    // --- Getters ---
    public int getId() { return id; }
    public String getAirport() { return airport; }
    public int getNumOfAvailabeHP() { return numOfAvailabeHP; }
    public String getLocationAtAirport() { return locationAtAirport; }

    public static String getBP(int i) {
        return BP[i];
    }

}
