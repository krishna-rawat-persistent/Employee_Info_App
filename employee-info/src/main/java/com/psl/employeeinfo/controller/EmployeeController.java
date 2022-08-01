package com.psl.employeeinfo.controller;

import com.psl.employeeinfo.exception.CustomException;
import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    CustomException customException;

    @GetMapping("/employee")
    private List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{emp_id}")
    private Object getEmployeeById(@PathVariable("emp_id") String emp_id){
        if(employeeService.getEmployeeById(emp_id).getEmp_id() != "PSL000"){
            return employeeService.getEmployeeById(emp_id);
        }else{
            customException = new CustomException(404,"No such Employee Id present in database");
            return customException;
        }
    }

    @PostMapping("/employee")
    private Object saveEmployee(@RequestBody Employee emp){
        try{
            if(emp.getEmp_id().startsWith("PSL")){
                if(employeeService.getEmployeeById(emp.getEmp_id()).getEmp_id() == emp.getEmp_id()) {
                    customException = new CustomException(409,"Data with Id "+emp.getEmp_id()+" already in database!");
                    return customException;
                }else{
                    employeeService.saveEmployee(emp);
                }
            }else{
                customException = new CustomException(400,"Invalid Employee Id - The format of Id should be like PSL101");
                return customException;
            }
        }catch(Exception e){
            customException = new CustomException(400,"Employee Record is not Saved");
            return customException;
        }

        customException = new CustomException(201,"Employee "+emp.getEmp_id()+" details added successfully");
        return customException;
    }

    @PutMapping("/employee")
    private Object updateEmployee(@RequestBody Employee emp){
        Employee empRec;
        try{
            empRec = employeeService.getEmployeeById(emp.getEmp_id());
            if((empRec.getEmp_name().equals(emp.getEmp_name()))&&
                    (empRec.getEmp_profile().equals(emp.getEmp_profile()))&&
                    (empRec.getEmp_salary() == emp.getEmp_salary())){
                customException = new CustomException(409,"No Change in the Data!");
                return customException;
            }else{
                employeeService.updateEmployee(emp);
                customException = new CustomException(204,"Resource updated Successfully!");
                return customException;
            }
        }catch(NoSuchElementException e) {
            customException = new CustomException(404,"No such Employee Id present in database");
            return customException;
        }
    }

    @DeleteMapping("/employee/{emp_id}")
    private Object deleteEmployeeById(@PathVariable("emp_id") String emp_id){
        try{
            employeeService.deleteEmployeeById(emp_id);
            customException = new CustomException(204,"Employee Id "+emp_id+" Deleted Successfully!");
            return customException;
        }catch(EmptyResultDataAccessException e){
            customException = new CustomException(404,"Employee Id Not In Database!");
            return customException;
        }

    }

    @DeleteMapping("/employee")
    private Object deleteAllEmployee(){
        try{
            employeeService.deleteAllEmployee();
            customException = new CustomException(204,"Employees are Deleted Successfully!");
            return customException;
        }catch(Exception e){
            customException = new CustomException(410,"Unable to delete Data");
            return customException;
        }
    }
}
