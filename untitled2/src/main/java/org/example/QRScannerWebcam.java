import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

public class QRScannerWebcam {
    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        while (true) {
            BufferedImage image = webcam.getImage();
            if (image == null) continue;

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new MultiFormatReader().decode(bitmap);
                System.out.println("Scanned: " + result.getText());
                break; // stop after first successful scan
            } catch (NotFoundException e) {
                // keep looping until something is found
            }
        }

        webcam.close();
    }
}
