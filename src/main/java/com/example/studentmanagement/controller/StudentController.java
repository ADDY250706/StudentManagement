
package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // 1. Add student
    @PostMapping
    public ResponseEntity<Student> addStudent(
            @Valid @RequestBody Student student) {

        return ResponseEntity.status(201)
                .body(service.addStudent(student));
    }

    // 2. Retrieve all
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // 3. Search by ID
    @GetMapping("/{id}")
    public Student getById(@PathVariable String id) {
        return service.getById(id);
    }

    // Search by email
    @GetMapping("/search/email")
    public List<Student> getByEmail(
            @RequestParam String email) {
        return service.getByEmail(email);
    }

    // Search by course
    @GetMapping("/search/course")
    public List<Student> getByCourse(
            @RequestParam String course) {
        return service.getByCourse(course);
    }

    // 4. Update scores and skills
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String id,
            @RequestBody Student student) {

        return ResponseEntity.ok(service.updateStudent(id, student));
    }

    // 5. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable String id) {

        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Merit list
    @GetMapping("/merit-list")
    public List<Student> meritList() {
        return service.getMeritList();
    }

    // Same skills
    @GetMapping("/same-skills")
    public List<List<Student>> sameSkills() {
        return service.getStudentsWithSameSkills();
    }

    // Programming improvement filter
    @GetMapping("/needs-programming-improvement")
    public List<Student> improvementList() {
        return service.getStudentsNeedingProgrammingImprovement();
    }
}

