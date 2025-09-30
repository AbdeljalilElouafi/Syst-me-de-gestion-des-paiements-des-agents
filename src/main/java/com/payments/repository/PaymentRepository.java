package main.java.com.payments.repository;

import main.java.com.payments.model.Payment;
import main.java.com.payments.model.TypePayment;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentRepository implements IPaymentRepository {
    private final Connection conn;

    public PaymentRepository() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Payment save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (type, amount, date_payment, motif, condition_validee, agent_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, payment.getType().name());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setDate(3, Date.valueOf(payment.getDate()));
            ps.setString(4, payment.getMotif());
            ps.setBoolean(5, payment.isConditionValidee());
            ps.setInt(6, payment.getAgentId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) payment.setId(rs.getInt(1));
            }
        }
        return payment;
    }

    @Override
    public Optional<Payment> findById(int id) throws SQLException {
        String sql = "SELECT id, type, amount, date_payment, motif, condition_validee, agent_id FROM payment WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payment p = new Payment(
                            rs.getInt("id"),
                            TypePayment.valueOf(rs.getString("type")),
                            rs.getBigDecimal("amount"),
                            rs.getDate("date_payment").toLocalDate(),
                            rs.getString("motif"),
                            rs.getBoolean("condition_validee"),
                            rs.getInt("agent_id")
                    );
                    return Optional.of(p);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Payment> findAll() throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT id, type, amount, date_payment, motif, condition_validee, agent_id FROM payment";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        TypePayment.valueOf(rs.getString("type")),
                        rs.getBigDecimal("amount"),
                        rs.getDate("date_payment").toLocalDate(),
                        rs.getString("motif"),
                        rs.getBoolean("condition_validee"),
                        rs.getInt("agent_id")
                );
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<Payment> findByAgentId(int agentId) throws SQLException {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT id, type, amount, date_payment, motif, condition_validee, agent_id FROM payment WHERE agent_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, agentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payment p = new Payment(
                            rs.getInt("id"),
                            TypePayment.valueOf(rs.getString("type")),
                            rs.getBigDecimal("amount"),
                            rs.getDate("date_payment").toLocalDate(),
                            rs.getString("motif"),
                            rs.getBoolean("condition_validee"),
                            rs.getInt("agent_id")
                    );
                    list.add(p);
                }
            }
        }
        return list;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM payment WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
