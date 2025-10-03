package main.java.com.payments.service;

import main.java.com.payments.model.Department;

import java.util.List;

public interface IDepartmentService {

    Department createDepartment(Department department);
    Department getDepartmentById(int id);
    List<Department> listDepartments();
    void updateDepartment(Department department);
    void deleteDepartment(int id);

}