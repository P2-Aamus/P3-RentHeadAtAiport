package org.example.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import org.example.BoardingPass;
import org.example.Kiosk;

import java.net.URL;

/**
 * This UI page is an error message for the passenger if they have an invalid boarding pass.
 * The purpose of this page is if the scanner did not read the QR-code correctly
 */
public class PleaseTryAgainMessagePage {
    /**
     * The boarding pass object that the passenger scanned made into an attribute
     */
    static BoardingPass BP = UIManager.boardingPass;

    /**
     * Create scene with borders, vertical and horizontal boxes with a picture and an arrow object included
     * @return
     */
    public static Scene createScene() {

        UIManager.startScan();
        System.out.println("Validation result: " + Kiosk.validateAirports(BP));
        BorderPane border = new BorderPane();

        Text title = new Text("Please try again...");
        title.setFont(new Font(70));

        Text message1 = new Text("Your boarding pass is invalid.");
        message1.setFont(new Font(40));
        Text message2 = new Text("Please scan a valid boarding pass.");
        message2.setFont(new Font(40));

        URL scannerUrl = ScannerPage.class.getResource("/Images/image-scanner-barcode-qr-code-icon-others-dcafa04faf2cfff6510f74883562e3c6.png");
        ImageView scannerView = null;
        if (scannerUrl != null) {
            scannerView = new ImageView(new Image(scannerUrl.toExternalForm()));
            scannerView.setFitWidth(250);
            scannerView.setPreserveRatio(true);


            /**
             * VBox center content, contains messages and the ImageView
             */
            VBox centerContent = new VBox(message1, message2, scannerView);
            centerContent.setSpacing(50);
            centerContent.setAlignment(Pos.CENTER);
        } else {
            System.out.println("Scanner image not found!");
        }

        ArrowShape arrow = new ArrowShape();
        BorderPane.setAlignment(arrow, Pos.CENTER);
        BorderPane.setMargin(arrow, new Insets(100));
        border.setBottom(arrow);


        VBox top = new VBox(title);
        top.setSpacing(10);
        VBox.setMargin(title, new Insets(50, 0, 0, 0));
        top.setAlignment(Pos.TOP_CENTER);
        VBox center = new VBox(message1, message2, scannerView);
        center.setSpacing(15);
        center.setAlignment(Pos.CENTER);

        border.setTop(top);
        border.setCenter(center);

        return new Scene(border, 1920, 1080);
    }
}
