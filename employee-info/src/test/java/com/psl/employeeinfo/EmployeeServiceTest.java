package com.psl.employeeinfo;

import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.repository.EmployeeRepository;
import com.psl.employeeinfo.service.EmployeeService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isEmployeesExists_service(){
        Mockito.when(employeeRepository.findAll())
                .thenReturn(List.of(new Employee("PSL101","krishna","Software Engineer",30000),
                        new Employee("PSL102","Raman","Software Engineer 2",40000),
                        new Employee("PSL103","Rohit","Intern",10000)));
        List<Employee> employees = employeeService.getAllEmployee();
        Assert.assertEquals(3,employees.size());
        Mockito.verify(employeeRepository,Mockito.times(1)).findAll();
    }

    @Test
    void isEmployeeExistById_service(){
        Mockito.when(employeeRepository.findById("PSL101")).
                thenReturn(java.util.Optional.of(new Employee("PSL101", "krishna", "Software Engineer", 30000)));

        Employee employee = employeeService.getEmployeeById("PSL101");
        Assert.assertEquals("krishna",employee.getEmp_name());
        Assert.assertEquals("Software Engineer",employee.getEmp_profile());
        Assert.assertEquals(30000,employee.getEmp_salary());
    }

    @Test
    void isEmployeeCreating_service(){
        Employee employee = new Employee("PSL101", "krishna", "Software Engineer", 30000);
        employeeService.saveEmployee(employee);
        Mockito.verify(employeeRepository,Mockito.times(1)).save(employee);
    }

    @Test
    void isEmployeeUpdating_service(){
        Employee employee = new Employee("PSL101", "krishna", "Software Engineer", 30000);
        Employee employee1 = new Employee("PSL101", "krishna", "Software Engineer 2", 40000);

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        Employee oldEmployment = employeeService.saveEmployee(employee);
        Mockito.when(employeeRepository.save(employee1)).thenReturn(employee1);
        Employee newEmployment = employeeService.updateEmployee(employee1);

        Assert.assertEquals(oldEmployment.getEmp_name(),newEmployment.getEmp_name());
        Assert.assertNotEquals(oldEmployment.getEmp_profile(),newEmployment.getEmp_profile());
        Assert.assertNotEquals(oldEmployment.getEmp_salary(),newEmployment.getEmp_salary());
        Assert.assertEquals("Software Engineer 2",newEmployment.getEmp_profile());
        Assert.assertEquals(40000,newEmployment.getEmp_salary());

        Mockito.verify(employeeRepository,Mockito.times(1)).save(employee);
        Mockito.verify(employeeRepository,Mockito.times(1)).save(employee1);
    }

    @Test
    void isEmployeeDeletingById_service(){
        employeeService.deleteEmployeeById("PSL102");
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteById("PSL102");
    }

    @Test
    void isAllEmployeesDeleting_service(){
        employeeService.deleteAllEmployee();
        Mockito.verify(employeeRepository,Mockito.times(1)).deleteAll();
    }
}
