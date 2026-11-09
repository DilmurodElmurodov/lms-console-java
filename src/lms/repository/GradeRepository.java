package lms.repository;

import lms.model.*;
import java.util.*;
import java.util.stream.Collectors;

public class GradeRepository implements Repository<Grade> {
    private final List<Grade> grades = new ArrayList<>();

    @Override public void add(Grade g) { grades.add(g); }

    @Override
    public Optional<Grade> findById(int id) {
        return grades.stream().filter(g -> g.getId() == id).findFirst();
    }

    @Override public List<Grade> findAll() { return new ArrayList<>(grades); }

    @Override
    public boolean update(Grade updated) {
        return findById(updated.getId()).map(g -> {
            g.setMidtermScore(updated.getMidtermScore());
            g.setFinalScore(updated.getFinalScore());
            g.setAssignmentScore(updated.getAssignmentScore());
            return true;
        }).orElse(false);
    }

    @Override public boolean delete(int id) { return grades.removeIf(g -> g.getId() == id); }
    @Override public int count() { return grades.size(); }

    public List<Grade> findByStudent(Student s) {
        return grades.stream()
                .filter(g -> g.getStudent().getId() == s.getId())
                .collect(Collectors.toList());
    }
    public List<Grade> findBySubject(Subject sub) {
        return grades.stream()
                .filter(g -> g.getSubject().getId() == sub.getId())
                .collect(Collectors.toList());
    }
    public Optional<Grade> findByStudentAndSubject(Student s, Subject sub) {
        return grades.stream()
                .filter(g -> g.getStudent().getId() == s.getId()
                        && g.getSubject().getId() == sub.getId())
                .findFirst();
    }
    public double getAverageScore(Subject sub) {
        List<Grade> sg = findBySubject(sub);
        return sg.isEmpty() ? 0.0
                : sg.stream().mapToDouble(Grade::getOverallScore).average().orElse(0.0);
    }
}