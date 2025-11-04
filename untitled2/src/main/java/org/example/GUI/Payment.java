package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Kiosk;

public class Payment {

    static BoardingPass BP = UIManager.boardingPass;
    private static final String CARD_ICON_PATH = "images/Debit.png";
    private static final String MOBILEPAY_ICON_PATH = "images/MobilePay.png";

    private static final Color BACKGROUND_COLOR = Color.web("#F9FAFB");
    private static final Font HEADER_FONT = Font.font("Arial", 48);       // Bigger for HD
    private static final Font OPTION_TITLE_FONT = Font.font("Arial", 28);
    private static final Font OPTION_TEXT_FONT = Font.font("Arial", 22);

    //@Override
    //public void start(Stage primaryStage) {
    public static Scene createScene(){
        BorderPane root = new BorderPane();
        StackPane topPane = new StackPane();
        root.setPadding(new Insets(40));
        root.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        // --- HEADER (Centered) ---
        Label headerLabel = new Label("Choose a payment method");
        headerLabel.setFont(HEADER_FONT);

        HomeButton home = new HomeButton(40);

        topPane.getChildren().addAll(home, headerLabel);
        StackPane.setAlignment(headerLabel, Pos.CENTER);
        StackPane.setAlignment(home, Pos.TOP_LEFT);

        StackPane.setMargin(home, new Insets(30, 0, 0, 30));

        root.setTop(topPane);


        // --- OPTIONS PANEL ---
        HBox optionsBox = new HBox(80);  // Larger spacing for HD
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(40));

        VBox cardOption = createPaymentOption(CARD_ICON_PATH, "Card", "Debit / Credit Card");
        VBox mobilePayOption = createPaymentOption(MOBILEPAY_ICON_PATH, "MobilePay", "Pay easily via app");

        optionsBox.getChildren().addAll(cardOption, mobilePayOption);
        root.setCenter(optionsBox);

        return new Scene(root, 1920, 1080);
    }

    private static Image loadImage(String path) {
        try {
            return new Image(Payment.class.getResource("/" + path).toExternalForm());
        } catch (Exception e) {
            return new Image("file:" + path);
        }
    }

    private static VBox createPaymentOption(String imagePath, String title, String subtitle) {
        VBox box = new VBox(20);  // More spacing for HD
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(40));
        box.setPrefWidth(400);
        box.setPrefHeight(400);
        box.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 20; -fx-background-radius: 20; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");

        Image image = loadImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);    // Scale larger for HD
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(OPTION_TITLE_FONT);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(OPTION_TEXT_FONT);
        subtitleLabel.setTextFill(Color.GRAY);

        box.getChildren().addAll(imageView, titleLabel, subtitleLabel);

        // Hover effect
        box.setOnMouseEntered(e -> {
            box.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: lightgray; "
                    + "-fx-border-radius: 20; -fx-background-radius: 20; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 25, 0, 0, 6);");
            box.setCursor(Cursor.HAND);
            imageView.setScaleX(1.1);
            imageView.setScaleY(1.1);
        });

        box.setOnMouseExited(e -> {
            box.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                    + "-fx-border-radius: 20; -fx-background-radius: 20; "
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");
            box.setCursor(Cursor.DEFAULT);
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });

        // Click event
        box.setOnMouseClicked(e -> {
            Kiosk.pickUp(BP, UIManager.kiosk);
            UIManager.changeScene(DeliverHP::createScene);
        });

        return box;
    }
}
