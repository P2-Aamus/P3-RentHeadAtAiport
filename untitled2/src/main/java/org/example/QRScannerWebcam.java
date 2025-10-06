package org.example;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgproc;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import java.awt.image.BufferedImage;
import java.sql.*;

public class QRScannerWebcam {

    public static void main(String[] args) throws FrameGrabber.Exception {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/Airhead";
        String user = "root";
        String password = "Sodabobs123?";

        // Open default webcam
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        while (canvas.isVisible()) {
            Frame frame = grabber.grab();
            if (frame == null) continue;

            // Convert Frame to BufferedImage
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage img = converter.getBufferedImage(frame);

            // Decode QR code
            try {
                LuminanceSource source = new BufferedImageLuminanceSource(img);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);

                String scannedName = result.getText();
                System.out.println("QR Code detected: " + scannedName);

                // Insert scanned name into database
                try (Connection con = DriverManager.getConnection(url, user, password)) {
                    String sql = "INSERT INTO Names (name) VALUES (?)";
                    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                        pstmt.setString(1, scannedName);
                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Scanned name inserted into database: " + scannedName);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Database insert failed.");
                }

                // Exit after successful scan
                System.exit(0);

            } catch (NotFoundException e) {
                // No QR code in this frame
            }

            // Show the frame
            canvas.showImage(frame);
        }

        grabber.stop();
        canvas.dispose();
    }
}
