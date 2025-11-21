package org.example.DAO.impl;
import org.example.DAO.KioskDAO;
import org.example.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;

public class KioskDAOImpl implements KioskDAO {

    private final DataSource ds = DatabaseConfig.getDataSource();

    @Override
    public int getIDFromICAO(String icao) {
        String sql = "SELECT ID FROM kiosk WHERE airport = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, icao);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ID");
                throw new RuntimeException("Kiosk not found for ICAO: " + icao);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getNameFromICAO(String icao) {
        String sql = "SELECT airport_name FROM kiosk WHERE airport = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, icao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("airport_name") : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(String icao) {
        String sql = "SELECT 1 FROM kiosk WHERE airport = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, icao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getAvailableHeadphones(int kioskId) {
        String sql = "SELECT numOfAvailableHP FROM kiosk WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kioskId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("numOfAvailableHP");
                throw new RuntimeException("Kiosk ID not found: " + kioskId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decrementHeadphones(int kioskId) {
        String sql = "UPDATE kiosk SET numOfAvailableHP = numOfAvailableHP - 1 WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kioskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void incrementHeadphones(int kioskId) {
        String sql = "UPDATE kiosk SET numOfAvailableHP = numOfAvailableHP + 1 WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kioskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

