package lms.repository;

import lms.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AttendanceRepository implements Repository<Attendance> {
    private final List<Attendance> list = new ArrayList<>();

    @Override public void add(Attendance a) { list.add(a); }

    @Override
    public Optional<Attendance> findById(int id) {
        return list.stream().filter(a -> a.getId() == id).findFirst();
    }

    @Override public List<Attendance> findAll() { return new ArrayList<>(list); }

    @Override
    public boolean update(Attendance updated) {
        return findById(updated.getId()).map(a -> {
            a.setStatus(updated.getStatus());
            a.setRemarks(updated.getRemarks());
            return true;
        }).orElse(false);
    }

    @Override public boolean delete(int id) { return list.removeIf(a -> a.getId() == id); }
    @Override public int count() { return list.size(); }

    public List<Attendance> findByStudent(Student s) {
        return list.stream()
                .filter(a -> a.getStudent().getId() == s.getId())
                .collect(Collectors.toList());
    }
    public List<Attendance> findBySubject(Subject sub) {
        return list.stream()
                .filter(a -> a.getSubject().getId() == sub.getId())
                .collect(Collectors.toList());
    }
    public List<Attendance> findByStudentAndSubject(Student s, Subject sub) {
        return list.stream()
                .filter(a -> a.getStudent().getId() == s.getId()
                        && a.getSubject().getId() == sub.getId())
                .collect(Collectors.toList());
    }
    public List<Attendance> findByDate(LocalDate date) {
        return list.stream()
                .filter(a -> a.getDate().equals(date))
                .collect(Collectors.toList());
    }
    public List<Attendance> findBySubjectAndDate(Subject sub, LocalDate date) {
        return list.stream()
                .filter(a -> a.getSubject().getId() == sub.getId()
                        && a.getDate().equals(date))
                .collect(Collectors.toList());
    }
    public double getAttendancePercentage(Student s, Subject sub) {
        List<Attendance> records = findByStudentAndSubject(s, sub);
        if (records.isEmpty()) return 0.0;
        long present = records.stream().filter(Attendance::isPresent).count();
        return (double) present / records.size() * 100;
    }
}