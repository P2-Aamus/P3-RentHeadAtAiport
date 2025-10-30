package org.example.GUI;

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

    public static Scene createScene() throws FrameGrabber.Exception {
        // de skal alle sammen erstattes med objekter når det spiller
        Text title = new Text(875, 623, "AirHead");
        title.setFont(new Font(50));

        Text please = new Text(875, 523, "Hello");
        please.setFont(new Font(60));

        Text passengerName = new Text(875, 423, Kiosk.getBP(3));
        passengerName.setFont(new Font(50));

        Text flightNumber = new Text(375, 323, Kiosk.getBP(4));
        flightNumber.setFont(new Font(50));

        Text originAir = new Text(450, 323, Kiosk.getBP(1));
        originAir.setFont(new Font(45));

        Text originFull = new Text(450, 223, Database.getNameFromICAO(Kiosk.getBP(1)));
        originFull.setFont(new Font(30));

        Text destAir = new Text(650, 323, Kiosk.getBP(2));
        destAir.setFont(new Font(45));

        Text destFull = new Text(650, 223, Database.getNameFromICAO(Kiosk.getBP(2)));
        destFull.setFont(new Font(30));

        Image SAS = new Image("images/SAS Logo.png");
        ImageView sasView = new ImageView(SAS);

        Group root = new Group(title, please, passengerName, flightNumber, originAir, originFull, destAir, destFull, sasView);

        return new Scene(root, 1920, 1080);
    }
}
