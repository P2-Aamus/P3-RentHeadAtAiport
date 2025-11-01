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


public class CircelCheckmarkIcon extends Group {

    public CircelCheckmarkIcon(double radius,int height, int width) {
        Circle outerCircle = new Circle(80, Color.LIGHTGREEN);


        Rectangle bigRec = new Rectangle();
        bigRec.setX(0);
        bigRec.setY(0);
        bigRec.setWidth(10);
        bigRec.setHeight(70);
        bigRec.setFill(Color.WHITE);

        Rectangle smallRec = new Rectangle();
        smallRec.setX(0);
        smallRec.setY(0);
        smallRec.setWidth(40);
        smallRec.setHeight(10);
        smallRec.setFill(Color.WHITE);

        outerCircle.setCenterY(20);
        outerCircle.setCenterX(10);



        this.getChildren().addAll(outerCircle, bigRec, smallRec);
        this.setRotate(225);


    }
}
