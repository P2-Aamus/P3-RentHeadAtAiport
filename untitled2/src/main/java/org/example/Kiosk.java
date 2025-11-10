package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;

/**
 * This class sits between the GUI and the database and connects the two.
 */
public class Kiosk {

    /** An array of strings that contain the data to create a boarding pass object */
    public static String[] BP = new String[5];

    /** The location of the kiosk, formatted as a 4-letter ICAO code */
    private final String airport;

    /** TOBIAS HJÆLP */
    public static OpenCVFrameGrabber grabber;

    /** TOBIAS HJÆLP */
    public static CanvasFrame canvas;

    /** Used to define some use cases */
    public enum InstructionMode {
        DROP_OFF,
        UNKNOWN, PICK_UP
    }

    /** Defines the possible outcomes of the airport validation logic */
    public enum AirportVaildation{
        OKAY,
        INVALID_ORIGIN,
        INVALID_DESTINATION
    }

    /** sets the validation to null as a starting value */
    public static AirportVaildation validation = null;

    // Constructor

    /**
     * Allows to construct a kiosk object
     * @param airport The location of the kiosk, formatted as a 4-letter ICAO code
     */
    public Kiosk(String airport) {
        this.airport = airport;
    }

    /**
     * Handles the entire identification use case:
     * - Validates both airports
     * - Inserts boarding pass
     * - Executes database workflow
     * @param BP The boarding pass object
     * @param kiosk The kiosk object
     * @return 
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

            Database.ins_BP(BP);
            Database.transactionStart(BP.getBPNumber(), Database.getIDFromICAO(this.getAirport()));
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
    public static AirportVaildation validateAirports(BoardingPass BP) {
        // Validate origin
        if (!Database.isValidAirport(BP.getOriginAirport())) {
            System.err.println("ERROR: Origin airport '" + BP.getOriginAirport() + "' is NOT registered in the system.");
            validation = AirportVaildation.INVALID_ORIGIN;
            return validation;
        }

        // Validate destination
        if (!Database.isValidAirport(BP.getDestinationAirport())) {
            System.err.println("ERROR: Destination airport '" + BP.getDestinationAirport() + "' is NOT registered in the system.");
            validation = AirportVaildation.INVALID_DESTINATION;
            return validation;
        }

        // ✅ Only OKAY if both are valid
        validation = AirportVaildation.OKAY;
        return validation;
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
