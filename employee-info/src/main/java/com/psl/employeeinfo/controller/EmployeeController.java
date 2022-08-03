package com.psl.employeeinfo.controller;

import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee")
    private List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{emp_id}")
    private Object getEmployeeById(@PathVariable("emp_id") String emp_id){
        if(employeeService.getEmployeeById(emp_id).getEmp_id() != "PSL000"){
            return employeeService.getEmployeeById(emp_id);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such employee id present in database!");
        }
    }

    @PostMapping("/employee")
    private Object saveEmployee(@RequestBody Employee emp){
        try{
            if(emp.getEmp_id().startsWith("PSL")){
                if(employeeService.getEmployeeById(emp.getEmp_id()).getEmp_id() == emp.getEmp_id()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Data with Id "+emp.getEmp_id()+" already in database!");
                }else{
                    employeeService.saveEmployee(emp);
                }
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Employee Id - The format of Id should be like PSL101");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Record is not Saved");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Employee "+emp.getEmp_id()+" details added successfully");
    }

    @PutMapping("/employee")
    private Object updateEmployee(@RequestBody Employee emp){
        Employee empRec;
        try{
            empRec = employeeService.getEmployeeById(emp.getEmp_id());
            if(empRec.getEmp_id().equals(emp.getEmp_id())) {
                if ((empRec.getEmp_name().equals(emp.getEmp_name())) &&
                        (empRec.getEmp_profile().equals(emp.getEmp_profile())) &&
                        (empRec.getEmp_salary() == emp.getEmp_salary())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("No Change in the Data!");
                } else {
                    employeeService.updateEmployee(emp);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Resource updated Successfully!");
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Employee Id present in database");
            }
        }catch(NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Employee Id present in database");
        }
    }

    @DeleteMapping("/employee/{emp_id}")
    private Object deleteEmployeeById(@PathVariable("emp_id") String emp_id){
        try{
            employeeService.deleteEmployeeById(emp_id);
            return ResponseEntity.status(HttpStatus.OK).body("Employee Id "+emp_id+" Deleted Successfully!");
        }catch(EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Id Not In Database!");
        }

    }

    @DeleteMapping("/employee")
    private Object deleteAllEmployee(){
        try{
            employeeService.deleteAllEmployee();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employees are Deleted Successfully!");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.GONE).body("Unable to delete Data");
        }
    }
}
