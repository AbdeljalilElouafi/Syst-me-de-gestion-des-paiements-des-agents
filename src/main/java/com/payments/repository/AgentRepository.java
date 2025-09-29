package main.java.com.payments.repository;

import main.java.com.payments.model.Agent;
import main.java.com.payments.model.TypeAgent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgentRepository implements IAgentRepository {
    private final Connection conn;

    public AgentRepository() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Agent save(Agent agent) throws SQLException {
        String sql = "INSERT INTO agent (first_name, last_name, email, password, type, department_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, agent.getFirstName());
            ps.setString(2, agent.getLastName());
            ps.setString(3, agent.getEmail());
            ps.setString(4, agent.getPassword());
            ps.setString(5, agent.getType().name());
            if (agent.getDepartmentId() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, agent.getDepartmentId());
            }

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);
                        agent.setId(generatedId);
                    }
                }
            }
        }
        return agent;
    }

    @Override
    public Optional<Agent> findById(int id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email, password, type, department_id FROM agent WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Agent a = new Agent(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            TypeAgent.valueOf(rs.getString("type")),
                            rs.getObject("department_id") == null ? null : rs.getInt("department_id")
                    );
                    return Optional.of(a);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Agent> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, first_name, last_name, email, password, type, department_id FROM agent WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Agent a = new Agent(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            TypeAgent.valueOf(rs.getString("type")),
                            rs.getObject("department_id") == null ? null : rs.getInt("department_id")
                    );
                    return Optional.of(a);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Agent> findAll() throws SQLException {
        List<Agent> list = new ArrayList<>();
        String sql = "SELECT id, first_name, last_name, email, password, type, department_id FROM agent";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Agent a = new Agent(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        TypeAgent.valueOf(rs.getString("type")),
                        rs.getObject("department_id") == null ? null : rs.getInt("department_id")
                );
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public void update(Agent agent) throws SQLException {
        String sql = "UPDATE agent SET first_name=?, last_name=?, email=?, password=?, type=?, department_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, agent.getFirstName());
            ps.setString(2, agent.getLastName());
            ps.setString(3, agent.getEmail());
            ps.setString(4, agent.getPassword());
            ps.setString(5, agent.getType().name());
            if (agent.getDepartmentId() == null) ps.setNull(6, Types.INTEGER);
            else ps.setInt(6, agent.getDepartmentId());
            ps.setInt(7, agent.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM agent WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
