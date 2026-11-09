package lms.service;

import lms.model.*;
import lms.repository.SubjectRepository;
import java.util.*;

public class SubjectService {
    private final SubjectRepository repo;

    public SubjectService(SubjectRepository repo) { this.repo = repo; }

    public Subject addSubject(String name, String description, int creditHours) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Fan nomi bo'sh bo'lishi mumkin emas!");
        if (creditHours < 1 || creditHours > 10)
            throw new IllegalArgumentException("Kredit soatlar 1-10 oralig'ida bo'lishi kerak!");
        if (repo.findByName(name).isPresent())
            throw new IllegalArgumentException("Bu nomli fan allaqachon mavjud!");

        Subject s = new Subject(name.trim(), description.trim(), creditHours);
        repo.add(s);
        return s;
    }

    public List<Subject> getAllSubjects() { return repo.findAll(); }
    public Optional<Subject> getSubjectById(int id) { return repo.findById(id); }
    public Optional<Subject> getSubjectByCode(String code) { return repo.findByCode(code); }
    public List<Subject> searchSubjects(String kw) { return repo.searchByName(kw); }
    public boolean updateSubject(Subject s) { return repo.update(s); }
    public boolean deleteSubject(int id) { return repo.delete(id); }
    public int getTotalSubjects() { return repo.count(); }

    public void assignTeacher(Subject sub, Teacher t) {
        sub.setTeacher(t);
        t.assignSubject(sub);
    }

    public void printAllSubjects() {
        List<Subject> list = getAllSubjects();
        if (list.isEmpty()) { System.out.println("Hech qanday fan yo'q."); return; }
        System.out.println("\n========= BARCHA FANLAR (" + list.size() + " ta) =========");
        list.forEach(s -> System.out.println("  " + s));
        System.out.println("=".repeat(50));
    }
}