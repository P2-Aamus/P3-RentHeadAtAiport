package org.example;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void ins_BP() {

        BoardingPass BP = new BoardingPass(83689243, "EKCH", "ERYT", "ANDREAS M", "SK6322");

        assertDoesNotThrow(() ->
                Database.ins_BP(BP));
    }

    @Test
    void transactionStart() {
        int testBPN = 83689243;
        int testKioskID = 10;
        assertDoesNotThrow(() ->
                Database.transactionStart(testBPN, testKioskID)
        );
    }

    //NOT YET RUN
    @Test
    void pickUp() {
        BoardingPass BP = new BoardingPass(83689243, "EKCH", "EKYT", "ANDREAS M", "SK6322");
        Kiosk originKiosk = new Kiosk("EKCH");
        Database.ins_BP(BP);
        Database.transactionStart(BP.getBPNumber() ,Database.getIDFromICAO(originKiosk.getAirport()));
        Database.pickUp(BP.getBPNumber(), Database.getIDFromICAO(originKiosk.getAirport()));

        assertDoesNotThrow(() -> Database.pickUp(BP.getBPNumber(), Database.getIDFromICAO(originKiosk.getAirport())));
    }

    //NOT RUN YET
    @Test
    void dropOff() {
        BoardingPass BP = new BoardingPass(836843, "EKCH", "EKYT", "Randers Martens", "SK6322");
        Kiosk originKiosk = new Kiosk("EKCH");
        Kiosk destKiosk = new Kiosk("EKYT");

        Database.ins_BP(BP);
        Database.transactionStart(BP.getBPNumber(), Database.getIDFromICAO(originKiosk.getAirport()));
        Database.pickUp(BP.getBPNumber(), Database.getIDFromICAO(originKiosk.getAirport()));

        assertDoesNotThrow(() -> Database.dropOff(BP.getBPNumber(), Database.getIDFromICAO(destKiosk.getAirport())));

    }

    @Test
    void getNameFromICAO() {
        String ICAO = "EKBI";

        assertEquals("Billund",  Database.getNameFromICAO(ICAO));
    }

    @Test
    void getIDFromICAO() {
        String ICAO = "EKBI";

        assertEquals(2,  Database.getIDFromICAO(ICAO));
    }

    @Test
    void getBPN() throws SQLException {
        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(0, 1);

        assertEquals(exp, Database.getBPN());
    }

    @Test
    void deleteLastBP() {

        BoardingPass BP = new BoardingPass(83689243, "EKCH", "EKYT", "Randers Martens", "SK6322");

        assertDoesNotThrow(() -> Database.deleteLastBP(BP));
    }

    @Test
    void isValidAirport() {
        String ICAO3 = "OKEC";

        assertEquals(false, Database.isValidAirport(ICAO3));
    }
}