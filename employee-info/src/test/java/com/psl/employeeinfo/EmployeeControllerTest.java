package com.psl.employeeinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psl.employeeinfo.controller.EmployeeController;
import com.psl.employeeinfo.model.Employee;
import com.psl.employeeinfo.repository.EmployeeRepository;
import com.psl.employeeinfo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private  static ObjectMapper mapper = new ObjectMapper();

    Employee employee1 = new Employee("PSL101","Krishna","Software Engineer",32000);
    Employee employee2 = new Employee("PSL102", "Ram", "Software Engineer 2", 40000);
    Employee employee3 = new Employee("PSL103","Mohan","Intern",10000);

    @WithMockUser(value = "user")
    @Test
    void testGetAllEmployee_controller() throws Exception{
        List<Employee> employees = new ArrayList<>(Arrays.asList(employee1,employee2,employee3));
        Mockito.when(employeeService.getAllEmployee()).thenReturn(employees);
        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[0].emp_name", is("Krishna")));
    }

    @WithMockUser(value = "user")
    @Test
    void testGetEmployeeById_controller() throws Exception{
        Mockito.when(employeeService.getEmployeeById("PSL101")).thenReturn(employee1);

        mockMvc.perform(get("/employee/PSL101").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.emp_name",is("Krishna")));
    }

    @WithMockUser(value = "user")
    @Test
    void testCreateUser_controller() throws Exception{
        Employee employee = new Employee("PSL104","Komal","Lead Engineer",50000);

        Mockito.when(employeeService.getEmployeeById("PSL104")).thenReturn(employee);
        Mockito.when(employeeService.saveEmployee(employee)).thenReturn(employee);

        String data = mapper.writeValueAsString(employee);

        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()));
    }

    @WithMockUser(value = "user")
    @Test
    void testUpdateEmployee_controller() throws Exception{
        Employee employee = new Employee("PSL101","Krishna","Software Engineer 2", 40000);

        Mockito.when(employeeService.getEmployeeById("PSL101")).thenReturn(employee1);
        Mockito.when(employeeService.updateEmployee(employee)).thenReturn(employee);

        String updatedData = mapper.writeValueAsString(employee);

        mockMvc.perform(put("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedData))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$",notNullValue()));

    }

    @WithMockUser(value = "user")
    @Test
    void testDeleteEmployee_controller() throws Exception{
        Mockito.when(employeeService.getEmployeeById("PSL101")).thenReturn(employee1);

        mockMvc.perform(delete("/employee/PSL101").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
