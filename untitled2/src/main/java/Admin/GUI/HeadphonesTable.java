package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class HeadphonesTable extends AbstractDataTable {

    @Override
    protected String getWindowTitle() {
        return "Headphones Records Table";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Status", "Location", " Battery"};
    }

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