package org.example.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;

public class Scanner{

    public static Scene createScene(){

        UIManager.startScan();

        BorderPane border = new BorderPane();


        Text title = new Text("Kiosk");
        title.setFont(new Font(80));
        BorderPane.setAlignment(title, Pos.CENTER);
        border.setTop(title);


        VBox centerContent = new VBox(20);
        centerContent.setAlignment(Pos.CENTER);

        Text please = new Text("Please scan your boarding pass");
        please.setFont(new Font(60));


        border.setCenter(centerContent);

        URL scannerUrl = Scanner.class.getResource("/Images/Scan.JPEG");
        if (scannerUrl != null) {
            ImageView scannerView = new ImageView(new Image(scannerUrl.toExternalForm()));
            scannerView.setFitWidth(250);
            scannerView.setPreserveRatio(true);
            centerContent.getChildren().addAll(please, scannerView);

        } else {
            System.out.println("Scanner image not found!");
        }
        border.setCenter(centerContent);

        Arrow arrow = new Arrow(300, 300);
        BorderPane.setAlignment(arrow, Pos.CENTER);
        BorderPane.setMargin(arrow, new Insets(100));
        border.setBottom(arrow);


        return new Scene(border, 1920, 1080);
    }
}
