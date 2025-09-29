package main.java.com.payments.repository;

import main.java.com.payments.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository implements IDepartmentRepository {
    private final Connection conn;

    public DepartmentRepository() throws SQLException {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Department save(Department department) throws SQLException {
        String sql = "INSERT INTO department (name, responsible_agent_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, department.getName());
            if (department.getResponsibleAgentId() == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, department.getResponsibleAgentId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) department.setId(rs.getInt(1));
            }
        }
        return department;
    }

    @Override
    public Optional<Department> findById(int id) throws SQLException {
        String sql = "SELECT id, name, responsible_agent_id FROM department WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department d = new Department(rs.getInt("id"), rs.getString("name"),
                            rs.getObject("responsible_agent_id") == null ? null : rs.getInt("responsible_agent_id"));
                    return Optional.of(d);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Department> findAll() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT id, name, responsible_agent_id FROM department";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Department d = new Department(rs.getInt("id"), rs.getString("name"),
                        rs.getObject("responsible_agent_id") == null ? null : rs.getInt("responsible_agent_id"));
                list.add(d);
            }
        }
        return list;
    }

    @Override
    public void update(Department department) throws SQLException {
        String sql = "UPDATE department SET name=?, responsible_agent_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getName());
            if (department.getResponsibleAgentId() == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, department.getResponsibleAgentId());
            ps.setInt(3, department.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
