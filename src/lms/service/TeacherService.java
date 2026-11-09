package lms.service;

import lms.model.*;
import lms.repository.TeacherRepository;
import java.util.*;

public class TeacherService {
    private final TeacherRepository repo;

    public TeacherService(TeacherRepository repo) { this.repo = repo; }

    public Teacher addTeacher(String firstName, String lastName,
                              String email, String password, String department) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("Ism bo'sh bo'lishi mumkin emas!");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Email noto'g'ri formatda!");
        if (repo.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Bu email allaqachon ro'yxatdan o'tgan!");

        Teacher t = new Teacher(firstName.trim(), lastName.trim(),
                email.trim(), password, department.trim());
        repo.add(t);
        return t;
    }

    public List<Teacher> getAllTeachers() { return repo.findAll(); }
    public Optional<Teacher> getTeacherById(int id) { return repo.findById(id); }
    public Optional<Teacher> getTeacherByCode(String code) { return repo.findByTeacherCode(code); }
    public List<Teacher> searchTeachers(String name) { return repo.searchByName(name); }
    public List<Teacher> getTeachersByDepartment(String d) { return repo.findByDepartment(d); }
    public boolean updateTeacher(Teacher t) { return repo.update(t); }
    public boolean deleteTeacher(int id) { return repo.delete(id); }
    public int getTotalTeachers() { return repo.count(); }

    public void assignSubjectToTeacher(Teacher t, Subject sub) {
        t.assignSubject(sub);
        sub.setTeacher(t);
    }

    public void printAllTeachers() {
        List<Teacher> list = getAllTeachers();
        if (list.isEmpty()) { System.out.println("Hech qanday o'qituvchi yo'q."); return; }
        System.out.println("\n========= BARCHA O'QITUVCHILAR (" + list.size() + " ta) =========");
        list.forEach(t -> System.out.println("  " + t));
        System.out.println("=".repeat(50));
    }
}