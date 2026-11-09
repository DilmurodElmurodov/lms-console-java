package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import java.util.*;

public class TeacherMenu {
    private final LMSContext ctx;

    public TeacherMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("TEACHER MANAGEMENT");
            System.out.println("  1. Add a new teacher");
            System.out.println("  2. All teachers");
            System.out.println("  3. Search for a teacher");
            System.out.println("  4. Teacher information");
            System.out.println("  5. Attach science");
            System.out.println("  6. By department");
            System.out.println("  7. Delete teacher");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> addTeacher();
                case 2 -> ctx.teacherService.printAllTeachers();
                case 3 -> search();
                case 4 -> viewDetail();
                case 5 -> assignSubject();
                case 6 -> byDept();
                case 7 -> delete();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void addTeacher() {
        try {
            String fn   = ConsoleHelper.readLine("Name     : ");
            String ln   = ConsoleHelper.readLine("Surname  : ");
            String em   = ConsoleHelper.readLine("Email    : ");
            String pw   = ConsoleHelper.readLine("Parol    : ");
            String dept = ConsoleHelper.readLine("Module   : ");
            Teacher t = ctx.teacherService.addTeacher(fn, ln, em, pw, dept);
            ConsoleHelper.printSuccess("Added: " + t);
        } catch (Exception e) { ConsoleHelper.printError(e.getMessage()); }
    }

    private void search() {
        String name = ConsoleHelper.readLine("Search name: ");
        List<Teacher> found = ctx.teacherService.searchTeachers(name);
        if (found.isEmpty()) ConsoleHelper.printInfo("Not found!.");
        else found.forEach(t -> System.out.println("    " + t));
    }

    private void viewDetail() {
        int id = ConsoleHelper.readInt("ID: ");
        ctx.teacherService.getTeacherById(id).ifPresentOrElse(t -> {
            t.displayInfo();
            t.getAssignedSubjects().forEach(s -> System.out.println("    - " + s.getName()));
        }, () -> ConsoleHelper.printError("Not found!.!"));
    }

    private void assignSubject() {
        int tid = ConsoleHelper.readInt("Teacher ID: ");
        Optional<Teacher> tOpt = ctx.teacherService.getTeacherById(tid);
        if (tOpt.isEmpty()) { ConsoleHelper.printError("Not found!"); return; }
        ctx.subjectService.printAllSubjects();
        int sid = ConsoleHelper.readInt("Subject ID: ");
        Optional<Subject> sOpt = ctx.subjectService.getSubjectById(sid);
        if (sOpt.isEmpty()) { ConsoleHelper.printError("Subject not found!"); return; }
        ctx.subjectService.assignTeacher(sOpt.get(), tOpt.get());
        ConsoleHelper.printSuccess("Attached!");
    }

    private void byDept() {
        String dept = ConsoleHelper.readLine("Module Department: ");
        List<Teacher> list = ctx.teacherService.getTeachersByDepartment(dept);
        if (list.isEmpty()) ConsoleHelper.printInfo("There is no teacher in this section..");
        else list.forEach(t -> System.out.println("    " + t));
    }

    private void delete() {
        int id = ConsoleHelper.readInt("ID: ");
        if (ctx.teacherService.deleteTeacher(id)) ConsoleHelper.printSuccess("Deleted.");
        else ConsoleHelper.printError("Not found!");
    }
}