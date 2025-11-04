package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.example.Kiosk;

import java.net.URL;

public class ErrorMessageOriginAirport {

    private static Text instructionLabel;

    public static Scene createScene() {

        BorderPane border = new BorderPane();



        CircelErrorIcon Error1 = new CircelErrorIcon(100, 100);
        Error1.setScaleY(0.8);
        Error1.setScaleX(0.8);


        Text title = new Text("Oops!");
        title.setFont(new Font(70));

        instructionLabel = new Text();
        Text message1 = instructionLabel;
        message1.setFont(new Font(40));
        Text message2 = new Text("Please scan a valid boarding pass.");
        message2.setFont(new Font(40));

        ImageView scannerView = null;
        URL scannerUrl = ErrorMessageOriginAirport.class.getResource("/Images/Scan.JPEG");
        if (scannerUrl != null) {
            scannerView = new ImageView(new Image(scannerUrl.toExternalForm()));
            scannerView.setFitWidth(200);
            scannerView.setPreserveRatio(true);
        } else {
            System.out.println("Scanner image not found!");
        }

        Arrow arrow = new Arrow(300, 300);
        BorderPane.setAlignment(arrow, Pos.CENTER);
        BorderPane.setMargin(arrow, new Insets(100));
        border.setBottom(arrow);


        VBox top = new VBox(Error1, title, message1, message2, scannerView);
        top.setAlignment(Pos.TOP_CENTER);
        top.setSpacing(5);
        border.setTop(top);

        setInstructionMode(Kiosk.validation);

        return new Scene(border, 1920, 1080);
    }

    public static void setInstructionMode(Kiosk.AirportVaildation mode) {
        switch (mode) {
            case INVALID_ORIGIN:
                instructionLabel.setText("The Origin airport is incorrect.");
                break;
            case INVALID_DESTINATION:
                instructionLabel.setText("The destination airport is not in our network.");
                break;
        }
    }
}
