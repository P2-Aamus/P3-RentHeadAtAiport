package org.example.DAO;

public interface HeadphoneDAO {
    int findAvailableAt(String airportIcao);
    void setStatus(int headphoneId, int status);
    void setLocation(int headphoneId, String location);
    void setBattery(int headphoneId, int value);
}
