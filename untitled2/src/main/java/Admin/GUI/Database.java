package Admin.GUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/main";
    private static final String USER = "root";
    private static final String PASS = "Sodabobs123?";

    /**
     * Fetches all boarding pass data from the database.
     * @return A list of String[] rows, each representing one record.
     */
    public List<String[]> getAllBoardingPasses() {
        List<String[]> data = new ArrayList<>();
        String sql = "SELECT BPN, origin_airport, dest_airport, psg_name, flt_nr FROM boarding_pass";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[5];
                row[0] = String.valueOf(rs.getInt("BPN"));
                row[1] = rs.getString("origin_airport");
                row[2] = rs.getString("dest_airport");
                row[3] = rs.getString("psg_name");
                row[4] = rs.getString("flt_nr");
                data.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching boarding pass data: " + e.getMessage());
        }

        return data;
    }
}
