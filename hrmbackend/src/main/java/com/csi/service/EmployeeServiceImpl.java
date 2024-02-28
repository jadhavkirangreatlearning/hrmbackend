package com.csi.service;

import com.csi.model.Employee;
import com.csi.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepo employeeRepoImpl;

    public Employee save(Employee employee){
        return employeeRepoImpl.save(employee);
    }

    public List<Employee> findAll(){
        return employeeRepoImpl.findAll();
    }
}
