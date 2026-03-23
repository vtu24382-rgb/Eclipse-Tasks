package com.example.studentcrud.controller;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Create
    @PostMapping("/add")
    public Map<String, String> addStudent(@RequestBody Student student) {
        Map<String, String> response = new HashMap<>();
        if (studentService.isEmailExists(student.getEmail())) {
            response.put("status", "error");
            response.put("message", "Email already exists!");
            return response;
        }
        studentService.saveStudent(student);
        response.put("status", "success");
        response.put("message", "Student added successfully with ID: " + student.getId());
        return response;
    }
    
    // Read All (with sorting)
    @GetMapping("/all")
    public List<Student> getAllStudents(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return studentService.getAllStudents(sortBy, direction);
    }
    
    // Read All with pagination
    @GetMapping("/paginated")
    public Page<Student> getStudentsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return studentService.getStudentsWithPagination(page, size, sortBy);
    }
    
    // Read by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }
    
    // Update
    @PutMapping("/update/{id}")
    public String updateStudent(@PathVariable int id, @RequestBody Student student) {
        if (studentService.existsById(id)) {
            student.setId(id);
            studentService.updateStudent(id, student);
            return "Student updated successfully with ID: " + id;
        }
        return "Student not found with ID: " + id;
    }
    
    // Delete
    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {
        if (studentService.existsById(id)) {
            studentService.deleteStudent(id);
            return "Student deleted successfully with ID: " + id;
        }
        return "Student not found with ID: " + id;
    }
    
    // Get by department
    @GetMapping("/department/{dept}")
    public List<Student> getStudentsByDepartment(@PathVariable String dept) {
        return studentService.getStudentsByDepartment(dept);
    }
    
    // Get by department with pagination
    @GetMapping("/department/{dept}/paginated")
    public Page<Student> getStudentsByDepartmentPaginated(
            @PathVariable String dept,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return studentService.getStudentsByDepartment(dept, page, size);
    }
    
    // Get by age greater than
    @GetMapping("/age/{age}")
    public List<Student> getStudentsByAgeGreaterThan(@PathVariable int age) {
        return studentService.getStudentsByAgeGreaterThan(age);
    }
    
    // Get by department and age
    @GetMapping("/department/{dept}/age/{age}")
    public List<Student> getStudentsByDepartmentAndAge(@PathVariable String dept, @PathVariable int age) {
        return studentService.getStudentsByDepartmentAndAge(dept, age);
    }
    
    // Search by name
    @GetMapping("/search")
    public List<Student> searchStudentsByName(@RequestParam String name) {
        return studentService.searchStudentsByName(name);
    }
    
    // Get by age range
    @GetMapping("/age-range")
    public List<Student> getStudentsByAgeRange(
            @RequestParam int min,
            @RequestParam int max) {
        return studentService.getStudentsByAgeRange(min, max);
    }
    
    // Get count by department
    @GetMapping("/count/{dept}")
    public Map<String, Object> countByDepartment(@PathVariable String dept) {
        Map<String, Object> response = new HashMap<>();
        response.put("department", dept);
        response.put("count", studentService.countStudentsByDepartment(dept));
        return response;
    }
    
    // Delete by department
    @DeleteMapping("/delete/department/{dept}")
    public String deleteStudentsByDepartment(@PathVariable String dept) {
        studentService.deleteStudentsByDepartment(dept);
        return "All students from " + dept + " department deleted successfully";
    }
}