package Admin.GUI;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import org.example.GUI.UIButton;

import java.io.File;
import java.io.FileOutputStream;

/**
 * The {@code AbstractDataTable} class provides a reusable base for creating
 * JavaFX-based data table windows within the Admin GUI.
 *
 * <p>This abstract class defines the structure and shared functionality
 * for displaying data from the database tables in a unified table view.
 * Subclasses such as {@link HeadphonesTable}, {@link KioskTable}, {@link BoardingPassTable} and
 * {@link TransactionsTable} implement the abstract methods to specify
 * their own table columns, window titles, and data sources.</p>
 *
 * <p>Features include:</p>
 * <ul>
 *     <li>Automatic table column setup based on subclass definitions</li>
 *     <li>Dynamic loading and refreshing of data</li>
 *     <li>PDF export functionality using iText</li>
 *     <li>Navigation between related tables</li>
 * </ul>
 *
 * @see Database
 * @see HeadphonesTable
 * @see KioskTable
 * @see TransactionsTable
 * @see BoardingPassTable
 */
public abstract class AbstractDataTable extends Application {

    /** The JavaFX table displays data rows as observable lists. */
    protected TableView<ObservableList<String>> tableView = new TableView<>();

    /**
     * Launching the JavaFX window.
     *
     * @param primaryStage the primary window stage.
     */
    @Override
    public void start(Stage primaryStage) {
        setupTable();
        loadData();

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f7fa;");

        HBox navBar = createNavigationBar(primaryStage);
        VBox header = createHeader(primaryStage);

        VBox tableContainer = new VBox(10);
        tableContainer.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);" +
                        "-fx-padding: 20;"
        );
        tableContainer.getChildren().add(tableView);

        root.getChildren().addAll(navBar, header, tableContainer);

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle(getWindowTitle());
        primaryStage.show();
    }

    /**
     * A navigation bar with buttons for switching between different tables.
     *
     * @param stage the current JavaFX stage.
     * @return an HBox containing navigation buttons.
     */
    private HBox createNavigationBar(Stage stage) {
        Button boardingPassBtn = new Button("Boarding Pass");
        Button headphonesBtn = new Button("Headphones");
        Button kioskBtn = new Button("Kiosk");
        Button transactionsBtn = new Button("Transactions");

        for (Button btn : new Button[]{boardingPassBtn, headphonesBtn, kioskBtn, transactionsBtn}) {
            btn.setStyle(
                    "-fx-background-color: #808080;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-size: 14px;" +
                            "-fx-background-radius: 8;"
            );
            btn.setOnMouseEntered(e ->
                    btn.setStyle("-fx-background-color: #808080; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 8; -fx-cursor: hand;"));
            btn.setOnMouseExited(e ->
                    btn.setStyle("-fx-background-color: #808080; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 8;"));
        }

        boardingPassBtn.setOnAction(e -> openWindow(new BoardingPassTable(), stage));
        headphonesBtn.setOnAction(e -> openWindow(new HeadphonesTable(), stage));
        kioskBtn.setOnAction(e -> openWindow(new KioskTable(), stage));
        transactionsBtn.setOnAction(e -> openWindow(new TransactionsTable(), stage));

        HBox nav = new HBox(15, boardingPassBtn, headphonesBtn, kioskBtn, transactionsBtn);
        nav.setAlignment(Pos.CENTER);
        nav.setPrefHeight(60);
        nav.setSpacing(60);
        nav.setStyle("-fx-background-color: #808080; -fx-background-radius: 8;");
        return nav;
    }

    /**
     * Opens a new window displaying the specified table type.
     *
     * @param table the {@code AbstractDataTable} subclass to open.
     * @param stage the current application stage.
     */
    private void openWindow(AbstractDataTable table, Stage stage) {
        try {
            table.start(stage);
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Uh oh: " + ex.getMessage()).showAndWait();
        }
    }

    /**
     * Creates the header of the window, which includes the title and buttons.
     *
     * @param stage the current JavaFX stage.
     * @return a VBox representing the header layout.
     */
    private VBox createHeader(Stage stage) {
        VBox header = new VBox(10);

        Label titleLabel = new Label(getWindowTitle());
        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #2c3e50;"
        );

        UIButton refreshBtn = new UIButton(90, 190, "Refresh");
        refreshBtn.setScaleY(0.4);
        refreshBtn.setScaleX(0.4);
        refreshBtn.setOnMousePressed(e -> refreshTable());

        UIButton downloadBtn = new UIButton(80, 190, "Download PDF");
        downloadBtn.setScaleY(0.4);
        downloadBtn.setScaleX(0.4);
        downloadBtn.setOnMousePressed(e -> exportTableToPDF(stage));

        Group refreshGroup = new Group(refreshBtn);
        Group downloadGroup = new Group(downloadBtn);

        HBox headerBar = new HBox(20, titleLabel, refreshGroup, downloadGroup);
        headerBar.setSpacing(10);
        header.getChildren().add(headerBar);

        return header;
    }

    /**
     * Reloads data from the database and updates the table.
     * Displays an alert if successful or failure.
     */
    private void refreshTable() {
        try {
            tableView.setItems(getData());
            new Alert(Alert.AlertType.INFORMATION, "Table refreshed successfully!").showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to refresh table: " + ex.getMessage()).showAndWait();
        }
    }

    /**
     * Exports the current table data to a PDF file.
     *
     * @param stage the current JavaFX stage.
     */
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

    /**
     * Returns the column names.
     *
     * @return an array of column name strings.
     */
    protected abstract String[] getColumnNames();

    /**
     * Fetches and returns the data to the table.
     *
     * @return an {@link ObservableList} of rows, each represented as
     *         an {@code ObservableList<String>}.
     */
    protected abstract ObservableList<ObservableList<String>> getData();

    /**
     * Returns the window title for the specific subclass.
     *
     * @return a {@code String} showing the window title.
     */
    protected abstract String getWindowTitle();

    /**
     * Configures the table structure by creating columns.
     * based on {@link #getColumnNames()}.
     */
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

    /**
     * Loads the table data when the window starts or refreshes.
     */
    private void loadData() {
        tableView.setItems(getData());
    }
}
