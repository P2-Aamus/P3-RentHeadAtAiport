package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is an icon made in JavaFX, that has been used in the error message page.
 */
public class CircelErrorIcon extends Group {

    /**
     *
     * @param height Defines how long the vertical rectangle should be, and where it should be
     *               placed on the horizontal rectangle
     * @param width Defines the wide the horizontal rectangle should be, and where it should be
     *              placed on the vertical rectangle
     */
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

        /**
         * Group all the shapes together and rotate the cross 45 degrees to make it represent an X
         */
        this.getChildren().addAll(bigCircle, vertical, horizontal);
        this.setRotate(45);

    }
}
