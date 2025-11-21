package org.example.DAO.impl;

import org.example.DAO.HeadphoneDAO;
import org.example.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;

public class HeadphoneDAOImpl implements HeadphoneDAO {

    private final DataSource ds = DatabaseConfig.getDataSource();

    @Override
    public int findAvailableAt(String airportIcao) {
        String sql = "SELECT ID FROM headphones WHERE location = ? AND status = 1 LIMIT 1";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, airportIcao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("ID") : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setStatus(int headphoneId, int status) {
        String sql = "UPDATE headphones SET status = ? WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, status);
            ps.setInt(2, headphoneId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setLocation(int headphoneId, String location) {
        String sql = "UPDATE headphones SET location = ? WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, location);
            ps.setInt(2, headphoneId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBattery(int headphoneId, int value) {
        String sql = "UPDATE headphones SET battery = ? WHERE ID = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, value);
            ps.setInt(2, headphoneId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
