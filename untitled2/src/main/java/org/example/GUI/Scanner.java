package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.bytedeco.javacv.FrameGrabber;

import java.net.URL;
import java.util.Stack;
import java.util.List;

public class Scanner extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();


        Text title = new Text("Kiosk");
        title.setFont(new Font(80));
        BorderPane.setMargin(title, new Insets(50));
        BorderPane.setAlignment(title, Pos.CENTER);
        border.setTop(title);


        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);

        Text please = new Text("Please scan your boarding pass");
        please.setFont(new Font(60));
        border.setCenter(centerContent);

        URL scannerUrl = getClass().getResource("/Images/Scan.JPEG");
        if (scannerUrl != null) {
            ImageView scannerView = new ImageView(new Image(scannerUrl.toExternalForm()));
            scannerView.setFitWidth(250);
            scannerView.setPreserveRatio(true);
            centerContent.getChildren().addAll(please, scannerView);

        } else {
            System.out.println("Scanner image not found!");
        }
        border.setCenter(centerContent);

        URL arrowUrl = getClass().getResource("/Images/Arrow.png");
        if (arrowUrl != null) {
            ImageView arrowView = new ImageView(new Image(arrowUrl.toExternalForm()));
            arrowView.setFitWidth(200);
            arrowView.setPreserveRatio(true);

            //Logic to switch to Hello.java
            Button switchButton = new Button("Go to Hello Scene");
            switchButton.setOnAction(e -> {
                try {
                    Scene helloScene = Hello.createScene();
                    primaryStage.setScene(helloScene); // primaryStage is your main stage
                } catch (FrameGrabber.Exception ex) {
                    ex.printStackTrace();
                }
            });

            StackPane arrowPane = new StackPane(arrowView);
            StackPane.setAlignment(arrowView, Pos.BOTTOM_CENTER);
            border.setBottom(arrowPane);
            BorderPane.setMargin(arrowPane, new Insets(30));
        } else {
            System.out.println("Arrow image not found!");
        }




        Scene scene = new Scene(border, 1920, 1080);
        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
