package Admin.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import static javafx.application.Application.launch; // Needed if you removed it

// This class uses the GUI logic from AbstractDataTable
public class BoardingPassTable extends AbstractDataTable {

    // Assuming Database class is now accessible
    private Database db = new Database();

    // -----------------------------------------------------------------
    //  REQUIRED ABSTRACT METHODS (Implementation for BoardingPassTable)
    // -----------------------------------------------------------------

    // 1. Implementation for the window title
    @Override
    protected String getWindowTitle() {
        return "Boarding Pass Records Table"; // Set a meaningful title
    }

    // 2. Implementation for column headers (Assuming 3 columns from earlier)
    @Override
    protected String[] getColumnNames() {
        return new String[]{"BPN", "Origin Aiport", "Dest Airport", "Full name", "Flight Number"};
    }

    // 3. Implementation for the data retrieval
    @Override
    protected ObservableList<ObservableList<String>> getData() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        // Retrieve all rows
        // NOTE: This assumes db.getAllBoardingPasses() returns List<String[]>
        List<String[]> rows = db.getAllBoardingPasses();

        for (String[] row : rows) {
            data.add(FXCollections.observableArrayList(row));
        }

        if (rows.isEmpty()) {
            System.out.println("No boarding pass data found!");
        }

        return data;
    }

    // -----------------------------------------------------------------
    //  APPLICATION ENTRY POINT
    // -----------------------------------------------------------------

    public static void main(String[] args) {
        // This is the call that starts the AbstractDataTable.start(Stage) method.
        launch(args);
    }
}