package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class TransactionsTable extends AbstractDataTable {

    @Override
    protected String getWindowTitle() {
        return "Transactions Record Table";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "HeadphonesID", "OriginKioskID", "DestKioskID", "Status"};
    }

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