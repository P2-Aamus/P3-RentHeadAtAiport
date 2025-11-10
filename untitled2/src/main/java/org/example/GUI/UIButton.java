package org.example.GUI;

import javafx.scene.Group;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;


/**
 * this class is a UI button object that has been used in the Hello
 */

public class UIButton extends Group {

    /**
     * the different parameters determines the location and size of the textlabel in the button
     * @param x
     * @param y
     * @param size
     * @param labelText
     */
    public UIButton(int x, int y, int size, String labelText) {

        /**
         * the button is build up in three independent shapes
         */
        Rectangle UIButtonRec = new Rectangle(200, 80, Color.DODGERBLUE);
        UIButtonRec.setX(0);
        UIButtonRec.setY(0);

        Circle UIButtonCircle1 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle1.setCenterX(200);
        UIButtonCircle1.setCenterY(40);


        Circle UIButtonCircle2 = new Circle(40, Color.DODGERBLUE);
        UIButtonCircle2.setCenterX(-5);
        UIButtonCircle2.setCenterY(40);

        /**
         * text label in the button
         */

        Text label = new Text(labelText);
        label.setFont(Font.font(size));
        label.setFill(Color.WHITE);
        label.setX(x - label.getLayoutBounds().getWidth() / 2);
        label.setY(y);

        /**
         * group the shapes and label into one object
         */

        this.getChildren().addAll(UIButtonRec, UIButtonCircle1, UIButtonCircle2, label);


        /**
         * lambda function that changes the buttons scale and changes the colors when you hover
         * your mouse over the button
         */
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

        /**
         * function that changes the scale and color back when the pointer leaves the button
         */
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
