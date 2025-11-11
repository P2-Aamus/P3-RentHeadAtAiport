package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;

/**
 * This is an arrow shape that has been made with JavaFX and has been used in the scanner page
 * and pleaseTryAgainMessage page.
 */
public class ArrowShape extends Group {


    /**
     * The arrow shape consists of a rectangle and a triangle (polygon).
     */
    public ArrowShape(){

        /**
         * The x and y coordinates has been set in relation to the polygon
         */
        Rectangle rec = new Rectangle();
        rec.setX(60 / 2.0 - 10);
        rec.setY(-40);
        rec.setWidth(20);
        rec.setHeight(70);
        rec.setFill(Color.BLACK);

        /**
         * The polygon has been made with the getter, getPoints.
         * The array of numbers are the x and y points within the polygon.
         * 0.0 0.0 is the first corner which is above to the left
         * 25.0 60.0 is the second corner which is at the botton center
         * 60.0 25.0 is at the third corner which is at the to the right
         */
        Polygon upsideDownTriangle = new Polygon();
        upsideDownTriangle.getPoints().addAll(new Double[] {0.0, 0.0, 25.0, 60.0, 60.0, 25.0});
        upsideDownTriangle.setRotate(225);
        upsideDownTriangle.setFill(Color.BLACK);

        this.getChildren().addAll(rec, upsideDownTriangle);

    }


}
