package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * The {@code TransactionsTable} class is a JavaFX window
 * that displays data from the {@code transactions} database table.
 *
 * <p>This class is extended by {@link AbstractDataTable} and will provide
 * implementations for the transactions records — which will include
 *  the window title, column names, and data loading.</p>
 *
 * <p>It retrieves its data from the {@link Database#getTransactions()} method,
 * which will return a list of transactions entries from the database.</p>
 *
 * @see Database
 * @see AbstractDataTable
 */
public class TransactionsTable extends AbstractDataTable {

    /**
     * Returns the title of the JavaFX window for this specific table.
     *
     * @return a string representing the window title,
     *         which is {@code "Transactions Records Table"}.
     */
    @Override
    protected String getWindowTitle() {
        return "Transactions Record Table";
    }

    /**
     * Returns the names of the columns to be displayed in the table.
     *
     * @return an array of column header names:
     *         {@code "BPN", "HeadphonesID", "OriginKioskID", "DestKioskID", "Status"}.
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "HeadphonesID", "OriginKioskID", "DestKioskID", "Status"};
    }

    /**
     * Fetches transactions data from the database and converts it
     * into an {@link ObservableList} suitable for use in a JavaFX TableView.
     *
     * @return an {@code ObservableList} of {@code ObservableList<String>},
     *         where each inner list represents one transaction record.
     */
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        List<String[]> rows = Database.getTransactions();

        for (String[] row : rows) {
            data.add(FXCollections.observableArrayList(row));
        }

        if (rows.isEmpty()) {
            System.out.println("No transactions data found!");
        }

        return data;
    }


    public static void main(String[] args) {
        launch(args);
    }
}