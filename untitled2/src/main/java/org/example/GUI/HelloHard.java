package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// Fjern evt. unødvendige imports for at rydde op

import java.net.URL;

public class HelloHard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane border = new BorderPane();

        CircelCheckmarkIcon check = new CircelCheckmarkIcon(500, 500, 500);
        check.setScaleX(0.9);
        check.setScaleY(0.9);

        Text helloText = new Text("Hello!");
        helloText.setFont(Font.font(75));
        
        Text nameText = new Text("Name Lastname");
        nameText.setFont(Font.font(50));


        // Reads SAS-logo and flightnumber, puts logo next to text
        HBox airlineHBox = new HBox();
        airlineHBox.setSpacing(7);
        airlineHBox.setAlignment(Pos.CENTER_LEFT);

        URL SAS = getClass().getResource("/images/Scandinavian_Airlines_logo.svg.png");
        ImageView sasView = null;
        if (SAS != null) {
            sasView = new ImageView(new Image(SAS.toExternalForm()));
            sasView.setFitHeight(40);
            sasView.setPreserveRatio(true);
            airlineHBox.getChildren().add(sasView);
        }

        Text flightNumber = new Text("SK2864");
        flightNumber.setFont(Font.font(40));
        airlineHBox.getChildren().add(flightNumber);

        Text originCode = new Text("CPH/EKCH");
        originCode.setFont(Font.font(40));

        Text originFull = new Text("Copenhagen KastrupKastrupKastrup");
        originFull.setFont(Font.font(25));

        Text destCode = new Text("BGO/ENBR");
        destCode.setFont(Font.font( 40));

        Text destFull = new Text("Bergen Flesland");
        destFull.setFont(Font.font( 25));

        VBox originBox = new VBox(originCode, originFull);
        originBox.setAlignment(Pos.CENTER);

        VBox destBox = new VBox(destCode, destFull);
        destBox.setAlignment(Pos.CENTER);


        // Unicode pil → \u2794
        Text arrow = new Text("\u2794");
        arrow.setFont(Font.font(100));

        // Set flight
        HBox flightRoute = new HBox();
        flightRoute.setSpacing(23);
        flightRoute.setAlignment(Pos.CENTER);
        flightRoute.getChildren().addAll(airlineHBox, originBox, arrow, destBox);


        // Center all content
        VBox centerContent = new VBox(20, check, helloText, nameText, flightRoute);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(72, 50, 110, 50));
        border.setCenter(centerContent);

        Scene scene = new Scene(border, 1920, 1080);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Flight Info");
        primaryStage.show();
    }
}
