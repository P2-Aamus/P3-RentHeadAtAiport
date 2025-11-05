package Admin.GUI;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class AdminPage extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        Scene scene = createScene();
        primaryStage.setTitle("Headphones dropped off");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene createScene() {
        BorderPane root = new BorderPane();






        return new Scene(root, 1920, 1080);
    }
}
