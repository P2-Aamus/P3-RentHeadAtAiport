package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import org.bytedeco.javacv.FrameGrabber;
import org.example.BoardingPass;
import org.example.Kiosk;
import org.opencv.aruco.Board;

import java.net.URL;


public class Hello extends Application{

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) throws FrameGrabber.Exception {

// de skal alle sammen erstattes med objekter når det spiller, hvis ikke forkortelserne giver sig selv så bab klunk
        Text title = new Text(875, 623, "AirHead");
        title.setFont(new Font(50));

        Text please = new Text(875, 523, "Hello");
        please.setFont(new Font(60));

        Text passengerName = new Text(875, 423, "Antonio");
        passengerName.setFont(new Font(50));

        Text flightNumber = new Text(375, 323, "SK3913");
        flightNumber.setFont(new Font(50));

        Text originAir = new Text(450, 323, "EKCH");
        originAir.setFont(new Font(45));
        // understående skal kobles sammen med overstående på en eller anden måde
        Text originFull = new Text(450, 223, "Copenhagen");
        originFull.setFont(new Font(30));

        Text destAir = new Text(650, 323, "ENBR");
        destAir.setFont(new Font(45));

        Text destFull = new Text(650, 223, "Bergen");
        destFull.setFont(new Font(30));

        URL SAS = Hello.class.getResource("/SAS Logo.png");
        border.setCenter(SAS);
        BorderPane.setMargin(SAS, new Insets(5));

        BorderPane topBar = new BorderPane();




        Group root = new Group(title, please);



        Scene scene = new Scene(root, 1920, 1080);

        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
