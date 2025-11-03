package org.example.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;

public class Arrow extends Group {


    public Arrow(int x, int y){

        Rectangle rec = new Rectangle();
        rec.setX(60 / 2.0 - 10);
        rec.setY(-40);
        rec.setWidth(20);
        rec.setHeight(70);
        rec.setFill(Color.BLACK);

        Polygon upsideDownTriangle = new Polygon();
        upsideDownTriangle.getPoints().addAll(new Double[] {0.0, 0.0, 25.0, 60.0, 60.0, 25.0});
        upsideDownTriangle.setRotate(225);
        upsideDownTriangle.setFill(Color.BLACK);

        this.getChildren().addAll(rec, upsideDownTriangle);

    }


}
