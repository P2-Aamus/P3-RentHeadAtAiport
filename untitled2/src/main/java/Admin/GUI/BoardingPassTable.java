package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * The {@code BoardingPassTable} class is a JavaFX window
 * that displays data from the {@code boarding_pass} database table.
 *
 * <p>This class is extended by {@link AbstractDataTable} and will provide
 * implementations for the boarding pass records — which will include
 *  the window title, column names, and data loading.</p>
 *
 * <p>It retrieves its data from the {@link Database#getAllBoardingPasses()} method,
 * which will return a list of headphone entries from the database.</p>
 *
 * @see Database
 * @see AbstractDataTable
 */
public class BoardingPassTable extends AbstractDataTable {

    /**
     * Returns the title of the JavaFX window for this specific table.
     *
     * @return a string representing the window title,
     *         which is {@code "Boarding Pass Records Table"}.
     */
    @Override
    protected String getWindowTitle() {
        return "Boarding Pass Records Table";
    }

    /**
     * Returns the names of the columns to be displayed in the table.
     *
     * @return an array of column header names:
     *         {@code "BPN", "Origin Aiport", "Dest Airport", "Full name", "Flight Number"}.
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "Origin Aiport", "Dest Airport", "Full name", "Flight Number"};
    }

    /**
     * Fetches boarding pass data from the database and converts it
     * into an {@link ObservableList} suitable for use in a JavaFX TableView.
     *
     * @return an {@code ObservableList} of {@code ObservableList<String>},
     *         where each inner list represents one boarding pass record.
     */
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        List<String[]> rows = Database.getAllBoardingPasses();

        for (String[] row : rows) {
            data.add(FXCollections.observableArrayList(row));
        }

        if (rows.isEmpty()) {
            System.out.println("No boarding pass data found!");
        }

        return data;
    }


    public static void main(String[] args) {
        launch(args);
    }
}