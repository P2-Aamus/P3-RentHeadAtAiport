package org.example;

public class BoardingPass{

    //Attributes
     private int BPNumber;
     private string originAirport;
     private string destinationAirport;
     private string psgName;
     private string fltNr;

     //constructor
    public BoardingPass(int BPN, string ori, string dest, string name, string flt){
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

    public string getOriginAirport(){
        return originAirport;
    }

    public string getDestinationAirport() {
        return destinationAirport;
    }

    public string getPsgName() {
        return psgName;
    }

    public string getfltNr(){
        return fltNrM
    }
}