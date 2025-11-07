package org.example;

import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.example.Kiosk.InstructionMode.DROP_OFF;
import static org.example.Kiosk.useCaseIdentification;
import static org.junit.jupiter.api.Assertions.*;

class KioskTest {


    @Test
    void useCaseIdentification_PickUp() {
        BoardingPass BP = new BoardingPass(56789012, "EKCH", "EKBI", "Andreas", "SK2387");
        Kiosk kiosk = new Kiosk("EKCH");

        Kiosk.InstructionMode result = useCaseIdentification(BP, kiosk);
        assertEquals(Kiosk.InstructionMode.PICK_UP, result);

    }

    @Test
    void useCaseIdentification_DropOff() {
        BoardingPass BP = new BoardingPass(12, "EKCH", "EKBI", "Andreas", "SK2387");
        Kiosk kiosk = new Kiosk("EKBI");

        Database.ins_BP(BP);
        Database.transactionStart(BP.getBPNumber(), Database.getIDFromICAO(kiosk.getAirport()));
        Database.pickUp(BP.getBPNumber(), Database.getIDFromICAO(kiosk.getAirport()));

        Kiosk.InstructionMode result = useCaseIdentification(BP, kiosk);
        assertEquals(Kiosk.InstructionMode.DROP_OFF, result);
    }

    @Test
    void useCaseIdentification_Unknown() {
        BoardingPass BP = new BoardingPass(2312432, "EKCH", "EKBI", "Andreas", "SK2387");
        Kiosk kiosk = new Kiosk("EKYT");

        Kiosk.InstructionMode result = useCaseIdentification(BP, kiosk);
        assertEquals(Kiosk.InstructionMode.UNKNOWN, result);

    }

    @Test
    void BPalreadyStored() throws SQLException {
        BoardingPass BP = new BoardingPass(567890, "EKCH", "EKBI", "Andreas", "SK2387");
        Kiosk kiosk = new Kiosk("EKCH");

        assertFalse(kiosk.BPalreadyStored(BP));
    }

    @Test
    void initTransition() {
        String kioskAirportICAO = "EKBI";
        BoardingPass BP = new BoardingPass(689243, "EKCH", "EKYT", "ANDREAS M", "SK6322");
        Kiosk testKiosk = new Kiosk(kioskAirportICAO);

        assertDoesNotThrow(() -> testKiosk.initTransition(BP));
    }

    @Test
    void validateAirports_OKAY() {
        BoardingPass BP = new BoardingPass(689243, "EKCH", "EKYT", "ANDREAS M", "SK6322");

        assertEquals(Kiosk.AirportVaildation.OKAY, Kiosk.validateAirports(BP));

    }

    @Test
    void validateAirports_INVALID_ORIGIN() {
        BoardingPass BP = new BoardingPass(689243, "EHHE", "EKYT", "ANDREAS M", "SK6322");

        assertEquals(Kiosk.AirportVaildation.INVALID_ORIGIN, Kiosk.validateAirports(BP));
    }

    @Test
    void validateAirports_INVALID_DESTINATION() {
        BoardingPass BP = new BoardingPass(689243, "EKBI", "EKER", "ANDREAS M", "SK6322");

        assertEquals(Kiosk.AirportVaildation.INVALID_DESTINATION, Kiosk.validateAirports(BP));
    }

    @Test
    void QRScan() throws FrameGrabber.Exception {
        String[] res = new String[5];
        res[0] = "836892473";
        res[1] = "EKBI";
        res[2] = "EKCH";
        res[3] = "John Doe";
        res[4] = "SK2739";

        assertArrayEquals(res, Kiosk.QRScan());
    }

    @Test
    void getAirport() {
        Kiosk kiosk = new Kiosk("EKBI");

        assertEquals("EKBI", kiosk.getAirport());
    }

    @Test
    void sufficientData_FALSE() {
        String[] res = new String[3];

        assertFalse(Kiosk.sufficientData(res));
    }

    @Test
    void sufficientData_TRUE() {
        String[] res = new String[5];

        assertTrue(Kiosk.sufficientData(res));
    }


    @Test
    void pickUp() {
        Kiosk kiosk = new Kiosk("EKBI");

        BoardingPass BP = new BoardingPass(836243, "EKCH", "ERYT", "ANDREAS M", "SK6322");

        assertDoesNotThrow(() -> Kiosk.pickUp(BP, kiosk));
    }


}