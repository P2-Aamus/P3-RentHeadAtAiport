package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import org.bytedeco.javacv.FrameGrabber;
import org.example.Kiosk;
import javax.swing.*;
import java.net.URL;
import javafx.geometry.Insets;



public class Scanner extends Application{

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();


        Text title = new Text(875, 123, "AirHead");
        title.setFont(new Font(50));
        border.setTop(title);

        Text please = new Text(875, 223, "Please scan your boarding pass");
        please.setFont(new Font(50));
        border.setCenter(please);

        URL scannerImage = Scanner.class.getResource("/images/scan.JPEG");
        //border.setCenter(scannerImage);
        //BorderPane.setMargin(scannerImage, new Insets(5));


        URL arrow = Scanner.class.getResource("/images/arrow.png");
        //border.setBottom(arrow);
        //BorderPane.setMargin(arrow, new Insets(5));

        Group root = new Group(title, please);
        Scene scene = new Scene(root, 1920, 1080);

        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Run QR scanning in a background thread
        new Thread(() -> {
            try {
                String qrData = "";  // This blocks until a QR code is found
                if (qrData != null) {
                    System.out.println("QR Data: " + qrData);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
