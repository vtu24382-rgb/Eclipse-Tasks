package com.example.studentcrud.service;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Create
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    // Read All with sorting
    public List<Student> getAllStudents(String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        return studentRepository.findAll(sort);
    }
    
    // Read All with pagination
    public Page<Student> getStudentsWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return studentRepository.findAll(pageable);
    }
    
    // Read by ID
    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }
    
    // Update
    public Student updateStudent(int id, Student studentDetails) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent != null) {
            existingStudent.setName(studentDetails.getName());
            existingStudent.setDepartment(studentDetails.getDepartment());
            existingStudent.setAge(studentDetails.getAge());
            existingStudent.setEmail(studentDetails.getEmail());
            return studentRepository.save(existingStudent);
        }
        return null;
    }
    
    // Delete
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
    
    // Check if exists
    public boolean existsById(int id) {
        return studentRepository.existsById(id);
    }
    
    // Custom methods
    
    // Find by department
    public List<Student> getStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }
    
    // Find by department with pagination
    public Page<Student> getStudentsByDepartment(String department, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findByDepartment(department, pageable);
    }
    
    // Find by age greater than
    public List<Student> getStudentsByAgeGreaterThan(int age) {
        return studentRepository.findByAgeGreaterThan(age);
    }
    
    // Find by department and age
    public List<Student> getStudentsByDepartmentAndAge(String department, int age) {
        return studentRepository.findByDepartmentAndAge(department, age);
    }
    
    // Search by name
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Find by age range
    public List<Student> getStudentsByAgeRange(int minAge, int maxAge) {
        return studentRepository.findStudentsByAgeRange(minAge, maxAge);
    }
    
    // Find by department sorted
    public List<Student> getStudentsByDepartmentSorted(String department) {
        return studentRepository.findByDepartmentOrderByNameAsc(department);
    }
    
    // Count by department
    public long countStudentsByDepartment(String department) {
        return studentRepository.countByDepartment(department);
    }
    
    // Check email exists
    public boolean isEmailExists(String email) {
        return studentRepository.existsByEmail(email);
    }
    
    // Delete by department
    public void deleteStudentsByDepartment(String department) {
        studentRepository.deleteByDepartment(department);
    }
}