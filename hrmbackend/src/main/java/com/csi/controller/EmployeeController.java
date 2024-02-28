package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Employee;
import com.csi.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<Employee> save(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeServiceImpl.save(employee), HttpStatus.CREATED);
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {

        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());

    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeServiceImpl.findAll());
    }

    @PostMapping("/saveall/{employeeList}")
    public ResponseEntity<List<Employee>> saveAll(@Valid @RequestBody List<Employee> employeeList) {
        return new ResponseEntity<>(employeeServiceImpl.saveAll(employeeList), HttpStatus.CREATED);
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeServiceImpl.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServiceImpl.findById(empId));
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeServiceImpl.findByEmpName(empName));
    }

    @GetMapping("/findbycontactno/{empContactNo}")
    public ResponseEntity<Employee> findByContactNo(@PathVariable long empContactNo) {
        return ResponseEntity.ok(employeeServiceImpl.findByEmpContactNumber(empContactNo));
    }


    @PutMapping("/update/{empId}/{employee}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @Valid @RequestBody Employee employee) {

        Employee employee1 = employeeServiceImpl.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id does not exist"));

        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpDOB(employee1.getEmpDOB());
        employee1.setEmpUID(employee.getEmpUID());
        employee1.setEmpPancard(employee.getEmpPancard());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());

        return new ResponseEntity<>(employeeServiceImpl.update(employee1), HttpStatus.CREATED);

    }


    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingInt(Employee::getEmpId)).toList());
    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDOB() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());
    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingDouble(Employee::getEmpSalary)).toList());
    }

    @GetMapping("/filterbyid/{empId}")
    public ResponseEntity<Employee> filterById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpId() == empId).toList().get(0));
    }

    @GetMapping("/filterbyname/{empName}")
    public ResponseEntity<List<Employee>> filterByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpName().equals(empName)).toList());
    }

    @GetMapping("/filterbysalary/{empSalary}")
    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpSalary() >= empSalary).toList());
    }

    @GetMapping("/filterbydob/{empDOB}")
    public ResponseEntity<List<Employee>> filterByDOB(@PathVariable String empDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(empDOB)).toList());
    }

    @GetMapping("/filterbyanyinput/{input}")
    public ResponseEntity<List<Employee>> filterByAnyInput(@PathVariable String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(input)
                || emp.getEmpName().equals(input)
                || emp.getEmpEmailId().equals(input)
                || String.valueOf(emp.getEmpContactNumber()).equals(input)
                || String.valueOf(emp.getEmpId()).equals(input)
                || String.valueOf(emp.getEmpSalary()).equals(input)).toList());
    }

    @GetMapping("/sumofsalary")
    public ResponseEntity<Double> sumOfSalary() {

        double sum = employeeServiceImpl.findAll().stream().collect(Collectors.summingDouble(Employee::getEmpSalary));

        return ResponseEntity.ok(sum);
    }

    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @Valid @PathVariable String empAddress) {
        Employee employee = employeeServiceImpl.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id does not exist"));
        employee.setEmpAddress(empAddress);

        return new ResponseEntity<>(employeeServiceImpl.changeAddress(employee), HttpStatus.CREATED);

    }

    @GetMapping("/loaneligibility/{empId}")
    public ResponseEntity<String> loanEligibility(@PathVariable int empId) {
        boolean flag = false;

        String msg = "";

        for (Employee employee : employeeServiceImpl.findAll()) {

            if (employee.getEmpId() == empId && employee.getEmpSalary() >= 50000) {
                msg = "Eligible for loan";

                flag = true;

                break;

            }

            if (!flag) {
                msg = "Not Eligible for vote";
            }


        }
        return ResponseEntity.ok(msg);
    }


    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deletebyId(@PathVariable int empId) {
        employeeServiceImpl.deleteById(empId);

        return ResponseEntity.ok("Data deleted Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeServiceImpl.deleteAll();

        return ResponseEntity.ok("All data deleted Successfully");
    }
}
