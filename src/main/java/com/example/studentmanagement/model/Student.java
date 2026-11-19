
package com.example.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity //maps the java class to a database entity.
@Table(name = "students")
public class Student {

    @Id //marks the primary key
    private String studentId;

    private String studentName;

    @Email //it will help in validating email format
    @NotBlank
    private String email;

    private String courseName;

    @NotNull // prevents null scores
    @DecimalMin("0.0") //to include endpoints 0 and 100
    @DecimalMax("100.0")
    private Double programmingScore;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double mathematicsScore;

    @ElementCollection //stores the list of skills in a sep table and cz rel db cant stoer arrays in a single cell, it is connected via foreign key
    @CollectionTable( //for table name and fk
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "skill")
    private List<String> technicalSkills = new ArrayList<>();

    public Student() {
    }

    public Student(String studentId, String studentName,
                   String email, String courseName,
                   Double programmingScore, Double mathematicsScore,
                   List<String> technicalSkills) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.technicalSkills = technicalSkills;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public double getAverageScore() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    public String getPerformanceCategory() {
        double avg = getAverageScore();

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    public String getStrongerSubject() {
        if (programmingScore > mathematicsScore)
            return "Programming";
        else if (mathematicsScore > programmingScore)
            return "Mathematics";
        else
            return "Equal";
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", courseName='" + courseName + '\'' +
                ", programmingScore=" + programmingScore +
                ", mathematicsScore=" + mathematicsScore +
                ", technicalSkills=" + technicalSkills +
                ", average=" + getAverageScore() +
                ", category=" + getPerformanceCategory() +
                ", strongerSubject=" + getStrongerSubject() +
                '}';
    }

    @Override //these method make student id the basis for comparing 2 student objects
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Student student = (Student) obj;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}
