package lms.util;

import lms.model.*;
import lms.service.LMSContext;
import java.time.LocalDate;

public class DataSeeder {

    public static void seed(LMSContext ctx) {
        // === TEACHERS ===
        Teacher t1 = ctx.teacherService.addTeacher(
                "Aziz", "Karimov", "aziz@lms.uz", "pass123", "Math");
        Teacher t2 = ctx.teacherService.addTeacher(
                "Malika", "Yusupova", "malika@lms.uz", "pass123", "Informatics");
        Teacher t3 = ctx.teacherService.addTeacher(
                "Bobur", "Toshmatov", "bobur@lms.uz", "pass123", "Physics");

        // === SUBJECTS ===
        Subject math    = ctx.subjectService.addSubject("Math",     "Calculus for Engineers", 4);
        Subject java    = ctx.subjectService.addSubject("Java dev", "OOP programming",          5);
        Subject physics = ctx.subjectService.addSubject("Physics",          "Classic mechanics",      3);
        Subject english = ctx.subjectService.addSubject("English language",     "B2 level",           3);

        // === CONNECTING SUBJECTS TO TEACHERS ===
        ctx.subjectService.assignTeacher(math,    t1);
        ctx.subjectService.assignTeacher(java,    t2);
        ctx.subjectService.assignTeacher(physics, t3);
        ctx.subjectService.assignTeacher(english, t1);

        // === STUDENTS ===
        Student s1 = ctx.studentService.addStudent(
                "Jasur",   "Raximov",  "jasur@student.uz",   "pass123", 10);
        Student s2 = ctx.studentService.addStudent(
                "Nilufar", "Qodirova", "nilufar@student.uz", "pass123", 10);
        Student s3 = ctx.studentService.addStudent(
                "Sherzod", "Aliyev",   "sherzod@student.uz", "pass123", 11);
        Student s4 = ctx.studentService.addStudent(
                "Maftuna", "Hasanova", "maftuna@student.uz", "pass123", 11);
        Student s5 = ctx.studentService.addStudent(
                "Doniyor", "Mirzayev", "doniyor@student.uz", "pass123", 10);

        // === SUBJECTS RECORDING ===
        ctx.studentService.enrollStudentToSubject(s1, math);
        ctx.studentService.enrollStudentToSubject(s1, java);
        ctx.studentService.enrollStudentToSubject(s1, english);
        ctx.studentService.enrollStudentToSubject(s2, math);
        ctx.studentService.enrollStudentToSubject(s2, java);
        ctx.studentService.enrollStudentToSubject(s2, physics);
        ctx.studentService.enrollStudentToSubject(s3, java);
        ctx.studentService.enrollStudentToSubject(s3, physics);
        ctx.studentService.enrollStudentToSubject(s3, english);
        ctx.studentService.enrollStudentToSubject(s4, math);
        ctx.studentService.enrollStudentToSubject(s4, physics);
        ctx.studentService.enrollStudentToSubject(s4, english);
        ctx.studentService.enrollStudentToSubject(s5, math);
        ctx.studentService.enrollStudentToSubject(s5, java);
        ctx.studentService.enrollStudentToSubject(s5, physics);
        ctx.studentService.enrollStudentToSubject(s5, english);

        // === ATTENDANCE ===
        LocalDate today = LocalDate.now();
        ctx.attendanceService.markAttendance(s1, math, today,              AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s1, math, today.minusDays(1), AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s1, math, today.minusDays(2), AttendanceStatus.ABSENT);
        ctx.attendanceService.markAttendance(s2, math, today,              AttendanceStatus.LATE, "5 daqiqa kech");
        ctx.attendanceService.markAttendance(s2, math, today.minusDays(1), AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s3, java, today,              AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s3, java, today.minusDays(1), AttendanceStatus.EXCUSED, "Kasalligi bor");
        ctx.attendanceService.markAttendance(s4, physics, today,           AttendanceStatus.ABSENT);
        ctx.attendanceService.markAttendance(s4, physics, today.minusDays(1), AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s5, java, today,              AttendanceStatus.PRESENT);
        ctx.attendanceService.markAttendance(s5, java, today.minusDays(1), AttendanceStatus.PRESENT);

        // === GRADES ===
        ctx.gradeService.setScores(s1, math,    78, 85, 90);
        ctx.gradeService.setScores(s1, java,    92, 88, 95);
        ctx.gradeService.setScores(s1, english, 70, 75, 80);
        ctx.gradeService.setScores(s2, math,    65, 70, 72);
        ctx.gradeService.setScores(s2, java,    80, 85, 88);
        ctx.gradeService.setScores(s2, physics, 55, 60, 65);
        ctx.gradeService.setScores(s3, java,    95, 92, 98);
        ctx.gradeService.setScores(s3, physics, 88, 90, 85);
        ctx.gradeService.setScores(s3, english, 75, 78, 80);
        ctx.gradeService.setScores(s4, math,    60, 65, 70);
        ctx.gradeService.setScores(s4, physics, 72, 68, 75);
        ctx.gradeService.setScores(s4, english, 85, 88, 90);
        ctx.gradeService.setScores(s5, math,    88, 92, 85);
        ctx.gradeService.setScores(s5, java,    76, 80, 82);
        ctx.gradeService.setScores(s5, physics, 90, 94, 92);
        ctx.gradeService.setScores(s5, english, 68, 72, 70);

        System.out.println("  [+] Demo data loaded!");
    }
}