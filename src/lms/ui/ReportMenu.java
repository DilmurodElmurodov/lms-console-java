package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import java.util.List;

public class ReportMenu {
    private final LMSContext ctx;

    public ReportMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("REPORTS AND STATISTICS");
            System.out.println("  1. Dashboard (General Statistics)");
            System.out.println("  2. List of all students");
            System.out.println("  3. List of all teachers");
            System.out.println("  4. Summary of sciences");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> dashboard();
                case 2 -> ctx.studentService.printAllStudents();
                case 3 -> ctx.teacherService.printAllTeachers();
                case 4 -> subjectSummary();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void dashboard() {
        System.out.println("\n  +------------------------------------------+");
        System.out.printf ("  |  All Students        : %4d               |%n",
                ctx.studentService.getTotalStudents());
        System.out.printf ("  |  All Teachers        : %4d               |%n",
                ctx.teacherService.getTotalTeachers());
        System.out.printf ("  |  All Subjects        : %4d               |%n",
                ctx.subjectService.getTotalSubjects());
        System.out.printf ("  |  Attendance records  : %4d               |%n",
                ctx.attendanceService.getTotalAttendanceRecords());
        System.out.println("  +------------------------------------------+");
    }

    private void subjectSummary() {
        List<Subject> subjects = ctx.subjectService.getAllSubjects();
        System.out.println("\n========= SUMMARY OF SCIENCES =========");
        System.out.printf("%-8s %-20s %-15s %-8s %-8s%n",
                "Code", "Subject", "Teacehrs", "Credit", "Student");
        System.out.println("-".repeat(65));
        subjects.forEach(s -> System.out.printf("%-8s %-20s %-15s %-8d %-8d%n",
                s.getSubjectCode(),
                s.getName(),
                s.getTeacher() != null ? s.getTeacher().getLastName() : "N/A",
                s.getCreditHours(),
                s.getTotalStudents()));
        System.out.println("=".repeat(65));
    }
}