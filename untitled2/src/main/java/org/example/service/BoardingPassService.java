package org.example.service;

import org.example.BoardingPass;
import org.example.DAO.BoardingPassDAO;

import java.util.List;

public class BoardingPassService {

    private final BoardingPassDAO bpDAO;

    public BoardingPassService(BoardingPassDAO bpDAO) {
        this.bpDAO = bpDAO;
    }

    public void store(BoardingPass bp) {
        bpDAO.insert(bp);
    }

    public boolean exists(BoardingPass bp) {
        return bpDAO.exists(bp.getBPNumber());
    }

    public List<Integer> getAllBPN() {
        return bpDAO.getAllBPN();
    }

    public void delete(BoardingPass bp) {
        bpDAO.delete(bp.getBPNumber());
    }
}
