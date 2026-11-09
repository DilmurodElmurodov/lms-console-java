package lms.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {

    private String teacherCode;
    private String department;
    private List<Subject> assignedSubjects;

    public Teacher(String firstName, String lastName, String email, String password, String department) {
        super(firstName, lastName, email, password, Role.TEACHER);
        this.department = department;
        this.teacherCode = "TCH" + String.format("%04d", getId());
        this.assignedSubjects = new ArrayList<>();
    }

    public String getTeacherCode() { return teacherCode; }
    public String getDepartment() { return department; }
    public List<Subject> getAssignedSubjects() { return assignedSubjects; }
    public void setDepartment(String department) { this.department = department; }

    public void assignSubject(Subject subject) {
        if (!assignedSubjects.contains(subject))
            assignedSubjects.add(subject);
    }

    public void removeSubject(Subject subject) { assignedSubjects.remove(subject); }
    public int getTotalSubjects() { return assignedSubjects.size(); }

    @Override
    public void displayInfo() {
        System.out.println("=== TEACHER INFO ===");
        System.out.println("Code       : " + teacherCode);
        System.out.println("Name       : " + getFullName());
        System.out.println("Email      : " + getEmail());
        System.out.println("Department : " + department);
        System.out.println("Subjects   : " + assignedSubjects.size());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Dept: %s | Subjects: %d",
                teacherCode, getFullName(), department, assignedSubjects.size());
    }
}