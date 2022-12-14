package com.psl.employeeinfo.controller;

import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Lazy
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    static final String Permission_URL = "http://localhost:8084/";

    private boolean restTemplateAuthentication(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("admin", "admin121");
            HttpEntity<String> response = new HttpEntity<>(headers);
            return restTemplate.exchange(Permission_URL + "permission/EmployeeApp1", HttpMethod.GET, response, Boolean.class).getBody();
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping("/employee")
    private List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{emp_id}")
    private Object getEmployeeById(@PathVariable("emp_id") String emp_id){
        try{
            return employeeService.getEmployeeById(emp_id);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such employee id present in database!");
        }
    }

    @PostMapping("/employee")
    private Object saveEmployee(@RequestBody Employee emp){
        if(restTemplateAuthentication()) {
            try {
                if (emp.getEmp_id().startsWith("PSL")) {
                    if (employeeService.getEmployeeById(emp.getEmp_id()).getEmp_id() == emp.getEmp_id()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Data with Id " + emp.getEmp_id() + " already in database!");
                    } else {
                        employeeService.saveEmployee(emp);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Employee Id - The format of Id should be like PSL101");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to create user");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee " + emp.getEmp_id() + " details added successfully");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have permission to create employee");
        }
    }

    @PutMapping("/employee")
    private Object updateEmployee(@RequestBody Employee emp){
        if(restTemplateAuthentication()) {
            Employee empRec;
            try {
                empRec = employeeService.getEmployeeById(emp.getEmp_id());
                if (empRec.getEmp_id().equals(emp.getEmp_id())) {
                    if ((empRec.getEmp_name().equals(emp.getEmp_name())) &&
                            (empRec.getEmp_profile().equals(emp.getEmp_profile())) &&
                            (empRec.getEmp_salary() == emp.getEmp_salary())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("No Change in the Data!");
                    }
                    employeeService.updateEmployee(emp);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Resource updated Successfully!");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Employee Id present in database");
                }
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Employee Id present in database");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have permission to update employee");
        }
    }

    @DeleteMapping("/employee/{emp_id}")
    private Object deleteEmployeeById(@PathVariable("emp_id") String emp_id){
        if(restTemplateAuthentication()) {
            try {
                employeeService.deleteEmployeeById(emp_id);
                return ResponseEntity.status(HttpStatus.OK).body("Employee Id " + emp_id + " Deleted Successfully!");
            } catch (EmptyResultDataAccessException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Id Not In Database!");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have permission to delete employee");
        }
    }

    @DeleteMapping("/employee")
    private Object deleteAllEmployee(){
        if(restTemplateAuthentication()) {
            try {
                employeeService.deleteAllEmployee();
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Employees are Deleted Successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.GONE).body("Unable to delete Data");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have permission to delete employee");
        }
    }
}
