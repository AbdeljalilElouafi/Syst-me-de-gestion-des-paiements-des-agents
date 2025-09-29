package main.java.com.payments.repository;

import main.java.com.payments.model.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {
    Department save(Department department) throws Exception;
    Optional<Department> findById(int id) throws Exception;
    List<Department> findAll() throws Exception;
    void update(Department department) throws Exception;
    void deleteById(int id) throws Exception;
}
