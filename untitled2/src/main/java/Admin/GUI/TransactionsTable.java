package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * This is the transaction table, that inherits the frontend from the abstract data table
 */
public class TransactionsTable extends AbstractDataTable {

    /**
     *
     * @return the title for the table
     */
    @Override
    protected String getWindowTitle() {
        return "Transactions Record Table";
    }

    /**
     *
     * @return the relevant column names tied to the transactions
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "HeadphonesID", "OriginKioskID", "DestKioskID", "Status"};
    }

    /**
     *
     * @return a list that is fetched from the database class
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