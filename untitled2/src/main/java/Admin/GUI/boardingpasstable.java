package Admin.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class boardingpasstable extends Application {

    private TableView<ObservableList<String>> tableView = new TableView<>();
    private Database db = new Database();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setupTable();
        loadData(); // Load data from database

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 950, 500);

        primaryStage.setTitle("Admin Dashboard - Boarding Passes");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupTable() {
        String[] columnNames = {"BPN", "Origin Airport", "Dest Airport", "Passenger Name", "Flight Number"};

        for (int i = 0; i < columnNames.length; i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(param ->
                    new javafx.beans.property.SimpleStringProperty(param.getValue().get(colIndex))
            );
            column.setPrefWidth(180);
            tableView.getColumns().add(column);
        }
    }

    private void loadData() {
        tableView.getItems().clear();

        List<String[]> rows = db.getAllBoardingPasses();
        for (String[] row : rows) {
            ObservableList<String> obsRow = FXCollections.observableArrayList(row);
            tableView.getItems().add(obsRow);
        }

        if (rows.isEmpty()) {
            System.out.println("No boarding pass data found!");
        }
    }
}
