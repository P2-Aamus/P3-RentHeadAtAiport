package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.net.URL;

public class HeadphonesDroppedOffPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = createScene();
        primaryStage.setTitle("Headphones dropped off");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene createScene() {
        BorderPane root = new BorderPane();


        Text hpDrop = new Text("Headphones dropped off");
        hpDrop.setFont(Font.font(75));


        Text seeU = new Text("We hope to see you again soon");
        seeU.setFont(Font.font(50));

        ImageView planeView = null;
        URL imgUrl = HeadphonesDroppedOffPage.class.getResource("/images/airplane-aircraft-icon-a5-landing-clip-art-landing-2d0cf51950a7e2fd5bd7b9f93a890e1a.png");
        if (imgUrl != null) {
            planeView = new ImageView(new Image(imgUrl.toExternalForm()));
            planeView.setFitHeight(300);
            planeView.setPreserveRatio(true);
        }

       VBox vbox2 = new VBox(hpDrop);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPadding(new Insets(0,0,60,0));



        VBox vbox = new VBox(50);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20,0,30,0));
        vbox.getChildren().addAll(vbox2, seeU);
        if (planeView != null) {
            vbox.getChildren().add(planeView);
        }

        root.setCenter(vbox);
        return new Scene(root, 1920, 1080);
    }
}
