package com.employee;

import org.springframework.stereotype.Component;

@Component
public class Employee {

    private int id;
    private String name;
    private double salary;

    public Employee() {
        id = 1;
        name = "Charan";
        salary = 50000;
    }

    public void display() {
        System.out.println("Employee ID: " + id);
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Salary: " + salary);
    }
}