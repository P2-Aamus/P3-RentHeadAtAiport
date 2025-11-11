package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * This is the kiosk table, that inherits the frontend from the abstract data table
 */
public class KioskTable extends AbstractDataTable {

    /**
     *
     * @return the title
     */
    @Override
    protected String getWindowTitle() {
        return "Kiosk Records Table";
    }

    /**
     *
     * @return the relevant column names tied to the kiosk
     */
    @Override
    protected String[] getColumnNames() {
        return new String[]{"ID", "Aiport", "numOfAvaibleHP", "Airport Name"};
    }

    /**
     *
     * @return a list that is fetched from the database class
     */
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();


        List<String[]> rows = Database.getKioskData();

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