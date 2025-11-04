package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.*;
import org.example.GUI.DeliverHP;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Kiosk {

    public static String[] BP = new String[5];
    private final String airport;
    public static OpenCVFrameGrabber grabber;
    public static CanvasFrame canvas;

    public enum InstructionMode {
        DROP_OFF,
        UNKNOWN, PICK_UP
    }
    public static InstructionMode useCase = null;

    public enum AirportVaildation{
        OKAY,
        INVALID_ORIGIN,
        INVALID_DESTINATION
    }
    public static AirportVaildation validation = null;

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
    public static InstructionMode useCaseIdentification(BoardingPass BP, Kiosk kiosk) {
        System.out.println("\n🧾 Use Case: Passenger Identification");
        System.out.println("Boarding Pass: " + BP.getBPNumber());
        System.out.println("Origin: " + BP.getOriginAirport());
        System.out.println("Destination: " + BP.getDestinationAirport());
        System.out.println("Passenger: " + BP.getPsgName());

        try {
            // PICK_UP if kiosk is origin
            if (kiosk.getAirport().equals(BP.getOriginAirport())) {
                System.out.println("Identified as PICK_UP");
                return InstructionMode.PICK_UP;
            }

            // DROP_OFF if kiosk is destination
            if (kiosk.getAirport().equals(BP.getDestinationAirport())) {
                if (!BPalreadyStored(BP)) {
                    return InstructionMode.UNKNOWN;
                } else {
                    System.out.println("Identified as DROP_OFF (already stored)");
                }
                return InstructionMode.DROP_OFF;
            }

        } catch (SQLException e) {
            System.err.println("Database error while checking if boarding pass is already stored.");
            e.printStackTrace();
            return InstructionMode.UNKNOWN;
        }

        // If neither origin nor destination matches
        System.err.println("ERROR: This boarding pass is invalid for this kiosk.");
        return InstructionMode.UNKNOWN;
    }

    public static boolean BPalreadyStored(BoardingPass BP) throws SQLException {

        ArrayList<Integer> BPNumbers = Database.getBPN();

        if (BPNumbers.contains(BP.getBPNumber())){
            return true;
        } else return false;
    }





    public void initTransition(BoardingPass BP){
        // ---  Database operations ---
        try {

            int BPN = BP.getBPNumber();
            String origin = BP.getOriginAirport();
            String destination = BP.getDestinationAirport();
            String passenger = BP.getPsgName();
            String fltNr = BP.getfltNr();

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
    public static AirportVaildation validateAirports(BoardingPass BP, Kiosk kiosk){
        // Validate origin
        if (!isValidAirport(BP.getOriginAirport())) {
            System.err.println("ERROR: Origin airport '" + BP.getOriginAirport() + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            //closeAndExit(grabber, canvas, 1);
            validation = AirportVaildation.INVALID_ORIGIN;
        }


        if (!isValidAirport(BP.getDestinationAirport())) {
            validation = AirportVaildation.INVALID_DESTINATION;
            System.err.println("ERROR: Origin airport '" + BP.getDestinationAirport() + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            //closeAndExit(grabber, canvas, 1);
        }

        validation = AirportVaildation.OKAY;

        return validation;
    }



    public static boolean validateOriginAirport(BoardingPass BP,
                                     OpenCVFrameGrabber grabber,
                                     CanvasFrame canvas){

        // Validate origin
        if (!isValidAirport(BP.getOriginAirport())) {
            System.err.println("ERROR: Origin airport '" + BP.getOriginAirport() + "' is NOT registered in the system.");
            System.err.println("Please check the boarding pass");
            //closeAndExit(grabber, canvas, 1);
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
            //closeAndExit(grabber, canvas, 1);
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

    // --- Main scanner ---
    //public static void main(String[] args) throws FrameGrabber.Exception {
        public static String[] QRScan() throws FrameGrabber.Exception {
            String[] data = new String[5];
            String[] badScan = new String[1];
            grabber = new OpenCVFrameGrabber(0);
            // Start webcam
            canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            grabber.start();


            canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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


    public String getAirport() { return airport; }


    public static boolean sufficientData(String[] lines){
        if (lines.length < 5) {
            System.err.println("PLEASE TRY AGAIN");
            return false;
        } else
            return true;
    }

    public static void pickUp(BoardingPass BP, Kiosk kiosk){

        Database.pickUp(BP.getBPNumber(), Database.getIDFromICAO(kiosk.getAirport()));
    }







    //BACKUP
    public static void BACKUP() throws FrameGrabber.Exception {
        //Kiosk kiosk = new Kiosk(3, "EKAH", 10, "Terminal 1 - Gate 5");

        // Start webcam
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
