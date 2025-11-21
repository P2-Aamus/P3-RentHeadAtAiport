package org.example.DAO;

public interface KioskDAO {
    int getIDFromICAO(String icao);
    String getNameFromICAO(String icao);
    boolean exists(String icao);
    int getAvailableHeadphones(int kioskId);
    void decrementHeadphones(int kioskId);
    void incrementHeadphones(int kioskId);
}
