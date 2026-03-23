package com.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {

    @Autowired
    private Employee employee;

    public void showEmployee() {
        employee.display();
    }
}