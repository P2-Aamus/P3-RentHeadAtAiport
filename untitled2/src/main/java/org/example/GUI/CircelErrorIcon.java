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

    public CircelErrorIcon(int height, int width) {

        Circle bigCircle = new Circle(85, Color.RED);
        bigCircle.setCenterY(50);
        bigCircle.setCenterX(50);

        Rectangle vertical = new Rectangle();
        vertical.setX(0 + width / 2.0 - 10);
        vertical.setY(0);
        vertical.setWidth(20);
        vertical.setHeight(height);
        vertical.setFill(Color.WHITE);

        Rectangle horizontal = new Rectangle();
        horizontal.setX(0);
        horizontal.setY(0 + height / 2.0 - 10);
        horizontal.setWidth(width);
        horizontal.setHeight(20);
        horizontal.setFill(Color.WHITE);

        this.getChildren().addAll(bigCircle, vertical, horizontal);
        this.setRotate(45);

    }
}
