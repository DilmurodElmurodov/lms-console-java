package lms.ui;

import lms.model.*;
import lms.service.LMSContext;
import lms.util.ConsoleHelper;

public class SubjectMenu {
    private final LMSContext ctx;

    public SubjectMenu(LMSContext ctx) { this.ctx = ctx; }

    public void show() {
        while (true) {
            ConsoleHelper.printTitle("SCIENCE MANAGEMENT");
            System.out.println("  1. Add a new science");
            System.out.println("  2. All subjects");
            System.out.println("  3. Science data");
            System.out.println("  4. Students in science");
            System.out.println("  5. Science search");
            System.out.println("  6. Turn off the fan");
            System.out.println("  0. Back");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Choice: ");
            switch (choice) {
                case 1 -> add();
                case 2 -> ctx.subjectService.printAllSubjects();
                case 3 -> detail();
                case 4 -> students();
                case 5 -> search();
                case 6 -> delete();
                case 0 -> { return; }
                default -> ConsoleHelper.printError("Wrong choice!");
            }
            if (choice != 0) ConsoleHelper.pressEnter();
        }
    }

    private void add() {
        try {
            String name = ConsoleHelper.readLine("Subject name  : ");
            String desc = ConsoleHelper.readLine("Description    : ");
            int    cr   = ConsoleHelper.readInt ("Credits : ");
            Subject s = ctx.subjectService.addSubject(name, desc, cr);
            ConsoleHelper.printSuccess("Added: " + s);
        } catch (Exception e) { ConsoleHelper.printError(e.getMessage()); }
    }

    private void detail() {
        int id = ConsoleHelper.readInt("Subject ID: ");
        ctx.subjectService.getSubjectById(id).ifPresentOrElse(
                Subject::displayInfo,
                () -> ConsoleHelper.printError("Not found!"));
    }

    private void students() {
        int id = ConsoleHelper.readInt("Subject ID: ");
        ctx.subjectService.getSubjectById(id).ifPresentOrElse(sub -> {
            System.out.println("\n  [" + sub.getName() + "] Students ("
                    + sub.getTotalStudents() + " ta):");
            if (sub.getEnrolledStudents().isEmpty())
                ConsoleHelper.printInfo("Empty.");
            else
                sub.getEnrolledStudents().forEach(s -> System.out.println("    " + s));
        }, () -> ConsoleHelper.printError("Not found!"));
    }

    private void search() {
        String kw = ConsoleHelper.readLine("Search: ");
        ctx.subjectService.searchSubjects(kw).forEach(s -> System.out.println("    " + s));
    }

    private void delete() {
        int id = ConsoleHelper.readInt("Subject ID: ");
        if (ctx.subjectService.deleteSubject(id)) ConsoleHelper.printSuccess("Deleted.");
        else ConsoleHelper.printError("Not found!");
    }
}