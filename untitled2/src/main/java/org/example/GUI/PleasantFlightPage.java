package org.example.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import org.example.BoardingPass;
import org.example.Database;
import java.net.URL;


/**
 * This page is the last pick up page that the passenger sees
 */
public class PleasantFlightPage {

    /**
     * Attribute of the boarding pass that has been scanned
     */
    static BoardingPass BP = UIManager.boardingPass;


    /**
     * Create scene
     * @param primaryStage
     */
    public void start(Stage primaryStage) {
        Scene scene = createScene();
        primaryStage.setTitle("Pleasant flight");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Scene that contains borderpane with vertical and horizontal boxes
     * @return
     */
    public static Scene createScene() {
        BorderPane root = new BorderPane();


        Text haveAPleasantFlightMessage = new Text("Have a pleasant flight!");
        haveAPleasantFlightMessage.setFont(Font.font(75));


        Text reminderMessage = new Text("Remember to drop your headphones off in " + Database.getNameFromICAO(BP.getDestinationAirport()));
        reminderMessage.setFont(Font.font(50));

        ImageView planeUp = null;
        URL imgUrl = HeadphonesDroppedOffPage.class.getResource("/images/airplane-aircraft-takeoff-take-off-clip-art-plane-b59ead33bdd3e093bae1972ae8d89e05.png");
        if (imgUrl != null) {
            planeUp = new ImageView(new Image(imgUrl.toExternalForm()));
            planeUp.setFitHeight(300);
            planeUp.setPreserveRatio(true);
        }

        VBox vbox2 = new VBox(haveAPleasantFlightMessage);
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setPadding(new Insets(0,0,60,0));



        VBox vbox = new VBox(50);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20,0,30,0));
        vbox.getChildren().addAll(vbox2, reminderMessage);
        if (planeUp != null) {
            vbox.getChildren().add(planeUp);
        }

        root.setCenter(vbox);
        return new Scene(root, 1920, 1080);
    }
}
