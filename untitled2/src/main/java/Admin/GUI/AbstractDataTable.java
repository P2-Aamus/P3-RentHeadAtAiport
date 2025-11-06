package Admin.GUI;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

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
        VBox header = createHeader(primaryStage);

        // Table container
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

    /** HEADER BAR (title + buttons) **/
    private VBox createHeader(Stage stage) {
        VBox header = new VBox(10);

        Label titleLabel = new Label(getWindowTitle());
        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2c3e50;"
        );

        // 🔄 Refresh Button
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setStyle(
                "-fx-background-color: #2ecc71;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 15 8 15;"
        );
        refreshBtn.setOnAction(e -> refreshTable());

        // ⬇️ Download PDF Button
        Button downloadBtn = new Button("Download PDF");
        downloadBtn.setStyle(
                "-fx-background-color: #3498db;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 15 8 15;"
        );
        downloadBtn.setOnAction(e -> exportTableToPDF(stage));

        HBox headerBar = new HBox(10, titleLabel, refreshBtn, downloadBtn);
        headerBar.setSpacing(20);
        header.getChildren().add(headerBar);

        return header;
    }

    /** 🔄 Reload data from DB **/
    private void refreshTable() {
        try {
            tableView.setItems(getData());
            new Alert(Alert.AlertType.INFORMATION, "Table refreshed successfully!").showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to refresh table: " + ex.getMessage()).showAndWait();
        }
    }

    /** ⬇️ Export Table to PDF **/
    private void exportTableToPDF(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName(getWindowTitle().replaceAll("\\s+", "_") + ".pdf");
        File file = fileChooser.showSaveDialog(stage);
        if (file == null) return;

        try (Document doc = new Document()) {
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            Paragraph title = new Paragraph(
                    getWindowTitle(),
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)
            );
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));

            String[] columns = getColumnNames();
            PdfPTable pdfTable = new PdfPTable(columns.length);
            pdfTable.setWidthPercentage(100);

            for (String col : columns) {
                PdfPCell cell = new PdfPCell(
                        new Phrase(col, FontFactory.getFont(FontFactory.HELVETICA_BOLD))
                );
                pdfTable.addCell(cell);
            }

            for (ObservableList<String> row : tableView.getItems()) {
                for (String cellData : row) {
                    pdfTable.addCell(cellData == null ? "" : cellData);
                }
            }

            doc.add(pdfTable);
            doc.close();

            new Alert(Alert.AlertType.INFORMATION,
                    "PDF saved to:\n" + file.getAbsolutePath()
            ).showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR,
                    "Failed to save PDF:\n" + ex.getMessage()
            ).showAndWait();
        }
    }

    /** Abstract methods for subclasses **/
    protected abstract String[] getColumnNames();
    protected abstract ObservableList<ObservableList<String>> getData();
    protected abstract String getWindowTitle();

    /** Setup TableView **/
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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label placeholder = new Label("No data available");
        placeholder.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 14px;");
        tableView.setPlaceholder(placeholder);
    }

    private void loadData() {
        tableView.setItems(getData());
    }
}
