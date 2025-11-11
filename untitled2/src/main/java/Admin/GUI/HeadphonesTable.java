package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * this is the headphones table, that inherits the frontend from the abstract data table
 */
public class HeadphonesTable extends AbstractDataTable {

    /**
     *
     * @return returns the title
     */
    @Override
    protected String getWindowTitle() {
        return "Headphones Records Table";
    }

    /**
     *
     * @return the relevant column names tied to the headphones
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Status", "Location", " Battery"};
    }

    /**
     *
     * @return a list that is fetched from the database class
     */
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