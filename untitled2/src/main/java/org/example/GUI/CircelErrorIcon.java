package org.example.GUI;

import javafx.scene.Group;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircelErrorIcon extends Group {

    public CircelErrorIcon(int height, int width, double radius) {

        Circle bigCircle = new Circle(80, Color.RED);

        Rectangle vertical = new Rectangle();
        vertical.setX(50 + width / 2.0 - 10);
        vertical.setY(50);
        vertical.setWidth(10);
        vertical.setHeight(height);
        vertical.setFill(Color.WHITE);


        Rectangle horizontal = new Rectangle();
        horizontal.setX(50);
        horizontal.setY(50 + height / 2.0 - 10);
        horizontal.setWidth(width);
        horizontal.setHeight(20);
        horizontal.setFill(Color.WHITE);


        this.getChildren().addAll(bigCircle, vertical, horizontal);
        this.setRotate(45);
    }
}
