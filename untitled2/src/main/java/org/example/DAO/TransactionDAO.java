package org.example.DAO;

public interface TransactionDAO {
    void start(int bpNumber, int kioskId);
    void close(int bpNumber);
    void setHeadphone(int bpNumber, int headphoneId);
    int getHeadphoneFromBP(int bpNumber);
    void setDestinationKiosk(int bpNumber, int kioskId);
}
