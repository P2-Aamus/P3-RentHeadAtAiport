package org.example.service;

import org.example.DAO.TransactionDAO;

public class TransactionService {

    private final TransactionDAO txDAO;

    public TransactionService(TransactionDAO txDAO) {
        this.txDAO = txDAO;
    }

    public void start(int bpNumber, int kioskId) {
        txDAO.start(bpNumber, kioskId);
    }

    public void close(int bpNumber) {
        txDAO.close(bpNumber);
    }

    public void setHeadphone(int bpNumber, int hpId) {
        txDAO.setHeadphone(bpNumber, hpId);
    }

    public int getHeadphone(int bpNumber) {
        return txDAO.getHeadphoneFromBP(bpNumber);
    }

    public void setDestinationKiosk(int bpNumber, int kioskId) {
        txDAO.setDestinationKiosk(bpNumber, kioskId);
    }
}
