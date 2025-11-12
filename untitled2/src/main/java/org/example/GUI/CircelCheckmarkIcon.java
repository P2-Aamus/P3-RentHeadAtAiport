package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



/**
 * This is a checkmark icon that was made with JavaFX and has been used in the confirmation page.
 */
public class CircelCheckmarkIcon extends Group {

    public CircelCheckmarkIcon() {
        Circle outerCircle = new Circle(80, Color.LIGHTGREEN);


        /**
         * The checkmark consists of two rectangles with the same principles that was implemented
         * in the error icon.
         *
         * This icon was easier to make because it was not necessary to center the two rectangles
         * together, since the anchor point of both rectangles is placed in the top right corner.
         */
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

        /**
         * Center the outer circle in relation to the rectangles
         */
        outerCircle.setCenterY(25);
        outerCircle.setCenterX(15);


        /**
         * Group all the shapes together and rotate the icon 225 degrees, so the two rectangles
         * represents a checkmark symbol in the circle
         */
        this.getChildren().addAll(outerCircle, bigRec, smallRec);
        this.setRotate(225);


    }
}
