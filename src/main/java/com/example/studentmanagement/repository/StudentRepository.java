
package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface StudentRepository
        extends JpaRepository<Student, String> {

    List<Student> findByEmail(String email);

    List<Student> findByCourseNameIgnoreCase(String courseName);
}


