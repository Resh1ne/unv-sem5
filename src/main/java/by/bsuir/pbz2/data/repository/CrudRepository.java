package by.bsuir.pbz2.data.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<K, T> {
    Optional<T> findById(K id);

    List<T> findAll();

    T save(T entity);

    boolean delete(K id);
}
