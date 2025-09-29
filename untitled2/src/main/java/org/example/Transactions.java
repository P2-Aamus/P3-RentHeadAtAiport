package org.example;

public class Transactions {

    //Attributes
    private int transactionID;
    private int heaphonesID;
    private int BPNumber;
    private int originKioskID;
    private int destinationKioskID;
    private int status;

    //Constructor


    //Methods
    public int getTransactionID() {
        return transactionID;
    }

    public int getHeaphonesID() {
        return heaphonesID;
    }

    public int getBPNumber() {
        return BPNumber;
    }

    public int getOriginKioskID() {
        return originKioskID;
    }

    public int getDestinationKioskID() {
        return destinationKioskID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int i){
        status = i;
    }
}