package Admin.GUI;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;

public abstract class AbstractDataTable extends Application {

    protected TableView<ObservableList<String>> tableView = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        setupTable();
        loadData();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f7fa;");

        // Header
        VBox header = createHeader();

        // Table container with shadow effect
        VBox tableContainer = new VBox(10);
        tableContainer.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);" +
                        "-fx-padding: 20;"
        );

        tableContainer.getChildren().add(tableView);

        root.getChildren().addAll(header, tableContainer);

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getWindowTitle());
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox(10);

        // Title
        Label titleLabel = new Label(getWindowTitle());
        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2c3e50;"
        );

        header.getChildren().add(titleLabel);

        return header;
    }

    protected abstract String[] getColumnNames();
    protected abstract ObservableList<ObservableList<String>> getData();
    protected abstract String getWindowTitle();

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

        tableView.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-insets: 0;" +
                        "-fx-padding: 0;"
        );

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label placeholder = new Label("No data available");
        placeholder.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 14px;");
        tableView.setPlaceholder(placeholder);
    }

    private void loadData() {
        tableView.setItems(getData());
    }
}