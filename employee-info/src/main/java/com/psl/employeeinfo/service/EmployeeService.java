package com.psl.employeeinfo.service;

import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee(){
        return (List<Employee>) employeeRepository.findAll();
    }

    public Employee getEmployeeById(String emp_id){
        return employeeRepository.findById(emp_id).orElse(new Employee("PSL000","abc","xyz",123));
    }

    public Employee saveEmployee(Employee emp){
        return employeeRepository.save(emp);
    }

    public void deleteEmployeeById(String emp_id){
        employeeRepository.deleteById(emp_id);
    }

    public Employee updateEmployee(Employee emp){
        return employeeRepository.save(emp);
    }

    public  void deleteAllEmployee(){
        employeeRepository.deleteAll();
    }

}
