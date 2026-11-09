package lms.repository;

import lms.model.Subject;
import java.util.*;
import java.util.stream.Collectors;

public class SubjectRepository implements Repository<Subject> {
    private final List<Subject> subjects = new ArrayList<>();

    @Override public void add(Subject s) { subjects.add(s); }

    @Override
    public Optional<Subject> findById(int id) {
        return subjects.stream().filter(s -> s.getId() == id).findFirst();
    }

    @Override public List<Subject> findAll() { return new ArrayList<>(subjects); }

    @Override
    public boolean update(Subject updated) {
        return findById(updated.getId()).map(s -> {
            s.setName(updated.getName());
            s.setDescription(updated.getDescription());
            s.setCreditHours(updated.getCreditHours());
            return true;
        }).orElse(false);
    }

    @Override public boolean delete(int id) { return subjects.removeIf(s -> s.getId() == id); }
    @Override public int count() { return subjects.size(); }

    public Optional<Subject> findByCode(String code) {
        return subjects.stream()
                .filter(s -> s.getSubjectCode().equalsIgnoreCase(code)).findFirst();
    }
    public Optional<Subject> findByName(String name) {
        return subjects.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }
    public List<Subject> searchByName(String kw) {
        return subjects.stream()
                .filter(s -> s.getName().toLowerCase().contains(kw.toLowerCase()))
                .collect(Collectors.toList());
    }
}