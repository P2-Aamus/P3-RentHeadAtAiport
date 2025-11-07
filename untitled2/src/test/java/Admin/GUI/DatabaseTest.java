package Admin.GUI;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void getAllBoardingPasses() {
        ArrayList<String[]> exp = new ArrayList<>();
        String[] row = new String[5];
        row[0] = "1";
        row[1] = "EKBI";
        row[2] = "EKYT";
        row[3] = "Ale";
        row[4] = "123";
        exp.add(row);

        List<String[]> actual = Database.getAllBoardingPasses();
        assertEquals(exp.size(), actual.size());

        for (int i = 0; i < exp.size(); i++) {
            assertArrayEquals(exp.get(i), actual.get(i));
        }
    }

    @Test
    void getKioskData() {
        ArrayList<String[]> exp = new ArrayList<>();

        exp.add(new String[]{"1", "EKCH", "10", "Copenhagen Kastrup"});
        exp.add(new String[]{"2", "EKBI", "9", "Billund"});
        exp.add(new String[]{"3", "EKAH", "10", "Aarhus"});
        exp.add(new String[]{"4", "ENGM", "10", "Oslo Gardermoen"});
        exp.add(new String[]{"5", "ENBR", "10", "Bergen Flesland"});
        exp.add(new String[]{"6", "ENVA", "10", "Trondheim Værnes"});
        exp.add(new String[]{"7", "EKYT", "10", "Aalborg"});
        exp.add(new String[]{"8", "ENTC", "10", "Tromsø"});
        exp.add(new String[]{"9", "ESGG", "10", "Goteborg Landvetter"});
        exp.add(new String[]{"10", "ESSA", "10", "Stockholm Arlanda"});
        exp.add(new String[]{"11", "ESMS", "10", "Malmo "});
        exp.add(new String[]{"12", "ESSB", "10", "Stockholm Bromma"});

        List<String[]> actual = Database.getKioskData();
        assertEquals(exp.size(), actual.size());

        for (int i = 0; i < exp.size(); i++) {
            assertArrayEquals(exp.get(i), actual.get(i));
        }
    }

    @Test
    void getTransactions() {
        ArrayList<String[]> exp = new ArrayList<>();
        String[] row = new String[5];
        row[0] = "1";
        row[1] = "1";
        row[2] = "1";
        row[3] = "2";
        row[4] = "1";
        exp.add(row);

        List<String[]> actual = Database.getTransactions();
        assertEquals(exp.size(), actual.size());

        for (int i = 0; i < exp.size(); i++) {
            assertArrayEquals(exp.get(i), actual.get(i));
        }
    }

    @Test
    void getHeadphonesData() {
        ArrayList<String[]> exp = new ArrayList<>();
        String[] row = new String[4];
        row[0] = "1";
        row[1] = "1";
        row[2] = "EKBI";
        row[3] = "100";
        exp.add(row);

        List<String[]> actual = Database.getHeadphonesData();
        assertEquals(exp.size(), actual.size());

        for (int i = 0; i < exp.size(); i++) {
            assertArrayEquals(exp.get(i), actual.get(i));
        }
    }
}