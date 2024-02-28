package com.csi.service;

import com.csi.model.Employee;
import com.csi.repo.EmployeeRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl {

    @Autowired
    private EmployeeRepo employeeRepoImpl;

    public Employee save(Employee employee) {
        return employeeRepoImpl.save(employee);
    }

    public List<Employee> saveAll(List<Employee> employeeList) {
        return employeeRepoImpl.saveAll(employeeList);
    }

    public List<Employee> findAll() {
        return employeeRepoImpl.findAll();
    }


    public boolean signIn(String empEmailId, String empPassword) {
        Employee employee = employeeRepoImpl.findByEmpEmailIdAndEmpPassword(empEmailId, empPassword);

        boolean flag = false;

        if (employee != null && employee.getEmpEmailId().equals(empEmailId) && employee.getEmpPassword().equals(empPassword)) {
            flag = true;
        }

        return flag;

    }

    @Cacheable(value = "empId")
    public Optional<Employee> findById(int empId) {
        log.info("#####Fetching Employee Id from Database:" + empId);
        return employeeRepoImpl.findById(empId);
    }

    public List<Employee> findByEmpName(String empName) {
        return employeeRepoImpl.findByEmpName(empName);
    }

    public Employee findByEmpContactNumber(long empContactNumber) {
        return employeeRepoImpl.findByEmpContactNumber(empContactNumber);
    }

    public Employee update(Employee employee) {
        return employeeRepoImpl.save(employee);
    }

    public Employee changeAddress(Employee employee) {
        return employeeRepoImpl.save(employee);
    }

    public void deleteById(int empId) {
        employeeRepoImpl.deleteById(empId);
    }

    public void deleteAll() {
        employeeRepoImpl.deleteAll();
    }


}
