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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.text.Font;



public class UIButton extends Group {

    private ScaleTransition currentTransition;
    private final Text label;

    public UIButton(int height, int width, String text) {
        Rectangle UIButtonRec = new Rectangle(200, 80, Color.DODGERBLUE);
        UIButtonRec.setX(0);
        UIButtonRec.setY(0);

        Circle UIButtonCircle1 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle1.setCenterX(200);
        UIButtonCircle1.setCenterY(40);


        Circle UIButtonCircle2 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle2.setCenterX(-5);
        UIButtonCircle2.setCenterY(40);

        label = new Text(text);
        label.setFont(Font.font(height * 0.4));
        label.setFill(Color.WHITE);


        double textX = width / 2.0 - label.getLayoutBounds().getWidth() / 2.0;
        double textY = height / 2.0 + label.getLayoutBounds().getHeight() / 4.0;
        label.setX(textX);
        label.setY(textY);

        this.getChildren().addAll(UIButtonRec, UIButtonCircle1, UIButtonCircle2, label);

        this.setOnMouseEntered(e -> {

                UIButtonRec.setFill(Color.DEEPSKYBLUE);
                UIButtonCircle1.setFill(Color.DEEPSKYBLUE);
                UIButtonCircle2.setFill(Color.DEEPSKYBLUE);
          });

        this.setOnMouseExited(e -> {

            UIButtonRec.setFill(Color.DODGERBLUE);
            UIButtonCircle1.setFill(Color.DODGERBLUE);
            UIButtonCircle2.setFill(Color.DODGERBLUE);
        });
    }

}
