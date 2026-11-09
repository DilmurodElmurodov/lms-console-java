package lms.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void add(T item);
    Optional<T> findById(int id);
    List<T> findAll();
    boolean update(T item);
    boolean delete(int id);
    int count();
}