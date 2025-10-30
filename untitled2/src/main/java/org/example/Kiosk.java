package org.example;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacv.*;

import java.awt.image.BufferedImage;

public class Kiosk {

    private final int id;
    private final String airport;
    public int numOfAvailabeHP;
    private final String locationAtAirport;

    //Constructor
    public Kiosk(int id, String airport, int numOfAvailabeHP, String locationAtAirport) {
        this.id = id;
        this.airport = airport;
        this.numOfAvailabeHP = numOfAvailabeHP;
        this.locationAtAirport = locationAtAirport;
    }

    public static String scanQRCode() throws FrameGrabber.Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        Java2DFrameConverter converter = new Java2DFrameConverter();
        String qrResult = null;

        while (canvas.isVisible() && qrResult == null) {
            Frame frame = grabber.grab();
            if (frame == null) continue;

            BufferedImage img = converter.getBufferedImage(frame);

            try {
                LuminanceSource source = new BufferedImageLuminanceSource(img);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);

                qrResult = result.getText();
                System.out.println("QR Code detected: " + qrResult);
                String[] lines = qrResult.split("\\R"); // \R handles any line break

                int BPN = Integer.parseInt(lines[0].trim());

                Database.ins_BP(BPN, lines[1], lines[2], lines[3], lines[4]);
                Database.transactionStart(BPN, 2);
                Database.pickUp(BPN, 2);
                Database.dropOff(BPN, 1);




            } catch (NotFoundException e) {
                // No QR code found in this frame; keep looping
            }

            canvas.showImage(frame);
        }

        grabber.stop();
        canvas.dispose();

        return qrResult;
    }



    //Getters and Setters methods
    public int getId() {
        return id;
    }

    public String getAirport() {

        return airport;
    }

    public int getNumOfAvailabeHP() {

        return numOfAvailabeHP;
    }
    public String getLocationAtAirport() {

        return locationAtAirport;
    }




}




    // Attributes



