package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import java.util.Optional;

public class GradeMenu {
    private final LMSContext ctx;

    public GradeMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("ASSESSMENT MANAGEMENT");
            System.out.println("  1. Add/update rating");
            System.out.println("  2. Student excuse");
            System.out.println("  3. Science grade list");
            System.out.println("  4. Science GPA");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> enterGrade();
                case 2 -> transcript();
                case 3 -> subjectGrades();
                case 4 -> avgScore();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void enterGrade() {
        ctx.studentService.printAllStudents();
        int sid = ConsoleHelper.readInt("Student ID: ");
        Optional<Student> sOpt = ctx.studentService.getStudentById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Not found!"); return; }

        ctx.subjectService.printAllSubjects();
        int subId = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> subOpt = ctx.subjectService.getSubjectById(subId);
        if (subOpt.isEmpty()) { ConsoleHelper.printError("Science not found !"); return; }

        try {
            double mid   = ConsoleHelper.readDouble("Midterm    (0-100): ");
            double fin   = ConsoleHelper.readDouble("Final      (0-100): ");
            double assig = ConsoleHelper.readDouble("Assignment (0-100): ");
            ctx.gradeService.setScores(sOpt.get(), subOpt.get(), mid, fin, assig);
            ConsoleHelper.printSuccess("Save!");
            ctx.gradeService.getGrade(sOpt.get(), subOpt.get())
                    .ifPresent(g -> System.out.println("  Result: " + g));
        } catch (Exception e) { ConsoleHelper.printError(e.getMessage()); }
    }

    private void transcript() {
        int id = ConsoleHelper.readInt("Student ID: ");
        ctx.studentService.getStudentById(id).ifPresentOrElse(
                ctx.gradeService::printStudentTranscript,
                () -> ConsoleHelper.printError("Not found!"));
    }

    private void subjectGrades() {
        ctx.subjectService.printAllSubjects();
        int id = ConsoleHelper.readInt("Subject ID: ");
        ctx.subjectService.getSubjectById(id).ifPresentOrElse(
                ctx.gradeService::printSubjectGrades,
                () -> ConsoleHelper.printError("Not found!"));
    }

    private void avgScore() {
        ctx.subjectService.printAllSubjects();
        int id = ConsoleHelper.readInt("Subject ID: ");
        ctx.subjectService.getSubjectById(id).ifPresentOrElse(sub -> {
            double avg = ctx.gradeService.getSubjectAverage(sub);
            System.out.printf("%n  [%s] average grade: %.2f%n", sub.getName(), avg);
        }, () -> ConsoleHelper.printError("Not found!"));
    }
}