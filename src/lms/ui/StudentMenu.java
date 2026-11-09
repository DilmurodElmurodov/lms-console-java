package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import java.util.*;

public class StudentMenu {
    private final LMSContext ctx;

    public StudentMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("STUDENT MANAGEMENT");
            System.out.println("  1. Add new student");
            System.out.println("  2. View all students");
            System.out.println("  3. Search for a student");
            System.out.println("  4. View student information");
            System.out.println("  5. Enrolling the student in science");
            System.out.println("  6. Delete Student");
            System.out.println("  7. Students by class");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> ctx.studentService.printAllStudents();
                case 3 -> searchStudent();
                case 4 -> viewStudent();
                case 5 -> enrollToSubject();
                case 6 -> deleteStudent();
                case 7 -> studentsByGrade();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void addStudent() {
        try {
            String fn = ConsoleHelper.readLine("Name        : ");
            String ln = ConsoleHelper.readLine("Surname   : ");
            String em = ConsoleHelper.readLine("Email      : ");
            String pw = ConsoleHelper.readLine("Password:      : ");
            int    gr = ConsoleHelper.readInt ("Class (1-12): ");
            Student s = ctx.studentService.addStudent(fn, ln, em, pw, gr);
            ConsoleHelper.printSuccess("Added: " + s);
        } catch (Exception e) { ConsoleHelper.printError(e.getMessage()); }
    }

    private void searchStudent() {
        String name = ConsoleHelper.readLine("Name/Surname: ");
        List<Student> found = ctx.studentService.searchStudents(name);
        if (found.isEmpty()) ConsoleHelper.printInfo("Not found.");
        else found.forEach(s -> System.out.println("    " + s));
    }

    private void viewStudent() {
        int id = ConsoleHelper.readInt("Student ID: ");
        ctx.studentService.getStudentById(id).ifPresentOrElse(s -> {
            s.displayInfo();
            if (!s.getEnrolledSubjects().isEmpty()) {
                System.out.println("Subjects:");
                s.getEnrolledSubjects().forEach(sub ->
                        System.out.println("    - " + sub.getName()));
            }
        }, () -> ConsoleHelper.printError("Not found!"));
    }

    private void enrollToSubject() {
        int sid = ConsoleHelper.readInt("Student ID: ");
        Optional<Student> sOpt = ctx.studentService.getStudentById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Student not found!"); return; }
        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Subject not found!"); return; }
        ctx.studentService.enrollStudentToSubject(sOpt.get(), subOpt.get());
        ConsoleHelper.printSuccess(sOpt.get().getFullName() + " enrolled in science!");
    }

    private void deleteStudent() {
        int id = ConsoleHelper.readInt("ID for deletion: ");
        if (ctx.studentService.deleteStudent(id)) ConsoleHelper.printSuccess("Deleted.");
        else ConsoleHelper.printError("Not found!");
    }

    private void studentsByGrade() {
        int grade = ConsoleHelper.readInt("Class: ");
        List<Student> list = ctx.studentService.getStudentsByGrade(grade);
        if (list.isEmpty()) ConsoleHelper.printInfo(grade + "11-no students in the class..");
        else list.forEach(s -> System.out.println("    " + s));
    }
}