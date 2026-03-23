package com.employee;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {

        BeanFactory factory =
        new ClassPathXmlApplicationContext("beans.xml");

        EmployeeService service =
        (EmployeeService) factory.getBean("employeeService");

        service.showEmployee();
    }
}