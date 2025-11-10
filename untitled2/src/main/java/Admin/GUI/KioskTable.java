package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * The {@code KioskTable} class is a JavaFX window
 * that displays data from the {@code kiosk} database table.
 *
 * <p>This class is extended by {@link AbstractDataTable} and will provide
 * implementations for the kiosk records — which will include
 *  the window title, column names, and data loading.</p>
 *
 * <p>It retrieves its data from the {@link Database#getKioskData()} method,
 * which will return a list of kiosk entries from the database.</p>
 *
 * @see Database
 * @see AbstractDataTable
 */
public class KioskTable extends AbstractDataTable {

    /**
     * Returns the title of the JavaFX window for this specific table.
     *
     * @return a string representing the window title,
     *         which is {@code "Kiosk Records Table"}.
     */
    @Override
    protected String getWindowTitle() {
        return "Kiosk Records Table";
    }

    /**
     * Returns the names of the columns to be displayed in the table.
     *
     * @return an array of column header names:
     *         {@code "ID", "Aiport", "numOfAvaibleHP", "Airport Name"}.
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Aiport", "numOfAvaibleHP", "Airport Name"};
    }

    /**
     * Fetches kiosk data from the database and converts it
     * into an {@link ObservableList} suitable for use in a JavaFX TableView.
     *
     * @return an {@code ObservableList} of {@code ObservableList<String>},
     *         where each inner list represents one kiosk record.
     */
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


        List<String[]> rows = Database.getKioskData();

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