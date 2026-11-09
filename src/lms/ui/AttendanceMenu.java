package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import java.time.LocalDate;
import java.util.Optional;

public class AttendanceMenu {
    private final LMSContext ctx;

    public AttendanceMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("ATTENDANCE MANAGEMENT");
            System.out.println("  1. Schedule attendance today");
            System.out.println("  2. Enter a date to mark attendance");
            System.out.println("  3. View student attendance");
            System.out.println("  4. Science attendance (by date)");
            System.out.println("  5. Attendance percentage");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> mark(LocalDate.now());
                case 2 -> markCustom();
                case 3 -> viewStudentAtt();
                case 4 -> viewSubjectByDate();
                case 5 -> viewPercentage();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void mark(LocalDate date) {
        ctx.studentService.printAllStudents();
        int sid = ConsoleHelper.readInt("Student ID: ");
        Optional<Student> sOpt = ctx.studentService.getStudentById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Student not found!"); return; }

        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Subject not found !"); return; }

        System.out.println("  1-CAME 2-DID NOT COME 3-LATE 4-EXCUSED");
        AttendanceStatus status = switch (ConsoleHelper.readInt("Status: ")) {
            case 1 -> AttendanceStatus.PRESENT;
            case 2 -> AttendanceStatus.ABSENT;
            case 3 -> AttendanceStatus.LATE;
            case 4 -> AttendanceStatus.EXCUSED;
            default -> AttendanceStatus.ABSENT;
        };
        String remarks = ConsoleHelper.readLine("Comment (         ): ");
        ctx.attendanceService.markAttendance(sOpt.get(), subOpt.get(), date, status, remarks);
        ConsoleHelper.printSuccess("Attendance is set: " + status);
    }

    private void markCustom() {
        String ds = ConsoleHelper.readLine("Date (YYYY-MM-DD): ");
        try { mark(LocalDate.parse(ds)); }
        catch (Exception e) { ConsoleHelper.printError("Invalid date format!"); }
    }

    private void viewStudentAtt() {
        int sid = ConsoleHelper.readInt("Student ID: ");
        Optional<Student> sOpt = ctx.studentService.getStudentById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Not found!"); return; }
        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Science not found!"); return; }
        ctx.attendanceService.printStudentAttendanceReport(sOpt.get(), subOpt.get());
    }

    private void viewSubjectByDate() {
        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Science not found!"); return; }
        System.out.println("  1-Today 2-Next week");
        LocalDate date;
        if (ConsoleHelper.readInt("Choice: ") == 1) {
            date = LocalDate.now();
        } else {
            try { date = LocalDate.parse(ConsoleHelper.readLine("Date (YYYY-MM-DD): ")); }
            catch (Exception e) { ConsoleHelper.printError("Wrong Date!"); return; }
        }
        ctx.attendanceService.printSubjectAttendanceSummary(subOpt.get(), date);
    }

    private void viewPercentage() {
        int sid = ConsoleHelper.readInt("Student ID: ");
        Optional<Student> sOpt = ctx.studentService.getStudentById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Not found!"); return; }
        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Science not found!"); return; }
        double pct = ctx.attendanceService.getAttendancePercentage(sOpt.get(), subOpt.get());
        System.out.printf("%n  %s — %s attendance: %.1f%%%n",
                sOpt.get().getFullName(), subOpt.get().getName(), pct);
    }
}