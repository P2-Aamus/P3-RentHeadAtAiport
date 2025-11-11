package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is a home button icon that has been implemented in the UI, for the passenger to return back
 * to the scanner stage
 */
public class HomeButtonIcon extends Group {

    /**
     * The home butten consists of a blue circle made in javafx, that contains an image of a symbol that
     * represents a house
     * @param radius
     */
    public HomeButtonIcon(double radius) {

        Circle homeCircle = new Circle(radius, Color.DODGERBLUE);

        URL homeIconUrl = HomeButtonIcon.class.getResource("/Images/homeIcon.png");
        if (homeIconUrl != null) {
            ImageView iconHome = new ImageView(new Image(homeIconUrl.toExternalForm()));
            iconHome.setFitWidth(35);
            iconHome.setPreserveRatio(true);

            StackPane stack = new StackPane(homeCircle, iconHome);
            this.getChildren().add(stack);

            /**
             * Hover function that changes the scale and color
             */
            this.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
                st.setToX(1.05);
                st.setToY(1.05);
                st.play();

                homeCircle.setFill(Color.DEEPSKYBLUE);
            });

            /**
             * When the pointer has been removed from the butten, will with function return the
             * butten to its original scale and color
             */
            this.setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
                st.setToX(1.0);
                st.setToY(1.0);
                st.play();

                homeCircle.setFill(Color.DODGERBLUE);
            });
        } else {
            System.out.println("Home icon image not found!");
        }



    }
}
