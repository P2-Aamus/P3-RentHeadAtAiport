package org.example.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Kiosk;
import org.example.DAO.BoardingPassDAO;
import org.example.DAO.KioskDAO;
import org.example.DAO.HeadphoneDAO;
import org.example.DAO.TransactionDAO;
import org.example.DAO.impl.BoardingPassDAOImpl;
import org.example.DAO.impl.KioskDAOImpl;
import org.example.DAO.impl.HeadphoneDAOImpl;
import org.example.DAO.impl.TransactionDAOImpl;
import org.example.service.BoardingPassService;
import org.example.service.HeadphoneService;
import org.example.service.KioskService;
import org.example.service.TransactionService;

import java.util.function.Supplier;

/**
 * This class manages all the UI in our system.
 */
public class UIManager extends Application {

    public static BoardingPass boardingPass;
    public static Stage primaryStageRef;

    // The Kiosk instance (Controller/Coordinator)
    public static Kiosk kiosk;

    // We keep a static reference to bpService to check for existing BPs in startScan
    public static BoardingPassService bpService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStageRef = primaryStage;

        // --- 1. Initialize DAOs (Data Access Objects) ---
        BoardingPassDAO bpDAO = new BoardingPassDAOImpl();
        KioskDAO kioskDAO = new KioskDAOImpl();
        HeadphoneDAO hpDAO = new HeadphoneDAOImpl();
        TransactionDAO txDAO = new TransactionDAOImpl();

        // --- 2. Initialize Services (Business Logic) ---
        // We inject the DAOs into the Services
        bpService = new BoardingPassService(bpDAO);
        HeadphoneService hpService = new HeadphoneService(hpDAO);
        TransactionService txService = new TransactionService(txDAO);

        // KioskService needs specific services/DAOs injected
        KioskService kioskService = new KioskService(bpService, txService, kioskDAO, hpService);

        // --- 3. Initialize Kiosk (Main Controller) ---
        // We inject the Services into the Kiosk
        kiosk = new Kiosk("EKBI", bpService, kioskService, hpService, txService);

        // --- 4. Start UI ---
        primaryStage.setScene(ScannerPage.createScene());
        primaryStage.setTitle("AirHead");
        primaryStage.show();
    }

    public static void startScan() {
        // Logic runs on a background thread to avoid freezing UI
        new Thread(() -> {
            try {
                // 1. Perform Physical Scan (Instance method)
                String[] data = kiosk.QRScan();

                // 2. Update UI on JavaFX Thread
                Platform.runLater(() -> {
                    try {
                        // Validate Data Integrity (Static helper)
                        if (!Kiosk.sufficientData(data)) {
                            System.err.println("Bad scan: insufficient QR data");
                            changeScene(PleaseTryAgainMessagePage::createScene);
                            return;
                        }

                        // Parse Boarding Pass
                        int BPN = Integer.parseInt(data[0].trim());
                        String origin = data[1].trim();
                        String destination = data[2].trim();
                        String passenger = data[3].trim();
                        String fltNr = data[4].trim();

                        boardingPass = new BoardingPass(BPN, origin, destination, passenger, fltNr);

                        // 3. Validate Airports (Instance method)
                        Kiosk.AirportValidation validation = kiosk.validateAirports(boardingPass);

                        switch (validation) {
                            case INVALID_ORIGIN:
                                ErrorMessageOriginAirportPage.message = "Invalid Origin Airport.";
                                changeScene(ErrorMessageOriginAirportPage::createScene);
                                break;
                            case INVALID_DESTINATION:
                                ErrorMessageOriginAirportPage.message = "Invalid Destination Airport.";
                                changeScene(ErrorMessageOriginAirportPage::createScene);
                                break;
                            case OKAY:
                                // 4. Identify Use Case (Pick Up vs Drop Off)
                                Kiosk.InstructionMode mode = kiosk.useCaseIdentification(boardingPass);

                                switch (mode) {
                                    case PICK_UP -> {
                                        System.out.println("PICK UP CASE");
                                        // Check if BP already exists in DB using the service
                                        if (bpService.exists(boardingPass)) {
                                            ErrorMessageOriginAirportPage.message = "You have already picked up headphones.";
                                            changeScene(ErrorMessageOriginAirportPage::createScene);
                                        } else {
                                            // Store BP and Start Transaction
                                            kiosk.initTransition(boardingPass);
                                            // Assign Headphone
                                            kiosk.pickUp(boardingPass);
                                            changeScene(ScanConfirmedPage::createScene);
                                        }
                                    }
                                    case DROP_OFF -> {
                                        System.out.println("DROP OFF CASE");
                                        // Check if BP exists (Logic also handled partially in useCaseIdentification)
                                        if (!bpService.exists(boardingPass)) {
                                            ErrorMessageOriginAirportPage.message = "Boarding pass not found in system.";
                                            changeScene(ErrorMessageOriginAirportPage::createScene);
                                        } else {
                                            // Complete Transaction
                                            kiosk.dropOff(boardingPass);
                                            changeScene(ScanConfirmedPage::createScene);
                                        }
                                    }
                                    case UNKNOWN -> {
                                        System.err.println("Unknown instruction mode.");
                                        changeScene(PleaseTryAgainMessagePage::createScene);
                                    }
                                }
                                break;
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        changeScene(PleaseTryAgainMessagePage::createScene);
                    }
                });

            } catch (Exception ex) {
                // Handle QR Scanner or Thread errors
                ex.printStackTrace();
                Platform.runLater(() -> changeScene(PleaseTryAgainMessagePage::createScene));
            }
        }).start();
    }

    /**
     * Helper to change the scene
     */
    public static void changeScene(Supplier<Scene> sceneSupplier){
        Scene newScene = sceneSupplier.get();
        primaryStageRef.setScene(newScene);
    }
}