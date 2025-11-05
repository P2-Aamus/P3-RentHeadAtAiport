package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class BoardingPassTable extends AbstractDataTable {

    private Database db = new Database();

    @Override
    protected String getWindowTitle() {
        return "Boarding Pass Records Table";
    }

    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "Origin Aiport", "Dest Airport", "Full name", "Flight Number"};
    }

    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        List<String[]> rows = db.getAllBoardingPasses();

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