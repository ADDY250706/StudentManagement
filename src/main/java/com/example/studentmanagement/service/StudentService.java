
package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // Add student and prevent duplicate ID
    public Student addStudent(Student student) {

        if (repository.existsById(student.getStudentId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Duplicate Student ID"
            );
        }

        validateScores(student);

        return repository.save(student);
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // Search by ID
    public Student getById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );
    }

    // Search by email
    public List<Student> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    // Search by course
    public List<Student> getByCourse(String course) {
        return repository.findByCourseNameIgnoreCase(course);
    }

    // Update scores and skills
    public Student updateStudent(String id, Student updated) {

        Student existing = getById(id);

        validateScores(updated);

        existing.setProgrammingScore(updated.getProgrammingScore());
        existing.setMathematicsScore(updated.getMathematicsScore());
        existing.setTechnicalSkills(updated.getTechnicalSkills());

        return repository.save(existing);
    }

    // Delete student
    public void deleteStudent(String id) {
        Student existing = getById(id);
        repository.delete(existing);
    }

    // Calculate merit list
    public List<Student> getMeritList() {

        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(
                        Student::getAverageScore
                ).reversed())
                .collect(Collectors.toList());
    }

    // Same technical skills
    public List<List<Student>> getStudentsWithSameSkills() {

        Map<Set<String>, List<Student>> groups =
                new HashMap<>();

        for (Student s : repository.findAll()) {

            Set<String> skills =
                    new HashSet<>(s.getTechnicalSkills());

            groups.computeIfAbsent(
                    skills, k -> new ArrayList<>()
            ).add(s);
        }

        return groups.values().stream()
                .filter(group -> group.size() > 1)
                .collect(Collectors.toList());
    }

    // Programming needs improvement but maths >= 75
    public List<Student> getStudentsNeedingProgrammingImprovement() {

        return repository.findAll().stream()
                .filter(s -> s.getProgrammingScore() < 50
                        && s.getMathematicsScore() >= 75)
                .collect(Collectors.toList());
    }

    private void validateScores(Student s) {

        if (s.getProgrammingScore() == null ||
                s.getMathematicsScore() == null ||
                s.getProgrammingScore() < 0 ||
                s.getProgrammingScore() > 100 ||
                s.getMathematicsScore() < 0 ||
                s.getMathematicsScore() > 100) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Scores must be between 0 and 100"
            );
        }
    }
}

