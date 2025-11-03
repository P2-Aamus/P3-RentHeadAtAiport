package org.example.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.bytedeco.javacv.FrameGrabber;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class ErrorMessagePage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane border = new BorderPane();



        CircelErrorIcon Error1 = new CircelErrorIcon(100, 100);
        Error1.setScaleY(0.8);
        Error1.setScaleY(0.8);


        Text title = new Text("Oops!");
        title.setFont(new Font(70));




        VBox top = new VBox(Error1, title);
        top.setAlignment(Pos.TOP_CENTER);
        top.setSpacing(15);

        border.setCenter(top);







        Scene scene = new Scene(border, 1920, 1080);
        primaryStage.setTitle("AirHead");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
