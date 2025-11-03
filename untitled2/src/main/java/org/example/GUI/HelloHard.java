package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.BoardingPass;
import org.example.Database;
import org.example.Kiosk;

import java.net.URL;
import java.sql.SQLException;

public class HelloHard {

    static BoardingPass BP = UIManager.boardingPass;
    private static Text useCaseText = new Text();

    public static Scene createScene(){

        setInstructionMode(Kiosk.useCase);

        BorderPane border = new BorderPane();

        CircelCheckmarkIcon check = new CircelCheckmarkIcon(500, 500, 500);
        check.setScaleX(0.9);
        check.setScaleY(0.9);

        Text helloText = new Text("Hello!");
        helloText.setFont(Font.font(75));

        Text nameText = new Text(BP.getPsgName());
        nameText.setFont(Font.font(50));

        // SAS-logo and flight number
        HBox airlineHBox = new HBox();
        airlineHBox.setSpacing(7);
        airlineHBox.setAlignment(Pos.CENTER_LEFT);

        URL SAS = HelloHard.class.getResource("/images/Scandinavian_Airlines_logo.svg.png");
        ImageView sasView = null;
        if (SAS != null) {
            sasView = new ImageView(new Image(SAS.toExternalForm()));
            sasView.setFitHeight(40);
            sasView.setPreserveRatio(true);
            airlineHBox.getChildren().add(sasView);
        }

        Text flightNumber = new Text(BP.getfltNr());
        flightNumber.setFont(Font.font(40));
        airlineHBox.getChildren().add(flightNumber);

        Text originCode = new Text(BP.getOriginAirport());
        originCode.setFont(Font.font(40));

        Text originFull = new Text(Database.getNameFromICAO(BP.getOriginAirport()));
        originFull.setFont(Font.font(25));

        Text destCode = new Text(BP.getDestinationAirport());
        destCode.setFont(Font.font(40));

        Text destFull = new Text(Database.getNameFromICAO(BP.getDestinationAirport()));
        destFull.setFont(Font.font(25));

        VBox originBox = new VBox(originCode, originFull);
        originBox.setAlignment(Pos.CENTER);

        VBox destBox = new VBox(destCode, destFull);
        destBox.setAlignment(Pos.CENTER);

        Text arrow = new Text("\u2794");
        arrow.setFont(Font.font( 38));


        HBox flightRoute = new HBox();
        flightRoute.setSpacing(23);
        flightRoute.setAlignment(Pos.CENTER);
        flightRoute.getChildren().addAll(airlineHBox, originBox, arrow, destBox);

        // Use UIButton for custom blue buttons with text layered using StackPane
        UIButton notYourFlightBtn = new UIButton(0, 0);
        notYourFlightBtn.setOnMouseClicked(event -> {
            try {
                Database.deleteLastBP(BP);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            UIManager.changeScene(Scanner::createScene);
        });


        UIButton continueBtn = new UIButton(0, 0);
        continueBtn.setOnMouseClicked(e ->{
            useCaseBtn(Kiosk.useCase);});



        Text notYourFlightText = new Text("Not your flight?");
        notYourFlightText.setFont(Font.font(24));
        notYourFlightText.setFill(Color.WHITE);

        Text continueText = useCaseText;
        continueText.setFont(Font.font(24));
        continueText.setFill(Color.WHITE);

        StackPane stackBtn1 = new StackPane(notYourFlightBtn, notYourFlightText);
        stackBtn1.setPrefSize(200, 80);

        StackPane stackBtn2 = new StackPane(continueBtn, continueText);
        stackBtn2.setPrefSize(260, 80);

        HBox buttonsHBox = new HBox(40, stackBtn1, stackBtn2);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(45, 0, 0, 0));

        VBox centerContent = new VBox(20, check, helloText, nameText, flightRoute, buttonsHBox);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(72, 50, 110, 50));
        border.setCenter(centerContent);



        return new Scene(border, 1920, 1080);
    }

    public static void setInstructionMode(Kiosk.InstructionMode mode) {
        switch (mode) {
            case DROP_OFF:
                useCaseText.setText("Continue to drop-off");
                break;
            case PICK_UP:
                useCaseText.setText("Continue to payment");
                break;
        }
    }

    public static void useCaseBtn(Kiosk.InstructionMode mode) {
        switch (mode) {
            case DROP_OFF:
                UIManager.changeScene(BadScan::createScene);
                break;
            case PICK_UP:
                UIManager.changeScene(Payment::createScene);
                break;
        }
    }
}
