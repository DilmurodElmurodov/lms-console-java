package lms.service;

import lms.model.*;
import lms.repository.AttendanceRepository;
import java.time.LocalDate;
import java.util.*;

public class AttendanceService {
    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) { this.repo = repo; }

    public Attendance markAttendance(Student student, Subject subject,
                                     LocalDate date, AttendanceStatus status) {
        return markAttendance(student, subject, date, status, "");
    }

    public Attendance markAttendance(Student student, Subject subject,
                                     LocalDate date, AttendanceStatus status, String remarks) {
        if (student == null) throw new IllegalArgumentException("Student topilmadi!");
        if (subject == null) throw new IllegalArgumentException("Fan topilmadi!");
        if (date == null)    throw new IllegalArgumentException("Sana kiritilmagan!");

        Optional<Attendance> existing = repo.findAll().stream()
                .filter(a -> a.getStudent().getId() == student.getId()
                        && a.getSubject().getId() == subject.getId()
                        && a.getDate().equals(date))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setStatus(status);
            existing.get().setRemarks(remarks);
            return existing.get();
        }

        Attendance a = new Attendance(student, subject, date, status, remarks);
        repo.add(a);
        subject.addAttendance(a);
        return a;
    }

    public List<Attendance> getAttendanceByStudent(Student s) { return repo.findByStudent(s); }
    public List<Attendance> getAttendanceBySubject(Subject sub) { return repo.findBySubject(sub); }
    public List<Attendance> getAttendanceByDate(LocalDate d) { return repo.findByDate(d); }
    public List<Attendance> getAttendanceByStudentAndSubject(Student s, Subject sub) {
        return repo.findByStudentAndSubject(s, sub);
    }
    public double getAttendancePercentage(Student s, Subject sub) {
        return repo.getAttendancePercentage(s, sub);
    }
    public int getTotalAttendanceRecords() { return repo.count(); }

    public void printStudentAttendanceReport(Student student, Subject subject) {
        List<Attendance> records = repo.findByStudentAndSubject(student, subject);
        System.out.println("\n=== DAVOMAT HISOBOTI ===");
        System.out.println("Student : " + student.getFullName());
        System.out.println("Fan     : " + subject.getName());
        System.out.println("-".repeat(60));
        if (records.isEmpty()) {
            System.out.println("Hali davomat yozilmagan.");
        } else {
            records.forEach(a -> System.out.println("  " + a));
            long present = records.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
            long absent  = records.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
            long late    = records.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
            long excused = records.stream().filter(a -> a.getStatus() == AttendanceStatus.EXCUSED).count();
            System.out.println("-".repeat(60));
            System.out.printf("  Keldi: %d | Kelmadi: %d | Kech: %d | Uzrli: %d%n",
                    present, absent, late, excused);
            System.out.printf("  Davomat %%: %.1f%%%n",
                    repo.getAttendancePercentage(student, subject));
        }
        System.out.println("=".repeat(60));
    }

    public void printSubjectAttendanceSummary(Subject subject, LocalDate date) {
        List<Attendance> records = repo.findBySubjectAndDate(subject, date);
        System.out.println("\n=== " + subject.getName() + " | " + date + " ===");
        System.out.println("-".repeat(60));
        if (records.isEmpty()) System.out.println("Bu kun uchun davomat yozilmagan.");
        else records.forEach(a -> System.out.printf("  %-20s | %s%n",
                a.getStudent().getFullName(), a.getStatus()));
        System.out.println("=".repeat(60));
    }
}