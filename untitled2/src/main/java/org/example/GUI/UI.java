package org.example.GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UI extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    Rectangle square = new Rectangle(100,100, Color.RED);
    Group root = new Group(square);

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 1920, 1080);

        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
