package org.example;

public class Transactions {

    //Attributes
    private int transactionID;
    private final int headphonesID;
    private final int BPNumber;
    private int originKioskID;
    private int destinationKioskID;
    private int status;

    //Constructor
    public Transactions(int transactionID, int headphonesID, int BPNumber, int originKioskID, int destinationKioskID, int status) {
        this.transactionID = transactionID;
        this.headphonesID = headphonesID;
        this.BPNumber = BPNumber;
        this.originKioskID = originKioskID;
        this.destinationKioskID = destinationKioskID;
        this.status = status;
    }

    //Methods
    public int getTransactionID() {
        return transactionID;
    }

    public int getHeaphonesID() {
        return headphonesID;
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