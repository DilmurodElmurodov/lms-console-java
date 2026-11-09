package lms.service;

import lms.model.*;
import lms.repository.StudentRepository;
import java.util.*;

public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) { this.repo = repo; }

    public Student addStudent(String firstName, String lastName,
                              String email, String password, int grade) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("Ism bo'sh bo'lishi mumkin emas!");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email noto'g'ri formatda!");
        if (repo.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Bu email allaqachon ro'yxatdan o'tgan!");
        if (grade < 1 || grade > 12)
            throw new IllegalArgumentException("Sinf 1-12 oralig'ida bo'lishi kerak!");

        Student s = new Student(firstName.trim(), lastName.trim(), email.trim(), password, grade);
        repo.add(s);
        return s;
    }

    public List<Student> getAllStudents() { return repo.findAll(); }
    public Optional<Student> getStudentById(int id) { return repo.findById(id); }
    public Optional<Student> getStudentByCode(String code) { return repo.findByStudentCode(code); }
    public List<Student> searchStudents(String name) { return repo.searchByName(name); }
    public List<Student> getStudentsByGrade(int grade) { return repo.findByGrade(grade); }
    public boolean updateStudent(Student s) { return repo.update(s); }
    public boolean deleteStudent(int id) { return repo.delete(id); }
    public int getTotalStudents() { return repo.count(); }

    public void enrollStudentToSubject(Student s, Subject sub) {
        s.enrollSubject(sub);
        sub.enrollStudent(s);
    }
    public void unenrollStudentFromSubject(Student s, Subject sub) {
        s.unenrollSubject(sub);
        sub.unenrollStudent(s);
    }

    public void printAllStudents() {
        List<Student> list = getAllStudents();
        if (list.isEmpty()) { System.out.println("Hech qanday student yo'q."); return; }
        System.out.println("\n========= BARCHA STUDENTLAR (" + list.size() + " ta) =========");
        list.forEach(s -> System.out.println("  " + s));
        System.out.println("=".repeat(50));
    }
}