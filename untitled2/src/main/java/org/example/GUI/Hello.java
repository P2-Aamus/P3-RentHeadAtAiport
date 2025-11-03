package org.example.GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.example.BoardingPass;
import org.example.Database;
import org.example.Kiosk;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.*;
import org.bytedeco.javacv.FrameGrabber;



public class Hello{

    static BoardingPass BP = Scanner.sendBoardingPass();
    public static Scene createScene() throws FrameGrabber.Exception {
        // de skal alle sammen erstattes med objekter når det spiller
        Text title = new Text(875, 623, "AirHead");
        title.setFont(new Font(50));

        Text please = new Text(875, 523, "Hello");
        please.setFont(new Font(60));

        Text passengerName = new Text(875, 423, BP.getPsgName());
        passengerName.setFont(new Font(50));

        Text flightNumber = new Text(375, 323, BP.getfltNr());
        flightNumber.setFont(new Font(50));

        Text originAir = new Text(450, 323, BP.getOriginAirport());
        originAir.setFont(new Font(45));

        Text originFull = new Text(450, 223, Database.getNameFromICAO(BP.getOriginAirport()));
        originFull.setFont(new Font(30));

        Text destAir = new Text(650, 323, BP.getDestinationAirport());
        destAir.setFont(new Font(45));

        Text destFull = new Text(650, 223, Database.getNameFromICAO(BP.getDestinationAirport()));
        destFull.setFont(new Font(30));

        //Image SAS = new Image("images/SAS Logo.png");
        //ImageView sasView = new ImageView(SAS);

        Button goToPayment = new Button("Go To Payment");
        goToPayment.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Scene helloScene = Payment.createScene();
                Scanner.getPrimaryStage().setScene(helloScene);
            }
        }) ;

        Group root = new Group(title, please, passengerName, flightNumber, originAir, originFull, destAir, destFull, goToPayment);

        return new Scene(root, 1920, 1080);


    }
}
