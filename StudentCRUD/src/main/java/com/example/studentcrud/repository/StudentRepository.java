package com.example.studentcrud.repository;

import com.example.studentcrud.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    // Custom finder methods (Spring Data JPA will implement these)
    List<Student> findByDepartment(String department);
    
    List<Student> findByAgeGreaterThan(int age);
    
    List<Student> findByDepartmentAndAge(String department, int age);
    
    List<Student> findByNameContainingIgnoreCase(String name);
    
    // Custom query with sorting
    List<Student> findByDepartmentOrderByNameAsc(String department);
    
    // Using @Query annotation - JPQL
    @Query("SELECT s FROM Student s WHERE s.age BETWEEN :minAge AND :maxAge")
    List<Student> findStudentsByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
    
    @Query("SELECT s FROM Student s WHERE s.department = :dept ORDER BY s.name")
    List<Student> findStudentsByDepartmentSorted(@Param("dept") String department);
    
    // Native SQL query
    @Query(value = "SELECT * FROM student WHERE department = :dept", nativeQuery = true)
    List<Student> findStudentsByDepartmentNative(@Param("dept") String department);
    
    // Pagination support
    Page<Student> findByDepartment(String department, Pageable pageable);
    
    // Sorting support
    List<Student> findAll(Sort sort);
    
    // Count by department
    long countByDepartment(String department);
    
    // Check if exists
    boolean existsByEmail(String email);
    
    // Delete by department
    void deleteByDepartment(String department);
}