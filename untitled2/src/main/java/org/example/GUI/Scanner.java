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
import org.bytedeco.javacv.FrameGrabber;
import org.example.BoardingPass;
import org.example.Kiosk;

import java.net.URL;

public class Scanner extends Application {


    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {

        Kiosk kiosk = new Kiosk("EKBI");


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

            Arrow arrow = new Arrow(300, 300);
            BorderPane.setAlignment(arrow, Pos.CENTER);
            BorderPane.setMargin(arrow, new Insets(100));
            border.setBottom(arrow);

            //SLET
            arrow.setOnMousePressed(e -> {
                try {
                    Scene helloScene = Hello.createScene();
                    primaryStage.setScene(helloScene);
                } catch (FrameGrabber.Exception ex) {
                    ex.printStackTrace();
                }
            });



        Scene scene = new Scene(border, 1920, 1080);
        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();

        //QR scan and use case identification.
        new Thread(() -> {
            try {
                String[] data = Kiosk.QRScan(); // run QR scanning
                // After QRScan finishes, switch scene on the JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    try {
                        if(Kiosk.sufficientData(data)){

                            /////////parsing and creating object
                            int BPN = Integer.parseInt(data[0].trim());
                            String origin = data[1].trim();
                            String destination = data[2].trim();
                            String passenger = data[3].trim();
                            String fltNr = data[4];

                            BoardingPass boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);

                            if(Kiosk.validateOriginAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
                                if(Kiosk.validateDestinationAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
                                    switch(kiosk.useCaseIdentification(boardingPass)){

                                        case "pick-up" :
                                           kiosk.initTransition(data);
                                            Scene helloScene = Hello.createScene();
                                            primaryStage.setScene(helloScene);
                                            System.out.println("PICK UP CASE");
                                            break;
                                        case "drop-off" :
                                            System.out.println("DROP OFF CASE");
                                        break;
                                    }


                                } else {
                                    //bad Dest
                                    Scene helloScene = BadScan.createScene();
                                    primaryStage.setScene(helloScene);
                                }


                            } else {
                                //bad Origin
                                Scene helloScene = BadScan.createScene();
                                primaryStage.setScene(helloScene);
                            }


                        } else {
                            //Bad scan
                            Scene helloScene = BadScan.createScene();
                            primaryStage.setScene(helloScene);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();

    }
}
