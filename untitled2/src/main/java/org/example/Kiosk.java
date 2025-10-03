package org.example;

// Here is the class org.example.Kiosk
public class Kiosk {

    // Attributes
    private final int id;
    private final String airport;
    public int numOfAvailabeHP;
    private final String locationAtAirport;

    //Constructor
    public Kiosk(int id, String airport, int numOfAvailabeHP, String locationAtAirport) {
        this.id = id;
        this.airport = airport;
        this.numOfAvailabeHP = numOfAvailabeHP;
        this.locationAtAirport = locationAtAirport;
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
        return locationAtAirport;
    }
}


