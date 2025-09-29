import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgproc;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import java.awt.image.BufferedImage;

public class QRScannerWebcam {

    public static void main(String[] args) throws FrameGrabber.Exception {

        // Open default webcam
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();

        CanvasFrame canvas = new CanvasFrame("QR Scanner", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        while (true) {
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
                System.out.println("QR Code detected: " + result.getText());
            } catch (NotFoundException e) {
                // No QR code in this frame
            }

            // Show the frame
            //canvas.showImage(frame);
        }

        //grabber.stop();
        //canvas.dispose();
    }
}