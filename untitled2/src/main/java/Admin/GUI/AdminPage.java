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
        buttonBox.setPadding(new Insets(0,0,0,1000));
        root.setTop(buttonBox);


        HBox graphBox = new HBox(Graph);
        graphBox.setPadding(new Insets(70, 500, 40, 100));


        VBox overviewBox = new VBox(overview);
        overviewBox.setPadding(new Insets(70,40,40,20));


        HBox centerBox = new HBox();
        root.setCenter(centerBox);

        centerBox.getChildren().addAll(graphBox, overviewBox);



        return new Scene(root, 1920, 1080);
    }
}
