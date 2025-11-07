package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardingPassTest {

    BoardingPass BP = new BoardingPass(3415, "ESSA", "EKYT", "Anerwfwd", "DIN MOR 3023");

    @Test
    void getBPNumber() {
        assertEquals(3415, BP.getBPNumber());
    }

    @Test
    void getOriginAirport() {
        assertEquals("ESSA", BP.getOriginAirport());
    }

    @Test
    void getDestinationAirport() {
        assertEquals("EKYT", BP.getDestinationAirport());
    }

    @Test
    void getPsgName() {
        assertEquals("Anerwfwd", BP.getPsgName());
    }

    @Test
    void getfltNr() {
        assertEquals("DIN MOR 3023", BP.getfltNr());
    }
}