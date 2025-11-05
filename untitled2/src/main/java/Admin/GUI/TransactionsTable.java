package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class TransactionsTable extends AbstractDataTable {

    private Database db = new Database();

    @Override
    protected String getWindowTitle() {
        return "Transactions";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "HeadphonesID", "OriginKioskID", "DestKioskID", "Status"};
    }

    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        List<String[]> rows = db.getTransactions();

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