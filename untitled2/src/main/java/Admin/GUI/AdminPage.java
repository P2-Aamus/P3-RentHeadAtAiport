package Admin.GUI;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;
import org.example.GUI.UIButton;



public class AdminPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        Scene scene = createScene();
        primaryStage.setTitle("Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene createScene() {
        BorderPane root = new BorderPane();


        Text overview = new Text("Overview");
        overview.setFont(new Font(50));

        Text Graph = new Text("Graph");
        Graph.setFont(new Font(50));

        UIButton downloadButton = new UIButton(100, 100);
        downloadButton.setScaleX(0.5);
        downloadButton.setScaleY(0.5);

        HBox buttonBox = new HBox(downloadButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 20; -fx-background-radius: 20; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");



        VBox overviewBox = new VBox(overview, buttonBox);
        overviewBox.setPadding(new Insets(70,70,40,100));
        overviewBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 20; -fx-background-radius: 20; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");


        HBox graphBox = new HBox(Graph);
        graphBox.setPadding(new Insets(70, 800, 20, 40));
        graphBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 20; -fx-background-radius: 20; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");




        HBox centerBox = new HBox();
        centerBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; "
                + "-fx-border-radius: 20; -fx-background-radius: 20; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 5);");
        root.setCenter(centerBox);

        centerBox.getChildren().addAll(overviewBox, graphBox);



        return new Scene(root, 1920, 1080);
    }
}
