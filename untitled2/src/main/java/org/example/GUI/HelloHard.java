package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.bytedeco.flycapture.FlyCapture2.Format7ImageSettings;
import org.example.Database;
import org.example.Kiosk;

import java.net.URL;

public class HelloHard extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
// de skal alle sammen erstattes med objekter når det spiller

        BorderPane border = new BorderPane();

        Text title = new Text("AirHead");
        title.setFont(new Font("Impact",50));

        title.setFill(Color.AQUAMARINE);


        BorderPane.setAlignment(title, Pos.CENTER);
        border.setTop(title);




        Text please = new Text("Hello");
        please.setFont(new Font(60));





        Text passengerName = new Text("Dr gasia");
        passengerName.setFont(new Font(50));

        VBox centerContentPassenger = new VBox(20);
        centerContentPassenger.setAlignment(Pos.CENTER);

        Text flightNumber = new Text("SK2864");
        flightNumber.setFont(new Font(45));

        Text originAir = new Text("CPH/EKCH");
        originAir.setFont(new Font(45));

        Text originFull = new Text("Copenhagen Kastrup");
        originFull.setFont(new Font(30));

        Text destAir = new Text("BGO/ENBR");
        destAir.setFont(new Font(45));

        Text destFull = new Text("Bergen Flesland");
        destFull.setFont(new Font(30));

        Arrow arrow1 = new Arrow(150, 150);
        arrow1.setScaleX(0.6);
        arrow1.setScaleY(0.6);
        arrow1.setRotate(270);

        URL SAS = getClass().getResource("/images/SAS Logo.png");
        if (SAS != null) {
            ImageView sasView = new ImageView(new Image(SAS.toExternalForm()));
            sasView.setFitWidth(250);
            sasView.setPreserveRatio(true);
        } else {
            System.out.println("SAS image could not be rendered");
        }

        //boxes
        HBox topair = new HBox(flightNumber, originAir,arrow1, destAir);
        HBox lowair = new HBox(originFull, destFull);
        VBox flightInfo = new VBox( topair, lowair);
        VBox centerContent = new VBox(please, passengerName, flightInfo);
        border.setCenter(centerContent);

        centerContent.setPadding(new Insets(300, 500, 50, 500));
        flightInfo.setAlignment(Pos.TOP_CENTER);
        flightInfo.setSpacing(30);
        topair.setSpacing(30);
        topair.setPadding(new Insets(0,70,0,0));
        lowair.setSpacing(30);
        topair.setAlignment(Pos.CENTER);
        lowair.setAlignment(Pos.CENTER);

        centerContent.setAlignment(Pos.CENTER);



        Scene scene= new Scene(border, 1920, 1080);

        primaryStage.setScene(scene);
        primaryStage.setTitle("yo mama");
        primaryStage.show();
    }
}
