package main.java.com.payments.service;

import main.java.com.payments.exception.NotFoundException;
import main.java.com.payments.model.Department;
import main.java.com.payments.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

public class DepartmentService implements IDepartmentService {
    private final DepartmentRepository deptRepo;

    public DepartmentService(DepartmentRepository deptRepo) {
        this.deptRepo = deptRepo;
    }

    @Override
    public Department createDepartment(Department d) {
        try {
            return deptRepo.save(d);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Department getDepartmentById(int id) {
        try {
            Optional<Department> o = deptRepo.findById(id);
            return o.orElseThrow(() -> new NotFoundException("Département introuvable: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Department> listDepartments() {
        try {
            return deptRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateDepartment(Department d){
        try {
            deptRepo.update(d);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteDepartment(int id) {
        try {
            deptRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

