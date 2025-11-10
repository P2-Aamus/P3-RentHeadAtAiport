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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.scene.text.Font;



public class UIButton extends Group {



    public UIButton(int x, int y, int size, String labelText) {

        Rectangle UIButtonRec = new Rectangle(200, 80, Color.DODGERBLUE);
        UIButtonRec.setX(0);
        UIButtonRec.setY(0);

        Circle UIButtonCircle1 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle1.setCenterX(200);
        UIButtonCircle1.setCenterY(40);


        Circle UIButtonCircle2 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle2.setCenterX(-5);
        UIButtonCircle2.setCenterY(40);

        Text label = new Text(labelText);
        label.setFont(Font.font(size));
        label.setFill(Color.WHITE);
        label.setX(x - label.getLayoutBounds().getWidth() / 2);
        label.setY(y);

        this.getChildren().addAll(UIButtonRec, UIButtonCircle1, UIButtonCircle2, label);

        this.setOnMouseEntered(e -> {

            double baseX = getScaleX();
            double baseY = getScaleY();

            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(baseX * 1.1);
            st.setToY(baseY * 1.1);
            st.play();

            UIButtonRec.setFill(Color.DEEPSKYBLUE);
            UIButtonCircle1.setFill(Color.DEEPSKYBLUE);
            UIButtonCircle2.setFill(Color.DEEPSKYBLUE);
        });

        this.setOnMouseExited(e -> {

            double baseX = getScaleX() / 1.1;
            double baseY = getScaleY() / 1.1;

            ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
            st.setToX(baseX);
            st.setToY(baseY);
            st.play();

            UIButtonRec.setFill(Color.DODGERBLUE);
            UIButtonCircle1.setFill(Color.DODGERBLUE);
            UIButtonCircle2.setFill(Color.DODGERBLUE);
        });

    }

}
