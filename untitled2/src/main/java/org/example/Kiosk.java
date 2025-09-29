package org.example;

// Here is the class org.example.Kiosk
public class Kiosk {

    // Attributes
    private int id;
    private String airport;
    public int numOfAvailabeHP;
    private String locationOfAirport;

    //Constructor
    public Kiosk(int id, String airport, int numOfAvailabeHP, String locationOfAirport) {
        id = 0;
        airport = "";
        numOfAvailabeHP = 0;
        locationOfAirport = "";
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