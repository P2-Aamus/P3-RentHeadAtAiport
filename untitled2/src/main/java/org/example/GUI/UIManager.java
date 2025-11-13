package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Database;
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

    public static void startScan() {
        // QR scan and use case identification
        new Thread(() -> {
            Stage primaryStage = UIManager.primaryStageRef;

            try {
                String[] data = Kiosk.QRScan(); // run QR scanning

                javafx.application.Platform.runLater(() -> {
                    try {
                        if (!Kiosk.sufficientData(data)) {
                            System.err.println("Bad scan: insufficient QR data");
                            changeScene(ErrorPleaseTryAgainMessage::createScene);
                            return;
                        }

                        // Parse boarding pass
                        int BPN = Integer.parseInt(data[0].trim());
                        String origin = data[1].trim();
                        String destination = data[2].trim();
                        String passenger = data[3].trim();
                        String fltNr = data[4].trim();

                        boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);

                        switch (Kiosk.validateAirports(boardingPass)) {
                            case INVALID_ORIGIN -> changeScene(ErrorMessageOriginAirport::createScene);
                            case INVALID_DESTINATION -> changeScene(ErrorMessageOriginAirport::createScene);
                            case OKAY -> {
                                Kiosk.InstructionMode mode = kiosk.useCaseIdentification(boardingPass, kiosk);

                                if (mode == null) {
                                    System.err.println("ERROR: useCaseIdentification returned null for boarding pass: " + BPN);
                                    changeScene(ErrorPleaseTryAgainMessage::createScene);
                                    return;
                                }

                                switch (mode) {
                                    case PICK_UP -> {
                                        if (kiosk.BPalreadyStored(boardingPass)) {
                                            ErrorMessageOriginAirport.message = "You have already picked up a pair of headphones.";
                                            changeScene(ErrorMessageOriginAirport::createScene);

                                        } else {
                                            kiosk.initTransition(boardingPass);
                                            changeScene(HelloHard::createScene);
                                        }
                                        System.out.println("PICK UP CASE");
                                    }
                                    case DROP_OFF -> {
                                        System.out.println("DROP OFF CASE");
                                        if (!kiosk.BPalreadyStored(boardingPass)) {
                                            ErrorMessageOriginAirport.message = "who are you?????????";
                                            changeScene(ErrorMessageOriginAirport::createScene);
                                        } else {
                                            Database.dropOff(boardingPass.getBPNumber(), Database.getIDFromICAO(kiosk.getAirport()));
                                            changeScene(HelloHard::createScene);
                                        }
                                    }

                                    case UNKNOWN -> {
                                        changeScene(ErrorPleaseTryAgainMessage::createScene) ;}
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        changeScene(ErrorPleaseTryAgainMessage::createScene);
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
