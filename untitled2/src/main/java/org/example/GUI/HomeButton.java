package org.example.GUI;

import javafx.scene.Group;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class HomeButton extends Group {

    public HomeButton(double radius) {

        Circle homeCircle = new Circle(radius, Color.DODGERBLUE);

        URL homeIconUrl = HomeButton.class.getResource("/Images/homeIcon.png");
        if (homeIconUrl != null) {
            ImageView iconHome = new ImageView(new Image(homeIconUrl.toExternalForm()));
            iconHome.setFitWidth(35);
            iconHome.setPreserveRatio(true);

            StackPane stack = new StackPane(homeCircle, iconHome);
            this.getChildren().add(stack);
            this.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(150), this);
                st.setToX(1.05);
                st.setToY(1.05);
                st.play();

                homeCircle.setFill(Color.DEEPSKYBLUE);
            });

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
