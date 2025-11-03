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
    private final String airport;
    public static OpenCVFrameGrabber grabber;
    public static CanvasFrame canvas;

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/main";
    private static final String user = "root";
    private static final String password = "alexale9";

    // Constructor
    public Kiosk(String airport) {
        this.airport = airport;
    }

    /**
     * Handles the entire identification use case:
     * - Validates both airports
     * - Inserts boarding pass
     * - Executes database workflow
     */
    public String useCaseIdentification(BoardingPass BP){
        String res = "";
        System.out.println("\n🧾 Use Case: Passenger Identification");
        System.out.println("Boarding Pass: " + BP.getBPNumber());
        System.out.println("Origin: " + BP.getOriginAirport());
        System.out.println("Destination: " + BP.getDestinationAirport());
        System.out.println("Passenger: " + BP.getPsgName());

        // --- Validates originAirport and destiinationAirport ---
        //if (!validateAirports(originAirport, destinationAirportOnPass, grabber, canvas)) {
        //    return false; // Errors are handled inside validateAirports()
        //}

        System.out.println("Airports verified successfully (" + BP.getOriginAirport() + " → " + BP.getDestinationAirport() + ")");

        //Add a check in the database for drop off
        if (this.getAirport().equals(BP.getOriginAirport())){res = "pick-up";}
        else if (this.getAirport().equals(BP.getDestinationAirport())) {res = "drop-off";}
        return res;
    }


    public void initTransition(String[] data){
        // ---  Database operations ---
        try {

            int BPN = Integer.parseInt(data[0].trim());
            String origin = data[1].trim();
            String destination = data[2].trim();
            String passenger = data[3].trim();
            String fltNr = data[4];

            Database.ins_BP(BPN, origin, destination, passenger, fltNr);
            Database.transactionStart(BPN, Database.getIDFromICAO(this.getAirport()));
            //Database.pickUp(boardingPassNumber, kioskLocation);
            //Database.dropOff(boardingPassNumber, kioskLocation);



            System.out.println(" Boarding pass processed successfully!");



        } catch (Exception e) {
            System.err.println(" ERROR: Failed to process boarding pass.");
            e.printStackTrace();
        }
    }

    /**
     * Validates the origin and destination of the airport.
     * Returns true if both are valid, otherwise prints error and the QR Scanner is closed.
     */
    public static boolean validateOriginAirport(BoardingPass BP,
                                     OpenCVFrameGrabber grabber,
                                     CanvasFrame canvas){

        // Validate origin
        if (!isValidAirport(BP.getOriginAirport())) {
            System.err.println("ERROR: Origin airport '" + BP.getOriginAirport() + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            closeAndExit(grabber, canvas, 1);
            return false;
        }
        return true;
    }
    public static boolean validateDestinationAirport(BoardingPass BP,
                                                OpenCVFrameGrabber grabber,
                                                CanvasFrame canvas){

        // Validate origin
        if (!isValidAirport(BP.getDestinationAirport())) {
            System.err.println("ERROR: Origin airport '" + BP.getDestinationAirport() + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            closeAndExit(grabber, canvas, 1);
            return false;
        }
        return true;
    }

    //Checks if a single airport exists in the kiosk table.
    private static boolean isValidAirport(String airportCode) {
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
    private static void closeAndExit(OpenCVFrameGrabber grabber, CanvasFrame canvas, int status) {
        try {
            if (grabber != null) grabber.stop();
            if (canvas != null) canvas.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {}
        //System.exit(status);
    }

    // --- Main scanner ---
    //public static void main(String[] args) throws FrameGrabber.Exception {
        public static String[] QRScan() throws FrameGrabber.Exception {
            String[] data = new String[5];
            String[] badScan = new String[1];
            grabber = new OpenCVFrameGrabber(0);
            // Start webcam
            canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            grabber.start();


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

                    data = lines;
                    BP = lines;

                    // Pass the scanned data to the use case handler
                    //kiosk.useCaseIdentification(BPN, origin, destination, passenger, fltNr, grabber, kiosk.getId(), canvas);
                    break;

                } catch (NotFoundException e) {
                    // No QR code found in this frame — continue scanning
                } catch (Exception e) {
                    e.printStackTrace();
                    grabber.stop();
                    canvas.dispose();
                    return badScan;
                }

                canvas.showImage(frame);
            }

            grabber.stop();
            canvas.dispose();
            return data;
        }

    // --- Getters ---
    //public int getId() { return id; }
    public String getAirport() { return airport; }
    //public int getNumOfAvailabeHP() { return numOfAvailabeHP; }
    //public String getLocationAtAirport() { return locationAtAirport; }

    public static String getBP(int i) {return BP[i];}

    public static boolean sufficientData(String[] lines){
        if (lines.length < 5) {
            System.err.println("PLEASE TRY AGAIN");
            return false;
        } else
            return true;
    }







    //BACKUP
    public static void BACKUP() throws FrameGrabber.Exception {
        //Kiosk kiosk = new Kiosk(3, "EKAH", 10, "Terminal 1 - Gate 5");

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
                //kiosk.useCaseIdentification(BPN, origin, destination, passenger, fltNr, grabber, kiosk.getId(), canvas);


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

}
