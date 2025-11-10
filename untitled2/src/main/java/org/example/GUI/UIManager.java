package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Database;
import org.example.Kiosk;
import java.util.function.Supplier;


/**
 * This class is the manager to all the UI in our system
 */

public class UIManager extends Application {

    /**
     * attributes
     */
    public static BoardingPass boardingPass;
    public static Stage primaryStageRef;
    public static Kiosk kiosk = new Kiosk("EKBI");

    public static void main(String[] args) {
        launch(args);
    }

    /**
     *   set the stage
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStageRef = primaryStage;

        primaryStage.setScene(ScannerPage.createScene());

        primaryStage.setTitle("AirHead");
        primaryStage.show();

    }

    public static void startScan() {
        /**
         * QR scan and use case identification
          */
        new Thread(() -> {
            Stage primaryStage = UIManager.primaryStageRef;

            try {
                String[] data = Kiosk.QRScan(); // run QR scanning

                javafx.application.Platform.runLater(() -> {
                    try {
                        if (!Kiosk.sufficientData(data)) {
                            System.err.println("Bad scan: insufficient QR data");
                            changeScene(ErrorPleaseTryAgainMessagePage::createScene);
                            return;
                        }

                        // Parse boarding pass
                        int BPN = Integer.parseInt(data[0].trim());
                        String origin = data[1].trim();
                        String destination = data[2].trim();
                        String passenger = data[3].trim();
                        String fltNr = data[4].trim();

                        boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);

                        /**
                         *  switch case that determines the different cases a passenger
                         *  has depending on their boarding pass object
                         */
                        switch (Kiosk.validateAirports(boardingPass)) {
                            case INVALID_ORIGIN -> changeScene(ErrorMessageOriginAirportPage::createScene);
                            case INVALID_DESTINATION -> changeScene(ErrorMessageOriginAirportPage::createScene);
                            case OKAY -> {
                                Kiosk.InstructionMode mode = kiosk.useCaseIdentification(boardingPass, kiosk);

                                if (mode == null) {
                                    System.err.println("ERROR: useCaseIdentification returned null for boarding pass: " + BPN);
                                    changeScene(ErrorPleaseTryAgainMessagePage::createScene);
                                    return;
                                }
                                /**
                                 * pick up case
                                 */
                                switch (mode) {
                                    case PICK_UP -> {
                                        if (kiosk.BPalreadyStored(boardingPass)) {
                                            ErrorMessageOriginAirportPage.message = "You have already picked up a pair of headphones.";
                                            changeScene(ErrorMessageOriginAirportPage::createScene);

                                        } else {
                                            kiosk.initTransition(boardingPass);
                                            changeScene(ScanConfirmedPage::createScene);
                                        }
                                        System.out.println("PICK UP CASE");
                                    }
                                    /**
                                     * drop off case
                                     */
                                    case DROP_OFF -> {
                                        System.out.println("DROP OFF CASE");
                                        if (!kiosk.BPalreadyStored(boardingPass)) {
                                            ErrorMessageOriginAirportPage.message = "who are you?????????";
                                            changeScene(ErrorMessageOriginAirportPage::createScene);
                                        } else {
                                            Database.dropOff(boardingPass.getBPNumber(), Database.getIDFromICAO(kiosk.getAirport()));
                                            changeScene(ScanConfirmedPage::createScene);
                                        }
                                    }

                                    /**
                                     * error case
                                     */
                                    case UNKNOWN -> {
                                        changeScene(ErrorPleaseTryAgainMessagePage::createScene) ;}
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        changeScene(ErrorPleaseTryAgainMessagePage::createScene);
                    }
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

//    public static void startScanOld(){
//        //QR scan and use case identification.
//        new Thread(() -> {
//
//            Stage primaryStage = UIManager.primaryStageRef;
//
//            try {
//                String[] data = Kiosk.QRScan(); // run QR scanning
//                // After QRScan finishes, switch scene on the JavaFX Application Thread
//                javafx.application.Platform.runLater(() -> {
//                    try {
//                        if(Kiosk.sufficientData(data)){
//
//                            /////////parsing and creating object
//                            int BPN = Integer.parseInt(data[0].trim());
//                            String origin = data[1].trim();
//                            String destination = data[2].trim();
//                            String passenger = data[3].trim();
//                            String fltNr = data[4];
//
//                            boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);
//
//                            if(Kiosk.validateOriginAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
//                                if(Kiosk.validateDestinationAirport(boardingPass, Kiosk.grabber, Kiosk.canvas)){
//                                    switch(kiosk.useCaseIdentification(boardingPass, kiosk)){
//
//                                        case PICK_UP:
//                                            if(kiosk.BPalreadyStored(boardingPass)){
//                                                UIManager.changeScene(BadScan::createScene);
//
//                                            } else {kiosk.initTransition(boardingPass);
//                                                UIManager.changeScene(HelloHard::createScene);
//                                            }
//
//                                            System.out.println("PICK UP CASE");
//                                            break;
//
//
//                                        case DROP_OFF:
//                                            System.out.println("DROP OFF CASE");
//                                            break;
//                                    }
//
//                                } else {
//                                    //bad Dest
//                                    Scene helloScene = BadScan.createScene();
//                                    primaryStage.setScene(helloScene);
//                                }
//
//
//                            } else {
//                                //bad Origin
//                                Scene helloScene = BadScan.createScene();
//                                primaryStage.setScene(helloScene);
//                            }
//
//
//                        } else {
//                            //Bad scan
//                            Scene helloScene = BadScan.createScene();
//                            primaryStage.setScene(helloScene);
//                        }
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                });
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }).start();
//    }

    /**
     * this is the function that chages the scene
     * @param sceneSupplier
     */
    public static void changeScene(Supplier<Scene> sceneSupplier){
        Scene newScene = sceneSupplier.get();
        primaryStageRef.setScene(newScene);
    }

}
