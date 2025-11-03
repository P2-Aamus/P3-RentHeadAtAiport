package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Kiosk;
import java.util.function.Supplier;

public class UIManager extends Application {

    public static BoardingPass boardingPass;
    public static Stage primaryStageRef;
    public static Kiosk kiosk = new Kiosk("EKBI");

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        primaryStageRef = primaryStage;

        primaryStage.setScene(Scanner.createScene());

        primaryStage.setTitle("AirHead");
        primaryStage.show();

    }



    public static void startScan(){
        //QR scan and use case identification.
        new Thread(() -> {

            Stage primaryStage = UIManager.primaryStageRef;

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

                            boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);

                            if(Kiosk.validateOriginAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
                                if(Kiosk.validateDestinationAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
                                    switch(kiosk.useCaseIdentification(boardingPass)){

                                        case "pick-up" :
                                            if(kiosk.BPalreadyStored(boardingPass)){
                                                Scene helloScene = BadScan.createScene();
                                                primaryStage.setScene(helloScene);

                                            } else {kiosk.initTransition(boardingPass);
                                                Scene helloScene = Hello.createScene();
                                                primaryStage.setScene(helloScene);}

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

    public static void changeScene(Supplier<Scene> sceneSupplier){
        Scene newScene = sceneSupplier.get();
        primaryStageRef.setScene(newScene);
    }

}
