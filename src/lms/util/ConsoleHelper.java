package lms.util;

import java.util.Scanner;

public class ConsoleHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please, enter a number!");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please, enter a number (examples: 85.5)!");
            }
        }
    }

    public static void pressEnter() {
        System.out.print("\n  [ENTER] press to start...");
        scanner.nextLine();
    }

    public static void printLine()  { System.out.println("=".repeat(60)); }

    public static void printTitle(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   " + title);
        System.out.println("=".repeat(60));
    }

    public static void printSuccess(String msg) { System.out.println("  [+] " + msg); }
    public static void printError(String msg)   { System.out.println("  [!] error: " + msg); }
    public static void printInfo(String msg)     { System.out.println("  [i] " + msg); }
}