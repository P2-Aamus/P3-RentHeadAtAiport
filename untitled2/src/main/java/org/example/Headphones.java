package org.example;

public class Headphones {
    private int HID;
    //HID = Headphone ID
    private int Status;
    private String Location;
    private int Battery;

    //Constructor
    public Headphones(int HID, int Status, String Location, int Battery){
        HID = 0;
        Status = 0;
        Location = "";
        Battery = 0;
        
    }

    //Methods

    public int getHID() {
        return HID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocation() {
        return Location;
    }

    public int getBattery() {
        return Battery;
    }



}