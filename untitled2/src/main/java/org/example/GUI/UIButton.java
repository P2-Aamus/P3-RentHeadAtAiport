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

        // center text (approximate)
        double textX = width / 2.0 - label.getLayoutBounds().getWidth() / 2.0;
        double textY = height / 2.0 + label.getLayoutBounds().getHeight() / 4.0;
        label.setX(textX);
        label.setY(textY);

        this.getChildren().addAll(UIButtonRec, UIButtonCircle1, UIButtonCircle2, label);

        this.setOnMouseEntered(e -> {
            if (currentTransition == null) {
                double baseX = getScaleX();
                double baseY = getScaleY();

                ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
                st.setToX(baseX * 1.1);
                st.setToY(baseY * 1.1);
                st.play();
                currentTransition = st;

                UIButtonRec.setFill(Color.DEEPSKYBLUE);
                UIButtonCircle1.setFill(Color.DEEPSKYBLUE);
                UIButtonCircle2.setFill(Color.DEEPSKYBLUE);
            } else {
                System.out.println("error in button");
            }
        });

        this.setOnMouseExited(e -> {

            double baseX = getScaleX() / 1.1;
            double baseY = getScaleY() / 1.1;

            ScaleTransition st2 = new ScaleTransition(Duration.millis(150), this);
            st2.setToX(baseX);
            st2.setToY(baseY);
            st2.play();

            UIButtonRec.setFill(Color.DODGERBLUE);
            UIButtonCircle1.setFill(Color.DODGERBLUE);
            UIButtonCircle2.setFill(Color.DODGERBLUE);
        });
    }

}
