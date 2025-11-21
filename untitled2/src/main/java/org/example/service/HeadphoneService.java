package org.example.service;

import org.example.DAO.HeadphoneDAO;

public class HeadphoneService {

    private final HeadphoneDAO hpDAO;

    public HeadphoneService(HeadphoneDAO hpDAO) {
        this.hpDAO = hpDAO;
    }

    public int findAvailable(String airportIcao) {
        return hpDAO.findAvailableAt(airportIcao);
    }

    public void markInUse(int hpId) {
        hpDAO.setStatus(hpId, 2);
        hpDAO.setLocation(hpId, null);
    }

    public void markDroppedOff(int hpId, String location) {
        hpDAO.setLocation(hpId, location);
        hpDAO.setBattery(hpId, 25);
        hpDAO.setStatus(hpId, 0);
    }
}
