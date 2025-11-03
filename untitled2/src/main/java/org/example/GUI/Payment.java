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

public class Payment extends Application {

    private static final String SAS_LOGO_PATH = "images/SAS Logo.png";
    private static final String CARD_ICON_PATH = "images/Debit.png"; // use your Scan image for "Card"
    private static final String MOBILEPAY_ICON_PATH = "images/MobilePay.png";

    private static final Color BACKGROUND_COLOR = Color.web("#F9FAFB");
    private static final Font HEADER_FONT = Font.font("Arial", 24);
    private static final Font OPTION_TITLE_FONT = Font.font("Arial", 20);
    private static final Font OPTION_TEXT_FONT = Font.font("Arial", 16);

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 40, 0, 40));
        header.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Image sasImage = loadImage(SAS_LOGO_PATH);
        ImageView sasView = new ImageView(sasImage);
        sasView.setFitWidth(100);
        sasView.setPreserveRatio(true);

        Label headerLabel = new Label("Choose a payment method");
        headerLabel.setFont(HEADER_FONT);
        header.getChildren().addAll(sasView, headerLabel);
        root.setTop(header);

        HBox optionsBox = new HBox(40);
        optionsBox.setPadding(new Insets(40));
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox cardOption = createPaymentOption(CARD_ICON_PATH, "Card", "Debit / Credit Card");
        VBox mobilePayOption = createPaymentOption(MOBILEPAY_ICON_PATH, "MobilePay", "Pay easily via app");

        optionsBox.getChildren().addAll(cardOption, mobilePayOption);
        root.setCenter(optionsBox);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setTitle("Bump eller Skunk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Image loadImage(String path) {
        try {

            return new Image(getClass().getResource("/" + path).toExternalForm());
        } catch (Exception e) {

            return new Image("file:" + path);
        }
    }


    private VBox createPaymentOption(String imagePath, String title, String subtitle) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setPrefWidth(250);
        box.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 10; -fx-background-radius: 10;");

        Image image = loadImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true);

        Label titleLabel = new Label(title);
        titleLabel.setFont(OPTION_TITLE_FONT);

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(OPTION_TEXT_FONT);
        subtitleLabel.setTextFill(Color.GRAY);

        box.getChildren().addAll(imageView, titleLabel, subtitleLabel);

        box.setOnMouseEntered(e -> {
            box.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: lightgray; "
                    + "-fx-border-radius: 10; -fx-background-radius: 10;");
            box.setCursor(Cursor.HAND);
            imageView.setScaleX(1.05);
            imageView.setScaleY(1.05);
        });
        box.setOnMouseExited(e -> {
            box.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                    + "-fx-border-radius: 10; -fx-background-radius: 10;");
            box.setCursor(Cursor.DEFAULT);
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });

        box.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Confirmed");
            alert.setHeaderText(null);
            alert.setContentText("You selected: " + title);
            alert.showAndWait();
        });

        return box;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
