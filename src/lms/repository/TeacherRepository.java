package lms.repository;

import lms.model.Teacher;
import java.util.*;
import java.util.stream.Collectors;

public class TeacherRepository implements Repository<Teacher> {
    private final List<Teacher> teachers = new ArrayList<>();

    @Override public void add(Teacher t) { teachers.add(t); }

    @Override
    public Optional<Teacher> findById(int id) {
        return teachers.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override public List<Teacher> findAll() { return new ArrayList<>(teachers); }

    @Override
    public boolean update(Teacher updated) {
        return findById(updated.getId()).map(t -> {
            t.setFirstName(updated.getFirstName());
            t.setLastName(updated.getLastName());
            t.setEmail(updated.getEmail());
            t.setDepartment(updated.getDepartment());
            return true;
        }).orElse(false);
    }

    @Override public boolean delete(int id) { return teachers.removeIf(t -> t.getId() == id); }
    @Override public int count() { return teachers.size(); }

    public Optional<Teacher> findByEmail(String email) {
        return teachers.stream()
                .filter(t -> t.getEmail().equalsIgnoreCase(email)).findFirst();
    }
    public Optional<Teacher> findByTeacherCode(String code) {
        return teachers.stream()
                .filter(t -> t.getTeacherCode().equalsIgnoreCase(code)).findFirst();
    }
    public List<Teacher> findByDepartment(String dept) {
        return teachers.stream()
                .filter(t -> t.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }
    public List<Teacher> searchByName(String name) {
        return teachers.stream()
                .filter(t -> t.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}