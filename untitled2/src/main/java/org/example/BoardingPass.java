package org.example;

public class BoardingPass{

    //Attributes
     private int BPNumber;
     private String originAirport;
     private String destinationAirport;
     private String psgName;
     private String fltNr;

     //constructor
    public BoardingPass(int BPN, String ori, String dest, String name, String flt){
        BPNumber = BPN;
        originAirport = ori;
        destinationAirport = dest;
        name = psgName;
        fltNr = flt;
    }

    //methods
    public int getBPNumber(){
        return BPNumber;
    }

    public String getOriginAirport(){
        return originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getPsgName() {
        return psgName;
    }

    public String getfltNr(){
        return fltNr;
    }
}