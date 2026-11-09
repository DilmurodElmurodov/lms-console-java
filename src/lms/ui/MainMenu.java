package lms.ui;

import lms.service.LMSContext;
import lms.util.ConsoleHelper;
import lms.util.DataSeeder;

public class MainMenu {
    private final LMSContext     ctx;
    private final StudentMenu    studentMenu;
    private final TeacherMenu    teacherMenu;
    private final SubjectMenu    subjectMenu;
    private final AttendanceMenu attendanceMenu;
    private final GradeMenu      gradeMenu;
    private final ReportMenu     reportMenu;

    public MainMenu() {
        ctx            = new LMSContext();
        studentMenu    = new StudentMenu(ctx);
        teacherMenu    = new TeacherMenu(ctx);
        subjectMenu    = new SubjectMenu(ctx);
        attendanceMenu = new AttendanceMenu(ctx);
        gradeMenu      = new GradeMenu(ctx);
        reportMenu     = new ReportMenu(ctx);
    }

    public void start() {
        printBanner();
        System.out.print("  Loading demo data...");
        DataSeeder.seed(ctx);

        while (true) {
            ConsoleHelper.printTitle("MAIN MENU — LMS");
            System.out.println("  1.  Student administration");
            System.out.println("  2.  Teacher management");
            System.out.println("  3.  Science management");
            System.out.println("  4.  Attendance management");
            System.out.println("  5.  Grade management");
            System.out.println("  6.  Reports and statistics");
            System.out.println("  0.  Exit the program");
            ConsoleHelper.printLine();

            switch (ConsoleHelper.readInt("Enter your choice: ")) {
                case 1 -> studentMenu.show();
                case 2 -> teacherMenu.show();
                case 3 -> subjectMenu.show();
                case 4 -> attendanceMenu.show();
                case 5 -> gradeMenu.show();
                case 6 -> reportMenu.show();
                case 0 -> {
                    System.out.println("\n  Bye! You are logged out of the LMS system...\n");
                    System.exit(0);
                }
                default -> ConsoleHelper.printError("Wrong choice! Choose between 0-6.");
            }
        }
    }

    private void printBanner() {
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println("  ║    LMS — Learning Management System      ║");
        System.out.println("  ║        Java OOP | Console Application    ║");
        System.out.println("  ╚══════════════════════════════════════════╝\n");
    }
}