package org.example.DAO.impl;

import org.example.BoardingPass;
import org.example.DAO.BoardingPassDAO;
import org.example.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardingPassDAOImpl implements BoardingPassDAO {

    private final DataSource ds = DatabaseConfig.getDataSource();

    @Override
    public void insert(BoardingPass bp) {
        String sql = "INSERT INTO boarding_pass (BPN, origin_airport, dest_airport, psg_name, flt_nr) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bp.getBPNumber());
            ps.setString(2, bp.getOriginAirport());
            ps.setString(3, bp.getDestinationAirport());
            ps.setString(4, bp.getPsgName());
            ps.setString(5, bp.getfltNr());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int bpNumber) {
        String t1 = "DELETE FROM transactions WHERE BPN = ?";
        String t2 = "DELETE FROM boarding_pass WHERE BPN = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement p1 = c.prepareStatement(t1);
             PreparedStatement p2 = c.prepareStatement(t2)) {

            p1.setInt(1, bpNumber);
            p1.executeUpdate();

            p2.setInt(1, bpNumber);
            p2.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getAllBPN() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT BPN FROM boarding_pass";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(rs.getInt("BPN"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean exists(int bpNumber) {
        String sql = "SELECT 1 FROM boarding_pass WHERE BPN = ? LIMIT 1";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bpNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
