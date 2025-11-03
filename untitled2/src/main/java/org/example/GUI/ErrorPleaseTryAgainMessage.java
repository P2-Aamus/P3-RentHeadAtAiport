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

public class ErrorPleaseTryAgainMessage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane border = new BorderPane();



        Text title = new Text("Please try again...");
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


        VBox top = new VBox(title);
        top.setSpacing(10);
        VBox.setMargin(title, new Insets(50, 0, 0, 0));
        top.setAlignment(Pos.TOP_CENTER);
        VBox center = new VBox(message1, message2, scannerView);
        center.setSpacing(15);
        center.setAlignment(Pos.CENTER);

        border.setTop(top);
        border.setCenter(center);



        Scene scene = new Scene(border, 1920, 1080);
        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
