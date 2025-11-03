package org.example.GUI;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.net.URL;

public class ErrorMessageWrongAirportDesination extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane border = new BorderPane();



        CircelErrorIcon Error1 = new CircelErrorIcon(100, 100);
        Error1.setScaleY(0.8);
        Error1.setScaleX(0.8);


        Text title = new Text("Oops!");
        title.setFont(new Font(70));

        Text message1 = new Text("The destination airport is not in our network.");
        message1.setFont(new Font(40));
        Text message2 = new Text("Please scan a valid boarding pass.");
        message2.setFont(new Font(40));

        ImageView scannerView = null;
        URL scannerUrl = getClass().getResource("/Images/Scan.JPEG");
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



        Scene scene = new Scene(border, 1920, 1080);
        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
