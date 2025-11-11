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
 * This UI page is dedicated to the passenger, if they have a boarding pass contains either a wrong
 * origin destination or the boarding pass contains a destination that does not have a kiosk.
 */
public class ErrorMessageOriginAirportPage {

    /**
     * The boarding pass object made into an attribute
     */
    static BoardingPass BP = UIManager.boardingPass;
    private static Text instructionLabel;
    public static String message;

    /**
     * @return a scene with borders, vertical and horizontal boxes with an icon and an image
     */
    public static Scene createScene() {
        UIManager.startScan();
        BorderPane border = new BorderPane();

        setInstructionMode();

        CircelErrorIcon Error1 = new CircelErrorIcon(100, 100);
        Error1.setScaleY(0.8);
        Error1.setScaleX(0.8);


        Text title = new Text("Oops!");
        title.setFont(new Font(70));

        instructionLabel = new Text();
        Text message1 = new Text(message);
        message1.setFont(new Font(40));
        Text message2 = new Text("Please scan a valid boarding pass.");
        message2.setFont(new Font(40));

        URL scannerUrl = ScannerPage.class.getResource("/Images/image-scanner-barcode-qr-code-icon-others-dcafa04faf2cfff6510f74883562e3c6.png");
        if (scannerUrl != null) {
            ImageView scannerView = new ImageView(new Image(scannerUrl.toExternalForm()));
            scannerView.setFitWidth(250);
            scannerView.setPreserveRatio(true);


            /**
             * VBox centercontent, contains messages and ImageView
             */
            VBox centerContent = new VBox(message1, message2, scannerView);
            centerContent.setSpacing(50);
            centerContent.setAlignment(Pos.CENTER);


        ArrowShape arrow = new ArrowShape();
        BorderPane.setAlignment(arrow, Pos.CENTER);
        BorderPane.setMargin(arrow, new Insets(100));
        border.setBottom(arrow);


        VBox top = new VBox(Error1, title, message1, message2, scannerView);
        top.setAlignment(Pos.TOP_CENTER);
        top.setSpacing(5);
        border.setTop(top);



    } return new Scene(border, 1920, 1080);}


    /**
     * Switch case that uses the boarding pass object to determine if the passengers boarding pass
     * contains the wrong origin airport or a destination that is does not have a kiosk
     */
    public static void setInstructionMode() {
        switch (Kiosk.validateAirports(BP)) {
            case INVALID_ORIGIN:
                instructionLabel.setText("The origin airport is incorrect.");
                break;
            case INVALID_DESTINATION:
                instructionLabel.setText("The destination airport is not in our network.");
                break;
        }
    }
}
