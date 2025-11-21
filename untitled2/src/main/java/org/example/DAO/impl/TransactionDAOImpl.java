package org.example.DAO.impl;

import org.example.DAO.TransactionDAO;
import org.example.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.*;

public class TransactionDAOImpl implements TransactionDAO {

    private final DataSource ds = DatabaseConfig.getDataSource();

    @Override
    public void start(int bpNumber, int kioskId) {
        String sql = "INSERT INTO transactions (BPN, originKioskID, status) VALUES (?, ?, ?)";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bpNumber);
            ps.setInt(2, kioskId);
            ps.setInt(3, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(int bpNumber) {
        String sql = "UPDATE transactions SET status = 1 WHERE BPN = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bpNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setHeadphone(int bpNumber, int headphoneId) {
        String sql = "UPDATE transactions SET headphonesID = ? WHERE BPN = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, headphoneId);
            ps.setInt(2, bpNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getHeadphoneFromBP(int bpNumber) {
        String sql = "SELECT headphonesID FROM transactions WHERE BPN = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bpNumber);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("headphonesID") : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setDestinationKiosk(int bpNumber, int kioskId) {
        String sql = "UPDATE transactions SET destKioskID = ? WHERE BPN = ?";
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, kioskId);
            ps.setInt(2, bpNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
