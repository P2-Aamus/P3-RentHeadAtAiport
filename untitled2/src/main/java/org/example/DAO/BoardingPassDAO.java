package org.example.DAO;

import org.example.BoardingPass;

import java.util.List;

public interface BoardingPassDAO {
    void insert(BoardingPass bp);
    void delete(int bpNumber);
    List<Integer> getAllBPN();
    boolean exists(int bpNumber);
}
