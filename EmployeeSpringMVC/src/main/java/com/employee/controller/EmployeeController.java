package com.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.employee.model.Employee;

@Controller
public class EmployeeController {
    
    /**
     * Method 1: Using Model to pass data
     * URL: http://localhost:8080/EmployeeSpringMVC/employee
     */
    @RequestMapping("/employee")
    public String showEmployee(Model model) {
        
        // Create employee object with sample data
        Employee emp = new Employee(101, "Charan", "IT", 75000.50, "charan@company.com");
        
        // Add employee object to model (will be available in JSP)
        model.addAttribute("emp", emp);
        
        // Return view name - resolves to /WEB-INF/views/employee.jsp
        return "employee";
    }
    
    /**
     * Method 2: Using ModelAndView
     * URL: http://localhost:8080/EmployeeSpringMVC/employee2
     */
    @RequestMapping("/employee2")
    public ModelAndView showEmployeeWithModelAndView() {
        
        // Create ModelAndView object
        ModelAndView mav = new ModelAndView("employee");
        
        // Create employee object
        Employee emp = new Employee(102, "Priya", "HR", 65000.75, "priya@company.com");
        
        // Add object to ModelAndView
        mav.addObject("emp", emp);
        
        return mav;
    }
    
    /**
     * Method 3: Accepting request parameter
     * URL: http://localhost:8080/EmployeeSpringMVC/employeeById?id=103
     */
    @GetMapping("/employeeById")
    public String getEmployeeById(@RequestParam("id") int empId, Model model) {
        
        // Normally you would fetch from database, but we'll create based on ID
        Employee emp = null;
        
        if (empId == 101) {
            emp = new Employee(101, "Charan", "IT", 75000.50, "charan@company.com");
        } else if (empId == 102) {
            emp = new Employee(102, "Priya", "HR", 65000.75, "priya@company.com");
        } else {
            emp = new Employee(103, "Rahul", "Finance", 70000.00, "rahul@company.com");
        }
        
        model.addAttribute("emp", emp);
        return "employee";
    }
    
    /**
     * Method 4: Show multiple employees (for extension)
     * URL: http://localhost:8080/EmployeeSpringMVC/employees
     */
    @RequestMapping("/employees")
    public String showEmployees(Model model) {
        
        // Create a list of employees
        java.util.List<Employee> empList = new java.util.ArrayList<>();
        empList.add(new Employee(101, "Charan", "IT", 75000.50, "charan@company.com"));
        empList.add(new Employee(102, "Priya", "HR", 65000.75, "priya@company.com"));
        empList.add(new Employee(103, "Rahul", "Finance", 70000.00, "rahul@company.com"));
        
        model.addAttribute("employees", empList);
        return "employeeList";
    }
    
    /**
     * Home page
     * URL: http://localhost:8080/EmployeeSpringMVC/
     */
    @RequestMapping("/")
    public String home() {
        return "index";
    }
}