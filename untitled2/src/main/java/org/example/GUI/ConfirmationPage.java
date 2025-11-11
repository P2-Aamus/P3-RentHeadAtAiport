package org.example.GUI;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.BoardingPass;
import org.example.Database;
import org.example.Kiosk;

import java.sql.SQLException;

/**
 * This UI page is the confirmation page that the passenger gets if they have a
 * valid boarding pass for pick up or they dropped off their headphones successfully.
 */
public class ConfirmationPage {

    /**
     * Static attributes of the kiosk and boarding pass object that the passenger scans the
     * kiosk with.
     */
    static BoardingPass BP = UIManager.boardingPass;
    static Kiosk kiosk = UIManager.kiosk;
    // Paths to images.
    private static final String ARROW_ICON_PATH = "images/next.png";

    private static Label instructionLabel;

    /**
     * Returns a scene with borders and vertical and horizontal boxes with an icon and an image.
     * @return
     */
    public static Scene createScene(){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 5; -fx-border-radius: 15;");


        // Center content VBox
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);


        CircelCheckmarkIcon checkmark = new CircelCheckmarkIcon(100, 100, 100);
        checkmark.setScaleX(0.9);
        checkmark.setScaleY(0.9);
        StackPane checkmarkPane = new StackPane(checkmark);

        Label thankYouLabel = new Label("Thank you!");
        thankYouLabel.setFont(Font.font("Arial", 60));
        thankYouLabel.setTextFill(Color.BLACK);

        HomeButtonIcon home = new HomeButtonIcon(40);

        home.setOnMouseClicked(event -> {
            UIManager.changeScene(ScannerPage::createScene);
            try {
                Database.deleteLastBP(BP);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        instructionLabel = new Label();
        instructionLabel.setFont(Font.font("Arial", 38));
        instructionLabel.setTextFill(Color.GRAY);
        instructionLabel.setAlignment(Pos.CENTER);
        instructionLabel.setWrapText(true);

        centerBox.getChildren().addAll(checkmarkPane, thankYouLabel, instructionLabel);
        StackPane.setAlignment(home, Pos.TOP_LEFT);
        StackPane.setMargin(home, new Insets(30, 0, 0, 30));
        root.setCenter(centerBox);

        ImageView arrow = loadImageView(ARROW_ICON_PATH, 60);
        VBox arrowBox = new VBox(arrow);
        arrowBox.setAlignment(Pos.CENTER_RIGHT);
        arrowBox.setPadding(new Insets(0, 40, 0, 0));
        root.setRight(arrowBox);

        setInstructionMode();

        return new Scene(root, 1920, 1080);
    }



    private static ImageView loadImageView(String path, double size) {
        Image image;
        try {
            image = new Image(ConfirmationPage.class.getResource("/" + path).toExternalForm());
        } catch (Exception e) {
            image = new Image("file:" + path);
        }
        ImageView iv = new ImageView(image);
        iv.setFitWidth(size);
        iv.setPreserveRatio(true);
        return iv;
    }


    /**
     * Switch case that sends the passenger to a confirmation page based on if the passenger is
     * at the kiosk for either picking up or dropping off.
     *
     * The scenes will be displayed in 10 seconds because of the PauseTransition
     *
     * Afterward will a lambda function send the passenger to a different scene with a goodbye message.
     */
    public static void setInstructionMode() {
        switch (Kiosk.useCaseIdentification(BP, kiosk)) {
            case DROP_OFF:
                instructionLabel.setText("Please drop your\nheadphones to the right");
                // Set a 10-second timer
                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(event -> UIManager.changeScene(HeadphonesDroppedOffPage::createScene));
                pause.play();
                break;
            case PICK_UP:
                instructionLabel.setText("Please retrieve your\nheadphones to the right");
                // Set a 10-second timer
                PauseTransition pause2 = new PauseTransition(Duration.seconds(10));
                pause2.setOnFinished(event -> UIManager.changeScene(PleasantFlightPage::createScene));
                pause2.play();
                break;
        }
    }
}
