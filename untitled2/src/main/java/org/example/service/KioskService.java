package org.example.service;

import org.example.BoardingPass;
import org.example.DAO.KioskDAO;

public class KioskService {

    private final BoardingPassService bpService;
    private final TransactionService txService;
    private final KioskDAO kioskDAO;
    private final HeadphoneService hpService;

    public KioskService(BoardingPassService bpService,
                        TransactionService txService,
                        KioskDAO kioskDAO,
                        HeadphoneService hpService) {
        this.bpService = bpService;
        this.txService = txService;
        this.kioskDAO = kioskDAO;
        this.hpService = hpService;
    }

    public boolean isValidAirport(String airportICAO) {
        return kioskDAO.exists(airportICAO);
    }

    public void beginTransition(BoardingPass bp, String airportICAO) {
        bpService.store(bp);
        int kioskId = kioskDAO.getIDFromICAO(airportICAO);
        txService.start(bp.getBPNumber(), kioskId);
    }

    public void pickUp(BoardingPass bp, String airportICAO) {
        int kioskId = kioskDAO.getIDFromICAO(airportICAO);
        int available = kioskDAO.getAvailableHeadphones(kioskId);
        if (available <= 0) throw new RuntimeException("No headphones available");

        int hpId = hpService.findAvailable(airportICAO);
        if (hpId == 0) throw new RuntimeException("No headphone found");

        kioskDAO.decrementHeadphones(kioskId);
        hpService.markInUse(hpId);
        txService.setHeadphone(bp.getBPNumber(), hpId);
        txService.close(bp.getBPNumber());
    }

    public void dropOff(BoardingPass bp, String airportICAO) {
        int kioskId = kioskDAO.getIDFromICAO(airportICAO);
        txService.setDestinationKiosk(bp.getBPNumber(), kioskId);

        int hpId = txService.getHeadphone(bp.getBPNumber());
        if (hpId == 0) throw new RuntimeException("No headphone associated with BP");

        hpService.markDroppedOff(hpId, airportICAO);
        kioskDAO.incrementHeadphones(kioskId);
    }
    public String getKioskName(String airportICAO) {
        return kioskDAO.getNameFromICAO(airportICAO);
    }
}

