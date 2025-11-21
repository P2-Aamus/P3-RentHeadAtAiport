package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import org.example.service.BoardingPassService;
import org.example.service.KioskService;
import org.example.service.HeadphoneService;
import org.example.service.TransactionService;

/**
 * This class sits between the GUI and the services and coordinates the workflow.
 */
public class Kiosk {

    public enum InstructionMode { DROP_OFF, UNKNOWN, PICK_UP }
    public enum AirportValidation { OKAY, INVALID_ORIGIN, INVALID_DESTINATION }

    private final String airportICAO;

    // services injected via constructor
    private final BoardingPassService bpService;
    private final KioskService kioskService;
    private final HeadphoneService hpService;
    private final TransactionService txService;

    // frame grabber / canvas are instance fields now
    private OpenCVFrameGrabber grabber;
    private CanvasFrame canvas;

    public Kiosk(String airportICAO,
                 BoardingPassService bpService,
                 KioskService kioskService,
                 HeadphoneService hpService,
                 TransactionService txService) {

        this.airportICAO = airportICAO;
        this.bpService = bpService;
        this.kioskService = kioskService;
        this.hpService = hpService;
        this.txService = txService;
    }

    public String getAirport() { return airportICAO; }

    /**
     * Determines whether this kiosk should PICK_UP or DROP_OFF a boarding pass.
     */
    public InstructionMode useCaseIdentification(BoardingPass bp) {
        System.out.println("Use Case: Passenger Identification");
        System.out.println("Boarding Pass: " + bp.getBPNumber());
        System.out.println("Origin: " + bp.getOriginAirport());
        System.out.println("Destination: " + bp.getDestinationAirport());
        System.out.println("Passenger: " + bp.getPsgName());

        try {
            if (this.airportICAO.equals(bp.getOriginAirport())) {
                System.out.println("Identified as PICK_UP");
                return InstructionMode.PICK_UP;
            }

            if (this.airportICAO.equals(bp.getDestinationAirport())) {
                if (!bpService.exists(bp)) {
                    return InstructionMode.UNKNOWN;
                } else {
                    System.out.println("Identified as DROP_OFF (already stored)");
                }
                return InstructionMode.DROP_OFF;
            }
        } catch (Exception e) {
            System.err.println("Database error while checking if boarding pass is already stored.");
            e.printStackTrace();
            return InstructionMode.UNKNOWN;
        }

        System.err.println("ERROR: This boarding pass is invalid for this kiosk.");
        return InstructionMode.UNKNOWN;
    }

    public AirportValidation validateAirports(BoardingPass bp) {
        // Validate origin using the new Service method
        if (!kioskService.isValidAirport(bp.getOriginAirport())) {
            System.err.println("ERROR: Origin airport '" + bp.getOriginAirport() + "' is NOT registered.");
            return AirportValidation.INVALID_ORIGIN;
        }
        // Validate destination using the new Service method
        if (!kioskService.isValidAirport(bp.getDestinationAirport())) {
            System.err.println("ERROR: Destination airport '" + bp.getDestinationAirport() + "' is NOT registered.");
            return AirportValidation.INVALID_DESTINATION;
        }
        return AirportValidation.OKAY;
    }

    /**
     * Initialize transition (store BP + start transaction)
     */
    public void initTransition(BoardingPass bp) {
        kioskService.beginTransition(bp, this.airportICAO);
        System.out.println("Boarding pass processed successfully!");
    }

    /**
     * Start pick up workflow for a boarding pass at this kiosk.
     */
    public void pickUp(BoardingPass bp) {
        kioskService.pickUp(bp, this.airportICAO);
    }

    /**
     * Drop off workflow
     */
    public void dropOff(BoardingPass bp) {
        kioskService.dropOff(bp, this.airportICAO);
    }

    /**
     * QR scanning as an instance method — returns raw lines extracted from QR,
     * same format you used previously (String[] of lines).
     */
    public String[] QRScan() throws FrameGrabber.Exception {
        String[] data = new String[0];
        grabber = new OpenCVFrameGrabber(0);
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
                break;

            } catch (NotFoundException e) {
                // No QR in frame, continue
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            canvas.showImage(frame);
        }

        try {
            if (grabber != null) grabber.stop();
        } catch (FrameGrabber.Exception ignored) {}
        if (canvas != null) canvas.dispose();

        return data;
    }

    public static boolean sufficientData(String[] lines) {
        return lines != null && lines.length >= 5;
    }
}