package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * this is the boarding pass table, that inherits  the frontend from the abstract data table
 */
public class BoardingPassTable extends AbstractDataTable {

    /**
     *
     * @return returns the title
     */
    @Override
    protected String getWindowTitle() {
        return "Boarding Pass Records Table";
    }

    /**
     *
     * @return returns the relevant column names tied to the boarding pass
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "Origin Aiport", "Dest Airport", "Full name", "Flight Number"};
    }

    /**
     *
     * @return returns a list that is fetched from the database class
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