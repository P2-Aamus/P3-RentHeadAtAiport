package Admin.GUI;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

public abstract class AbstractDataTable extends Application {

    protected TableView<ObservableList<String>> tableView = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        setupTable();
        loadData();

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 950, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getWindowTitle());
        primaryStage.show();
    }

    // 🔹 Must be implemented by subclasses
    protected abstract String[] getColumnNames();
    protected abstract ObservableList<ObservableList<String>> getData();
    protected abstract String getWindowTitle();

    // 🔹 Common setup logic
    private void setupTable() {
        String[] columnNames = getColumnNames();

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

    // 🔹 Common data loading logic
    private void loadData() {
        tableView.setItems(getData());
    }
}
