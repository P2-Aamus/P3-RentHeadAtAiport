package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;

public class ArrowShape extends Group {


    public ArrowShape(int x, int y){

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
