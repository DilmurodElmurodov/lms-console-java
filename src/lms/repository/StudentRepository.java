package lms.repository;

import lms.model.Student;
import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository implements Repository<Student> {
    private final List<Student> students = new ArrayList<>();

    @Override public void add(Student s) { students.add(s); }

    @Override
    public Optional<Student> findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override public List<Student> findAll() { return new ArrayList<>(students); }

    @Override
    public boolean update(Student updated) {
        return findById(updated.getId()).map(s -> {
            s.setFirstName(updated.getFirstName());
            s.setLastName(updated.getLastName());
            s.setEmail(updated.getEmail());
            s.setGrade(updated.getGrade());
            return true;
        }).orElse(false);
    }

    @Override public boolean delete(int id) { return students.removeIf(s -> s.getId() == id); }
    @Override public int count() { return students.size(); }

    public Optional<Student> findByEmail(String email) {
        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email)).findFirst();
    }
    public Optional<Student> findByStudentCode(String code) {
        return students.stream()
                .filter(s -> s.getStudentCode().equalsIgnoreCase(code)).findFirst();
    }
    public List<Student> findByGrade(int grade) {
        return students.stream().filter(s -> s.getGrade() == grade).collect(Collectors.toList());
    }
    public List<Student> searchByName(String name) {
        return students.stream()
                .filter(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}