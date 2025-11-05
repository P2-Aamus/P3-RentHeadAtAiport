package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class KioskTable {

    private Database db = new Database();


    public ObservableList<ObservableList<String>> getBoardingPassData() {
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
}
