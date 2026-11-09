package lms.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private String studentCode;
    private int grade;
    private List<Subject> enrolledSubjects;

    public Student(String firstName, String lastName, String email, String password, int grade) {
        super(firstName, lastName, email, password, Role.STUDENT);
        this.grade = grade;
        this.studentCode = "STU" + String.format("%04d", getId());
        this.enrolledSubjects = new ArrayList<>();
    }

    public String getStudentCode() { return studentCode; }
    public int getGrade() { return grade; }
    public List<Subject> getEnrolledSubjects() { return enrolledSubjects; }
    public void setGrade(int grade) { this.grade = grade; }

    public void enrollSubject(Subject subject) {
        if (!enrolledSubjects.contains(subject))
            enrolledSubjects.add(subject);
    }

    public void unenrollSubject(Subject subject) { enrolledSubjects.remove(subject); }
    public int getTotalSubjects() { return enrolledSubjects.size(); }

    @Override
    public void displayInfo() {
        System.out.println("=== STUDENT INFO ===");
        System.out.println("Code     : " + studentCode);
        System.out.println("Name     : " + getFullName());
        System.out.println("Email    : " + getEmail());
        System.out.println("Grade    : " + grade);
        System.out.println("Subjects : " + enrolledSubjects.size());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Grade: %d | Subjects: %d",
                studentCode, getFullName(), grade, enrolledSubjects.size());
    }
}