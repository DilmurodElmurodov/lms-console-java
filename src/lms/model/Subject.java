package lms.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private static int idCounter = 1;

    private int id;
    private String subjectCode;
    private String name;
    private String description;
    private int creditHours;
    private Teacher teacher;
    private List<Student> enrolledStudents;
    private List<Attendance> attendanceList;

    public Subject(String name, String description, int creditHours) {
        this.id = idCounter++;
        this.subjectCode = "SUB" + String.format("%04d", id);
        this.name = name;
        this.description = description;
        this.creditHours = creditHours;
        this.enrolledStudents = new ArrayList<>();
        this.attendanceList = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getSubjectCode() { return subjectCode; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCreditHours() { return creditHours; }
    public Teacher getTeacher() { return teacher; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public List<Attendance> getAttendanceList() { return attendanceList; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public void enrollStudent(Student s) {
        if (!enrolledStudents.contains(s)) enrolledStudents.add(s);
    }

    public void unenrollStudent(Student s) { enrolledStudents.remove(s); }
    public void addAttendance(Attendance a) { attendanceList.add(a); }
    public int getTotalStudents() { return enrolledStudents.size(); }

    public void displayInfo() {
        System.out.println("=== SUBJECT INFO ===");
        System.out.println("Code        : " + subjectCode);
        System.out.println("Name        : " + name);
        System.out.println("Description : " + description);
        System.out.println("Credits     : " + creditHours);
        System.out.println("Teacher     : " + (teacher != null ? teacher.getFullName() : "Not assigned"));
        System.out.println("Students    : " + enrolledStudents.size());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Credits: %d | Teacher: %s | Students: %d",
                subjectCode, name, creditHours,
                teacher != null ? teacher.getFullName() : "N/A",
                enrolledStudents.size());
    }
}