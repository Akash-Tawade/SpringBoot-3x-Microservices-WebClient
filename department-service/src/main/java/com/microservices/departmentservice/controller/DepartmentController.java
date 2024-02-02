package com.microservices.departmentservice.controller;

import com.microservices.departmentservice.client.EmployeeClient;
import com.microservices.departmentservice.model.Department;
import com.microservices.departmentservice.model.Employee;
import com.microservices.departmentservice.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeClient employeeClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping
    public void addDepartment(@RequestBody Department depart){
        LOGGER.info("Department add: {}", depart);
        departmentRepository.addDepartment(depart);
    }

    @GetMapping
    public List<Department> findAll(){
        LOGGER.info("Department find:");
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id){
        LOGGER.info("Department find: Id={}", id);
        return departmentRepository.findById(id);
    }

//    @GetMapping("/with-employees")
//    public List<Department> findAllWithEmployees(){
//        LOGGER.info("Department find");
//        List<Department> departments = departmentRepository.findAll();
//        departments.forEach(department ->
//               department.setEmployees(employeeClient.findByDepartment(department.getId())));
//        return departments;
//    }

    @GetMapping("/with-employees")
    public List<Department> findAllWithEmployees() {
        LOGGER.info("Department find");
        List<Department> departments
                = departmentRepository.findAll();
        departments.forEach(department ->
                department.setEmployees(
                        employeeClient.findByDepartment(department.getId())));
        return  departments;
    }
}





//Employee emp = webClient.get().uri(uriBuilder -> uriBuilder.path("http://localhost:2022/employee").build()).retrieve().bodyToMono(Employee.class).block();
//department.setEmployees(employeeClient.findByDepartment(emp.id())));