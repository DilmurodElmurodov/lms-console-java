package lms.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private static int idCounter = 1;

    private int id;
    private Student student;
    private Subject subject;
    private LocalDate date;
    private AttendanceStatus status;
    private String remarks;

    public Attendance(Student student, Subject subject, LocalDate date, AttendanceStatus status) {
        this.id = idCounter++;
        this.student = student;
        this.subject = subject;
        this.date = date;
        this.status = status;
        this.remarks = "";
    }

    public Attendance(Student student, Subject subject, LocalDate date,
                      AttendanceStatus status, String remarks) {
        this(student, subject, date, status);
        this.remarks = remarks;
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public Subject getSubject() { return subject; }
    public LocalDate getDate() { return date; }
    public AttendanceStatus getStatus() { return status; }
    public String getRemarks() { return remarks; }

    public void setStatus(AttendanceStatus status) { this.status = status; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT || status == AttendanceStatus.LATE;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Subject: %s | Status: %s | Remarks: %s",
                getFormattedDate(), student.getFullName(),
                subject.getName(), status,
                remarks.isEmpty() ? "-" : remarks);
    }
}