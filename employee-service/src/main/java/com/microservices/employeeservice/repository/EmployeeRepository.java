package com.microservices.employeeservice.repository;

import com.microservices.employeeservice.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    public Employee add(Employee emp){
        employees.add(emp);
        return emp;
    }

    public Employee findById(Long id){
        return employees.stream()
                .filter(t->t.id().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Employee> findAll(){
        return employees;
    }

    public List<Employee> findByDepartment(Long DepartmentId){
        return employees.stream()
                .filter(t->t.departmentId().equals(DepartmentId))
                .toList();
    }
}
