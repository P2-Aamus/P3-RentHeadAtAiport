package org.example;

// Here is the class org.example.Kiosk
public class Kiosk {

    // Attributes
    private final int id;
    private final String airport;
    public int numOfAvailabeHP;
    private final String locationOfAirport;

    //Constructor
    public Kiosk(int id, String airport, int numOfAvailabeHP, String locationOfAirport) {
        this.id = id;
        this.airport = airport;
        this.numOfAvailabeHP = numOfAvailabeHP;
        this.locationOfAirport = locationOfAirport;
    }


    //Getters and Setters methods
    public int getId() {
        return id;
    }

    public String getAirport() {

        return airport;
    }

    public int getNumOfAvailabeHP() {

        return numOfAvailabeHP;
    }

    public String getLocationOfAirport() {
        return locationOfAirport;
    }
}


/*

okay jeg har en ide til hvordan man kan gøre det. lad mig lige cook hurtigt.
jeg er meget på hardcoding af lufthavne siden der alligevel ikke er så mange

jeg tænker subklasser.

*/