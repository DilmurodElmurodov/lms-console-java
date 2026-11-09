package lms.service;

import lms.model.*;
import lms.repository.GradeRepository;
import java.util.*;

public class GradeService {
    private final GradeRepository repo;

    public GradeService(GradeRepository repo) { this.repo = repo; }

    public Grade getOrCreateGrade(Student s, Subject sub) {
        return repo.findByStudentAndSubject(s, sub).orElseGet(() -> {
            Grade g = new Grade(s, sub);
            repo.add(g);
            return g;
        });
    }

    public void setScores(Student s, Subject sub,
                          double midterm, double finalScore, double assignment) {
        if (midterm < 0 || midterm > 100 || finalScore < 0 || finalScore > 100
                || assignment < 0 || assignment > 100)
            throw new IllegalArgumentException("Baholar 0-100 oralig'ida bo'lishi kerak!");
        Grade g = getOrCreateGrade(s, sub);
        g.setMidtermScore(midterm);
        g.setFinalScore(finalScore);
        g.setAssignmentScore(assignment);
    }

    public Optional<Grade> getGrade(Student s, Subject sub) {
        return repo.findByStudentAndSubject(s, sub);
    }

    public List<Grade> getGradesByStudent(Student s) { return repo.findByStudent(s); }
    public List<Grade> getGradesBySubject(Subject sub) { return repo.findBySubject(sub); }
    public double getSubjectAverage(Subject sub) { return repo.getAverageScore(sub); }

    public void printStudentTranscript(Student student) {
        List<Grade> grades = repo.findByStudent(student);
        System.out.println("\n=== TALABA BAHONOMASI ===");
        System.out.println("Student : " + student.getFullName()
                + " (" + student.getStudentCode() + ")");
        System.out.printf("%-20s | %6s | %6s | %6s | %7s | %s%n",
                "Fan", "Midterm", "Final", "Topsh.", "Umumiy", "Baho");
        System.out.println("-".repeat(65));
        if (grades.isEmpty()) {
            System.out.println("Hali baho yo'q.");
        } else {
            double total = 0;
            for (Grade g : grades) {
                System.out.printf("%-20s | %6.1f | %6.1f | %6.1f | %7.1f | %s%n",
                        g.getSubject().getName(), g.getMidtermScore(),
                        g.getFinalScore(), g.getAssignmentScore(),
                        g.getOverallScore(), g.getLetterGrade());
                total += g.getOverallScore();
            }
            System.out.println("-".repeat(65));
            System.out.printf("GPA: %.2f%n", total / grades.size());
        }
        System.out.println("=".repeat(65));
    }

    public void printSubjectGrades(Subject subject) {
        List<Grade> grades = repo.findBySubject(subject);
        System.out.println("\n=== " + subject.getName() + " - BAHOLAR ===");
        System.out.printf("%-20s | %6s | %6s | %6s | %7s | %s%n",
                "Student", "Midterm", "Final", "Topsh.", "Umumiy", "Baho");
        System.out.println("-".repeat(65));
        if (grades.isEmpty()) System.out.println("Hali baho yo'q.");
        else {
            grades.forEach(g ->
                    System.out.printf("%-20s | %6.1f | %6.1f | %6.1f | %7.1f | %s%n",
                            g.getStudent().getFullName(), g.getMidtermScore(),
                            g.getFinalScore(), g.getAssignmentScore(),
                            g.getOverallScore(), g.getLetterGrade()));
            System.out.println("-".repeat(65));
            System.out.printf("O'rtacha: %.2f%n", repo.getAverageScore(subject));
        }
        System.out.println("=".repeat(65));
    }
}