package org.example;

/**
 * Allows to create boarding pass objects
 */
public class BoardingPass{

    //Attributes
    /** Unique number for each boarding pass*/
     private int BPNumber;
     /** 4-letter ICAO code of the origin airport */
     private String originAirport;
    /** 4-letter ICAO code of the destination airport */
     private String destinationAirport;
    /** The name of the passenger */
     private String psgName;
    /** The flight number given on the boarding pass */
     private String fltNr;

     //constructor
    /** Constructs a boarding pass object out of the given parameters */
    public BoardingPass(int BPN, String ori, String dest, String name, String flt){
        BPNumber = BPN;
        originAirport = ori;
        destinationAirport = dest;
        psgName = name;
        fltNr = flt;
    }

    //methods

    /**
     * Gets the boarding pass number
     * @return Boarding pass number
     */
    public int getBPNumber(){
        return BPNumber;
    }

    /**
     * Gets the origin airport
     * @return Origin airport
     */
    public String getOriginAirport(){
        return originAirport;
    }

    /**
     * Gets the destination airport
     * @return Destination airport
     */
    public String getDestinationAirport() {
        return destinationAirport;
    }

    /**
     * Gets the passenger's name
     * @return Passenger's name
     */
    public String getPsgName() {
        return psgName;
    }

    /**
     * Gets the flight number
     * @return Flight number
     */
    public String getfltNr(){
        return fltNr;
    }
}