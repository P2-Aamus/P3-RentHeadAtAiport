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
               BP.ins_BP());
    }

    @Test
    void transactionStart() {
        int testBPN = 83689243;
        int testKioskID = 10;
        assertDoesNotThrow(() ->
                Transactions.transactionStart(testBPN, testKioskID)
        );
    }

    //NOT YET RUN
    @Test
    void pickUp() throws SQLException {
        BoardingPass BP = new BoardingPass(83689243, "EKCH", "EKYT", "ANDREAS M", "SK6322");
        Kiosk originKiosk = new Kiosk("EKCH");
        //Database.ins_BP(BP);
        Transactions.transactionStart(BP.getBPNumber() ,Kiosk.getIDFromICAO(originKiosk.getAirport()));
        Database.pickUp(BP.getBPNumber(), Kiosk.getIDFromICAO(originKiosk.getAirport()));

        assertDoesNotThrow(() -> Database.pickUp(BP.getBPNumber(), Kiosk.getIDFromICAO(originKiosk.getAirport())));
    }

    //NOT RUN YET
    @Test
    void dropOff() throws SQLException {
        BoardingPass BP = new BoardingPass(836843, "EKCH", "EKYT", "Randers Martens", "SK6322");
        Kiosk originKiosk = new Kiosk("EKCH");
        Kiosk destKiosk = new Kiosk("EKYT");

        //Database.ins_BP(BP);
        Transactions.transactionStart(BP.getBPNumber(), Kiosk.getIDFromICAO(originKiosk.getAirport()));
        Database.pickUp(BP.getBPNumber(), Kiosk.getIDFromICAO(originKiosk.getAirport()));

        assertDoesNotThrow(() -> Database.dropOff(BP.getBPNumber(), Kiosk.getIDFromICAO(destKiosk.getAirport())));

    }

    @Test
    void getNameFromICAO() {
        String ICAO = "EKBI";

        //assertEquals("Billund",  Database.getNameFromICAO(ICAO));
    }

    @Test
    void getIDFromICAO() {
        String ICAO = "EKBI";

        assertEquals(2, Kiosk.getIDFromICAO(ICAO));
    }

    @Test
    void getBPN() throws SQLException {
        ArrayList<Integer> exp = new ArrayList<>();
        exp.add(0, 1);

        assertEquals(exp, BoardingPass.getBPN());
    }

    @Test
    void deleteLastBP() {

        BoardingPass BP = new BoardingPass(83689243, "EKCH", "EKYT", "Randers Martens", "SK6322");

        assertDoesNotThrow(() -> BP.deleteLastBP());
    }

    @Test
    void isValidAirport() {
        String ICAO3 = "OKEC";

        assertEquals(false, Kiosk.isValidAirport(ICAO3));
    }
}