package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * The {@code HeadphonesTable} class is a JavaFX window
 * that displays data from the {@code headphones} database table.
 *
 * <p>This class is extended by {@link AbstractDataTable} and will provide
 * implementations for  the headphone records — which will include
 *  the window title, column names, and data loading.</p>
 *
 * <p>It retrieves its data from the {@link Database#getHeadphonesData()} method,
 * which will return a list of headphone entries from the database.</p>
 *
 * @see Database
 * @see AbstractDataTable
 */
public class HeadphonesTable extends AbstractDataTable {

    /**
     * Returns the title of the JavaFX window for this specific table.
     *
     * @return a string representing the window title,
     *         which is {@code "Headphones Records Table"}.
     */
    @Override
    protected String getWindowTitle() {
        return "Headphones Records Table";
    }

    /**
     * Returns the names of the columns to be displayed in the table.
     *
     * @return an array of column header names:
     *         {@code "ID", "Status", "Location", "Battery"}.
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Status", "Location", " Battery"};
    }

    /**
     * Fetches headphone data from the database and converts it
     * into an {@link ObservableList} suitable for use in a JavaFX TableView.
     *
     * @return an {@code ObservableList} of {@code ObservableList<String>},
     *         where each inner list represents one headphone record.
     */
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


        List<String[]> rows = Database.getHeadphonesData();

        for (String[] row : rows) {
            data.add(FXCollections.observableArrayList(row));
        }

        if (rows.isEmpty()) {
            System.out.println("No data found!");
        }

        return data;
    }

    public static void main(String[] args) {
        launch(args);
    }
}