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
import javafx.animation.ScaleTransition;
import javafx.util.Duration;


public class UIButton extends Group {

    public UIButton(int x, int y) {
        Rectangle UIButtonRec = new Rectangle(200, 80, Color.DODGERBLUE);
        UIButtonRec.setX(0);
        UIButtonRec.setY(0);

        Circle UIButtonCircle1 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle1.setCenterX(200);
        UIButtonCircle1.setCenterY(40);


        Circle UIButtonCircle2 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle2.setCenterX(-5);
        UIButtonCircle2.setCenterY(40);

        this.getChildren().addAll(UIButtonRec, UIButtonCircle1, UIButtonCircle2);


        this.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();

            UIButtonRec.setFill(Color.DEEPSKYBLUE);
            UIButtonCircle1.setFill(Color.DEEPSKYBLUE);
            UIButtonCircle2.setFill(Color.DEEPSKYBLUE);
        });

        this.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();

            UIButtonRec.setFill(Color.DODGERBLUE);
            UIButtonCircle1.setFill(Color.DODGERBLUE);
            UIButtonCircle2.setFill(Color.DODGERBLUE);
        });




    }

}
