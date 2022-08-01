package com.psl.employeeinfo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
    @Id
    private String emp_id;
    private String emp_name;
    private String emp_profile;
    private long emp_salary;

    public Employee(){}

    public Employee(String emp_id, String emp_name, String emp_profile, long emp_salary) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_profile = emp_profile;
        this.emp_salary = emp_salary;
    }

    public String getEmp_id() {
        return emp_id;
    }
    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }
    public String getEmp_name() {
        return emp_name;
    }
    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }
    public String getEmp_profile() {
        return emp_profile;
    }
    public void setEmp_profile(String emp_profile) {
        this.emp_profile = emp_profile;
    }
    public long getEmp_salary() {
        return emp_salary;
    }
    public void setEmp_salary(long emp_salary) {
        this.emp_salary = emp_salary;
    }
}
