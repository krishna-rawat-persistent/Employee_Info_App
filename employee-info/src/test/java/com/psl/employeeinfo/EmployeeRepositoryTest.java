package com.psl.employeeinfo;

import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import java.util.List;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setup(){
        Employee emp = new Employee("PSL110","Krishna","Software Engineer",30000);
        employeeRepository.save(emp);
    }

    @AfterEach
    void tearDown(){
        try {
            employeeRepository.deleteById("PSL110");
        }catch (Exception e){
            System.out.println("Data already deleted");
        }
    }

    @Test
    void isEmployeesExist(){
        List<Employee> employees = employeeRepository.findAll();
        assertTrue(employees.size()>0);
    }

    @Test
    void isEmployeeExistsById(){
        Boolean emp1 = employeeRepository.findById("PSL110").isPresent();
        assertTrue(emp1);
    }

    @Test
    void isEmployeeUpdated(){
        Employee emp = new Employee("PSL110","Krishna","Software Engineer 2",40000);
        employeeRepository.save(emp);
        Employee employee = employeeRepository.findById("PSL110").get();
        assertEquals(employee.getEmp_profile(),emp.getEmp_profile());
        assertEquals(employee.getEmp_salary(),emp.getEmp_salary());
    }
    @Test
    void isEmployeeDeleted(){
        employeeRepository.deleteById("PSL110");
        assertFalse(employeeRepository.findById("PSL110").isPresent());
    }
}
