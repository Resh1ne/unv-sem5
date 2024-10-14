package by.bsuir.pbz2.data;

import java.util.List;

public interface CrudDao<K, T> {
    T create(T entity);

    T findById(K id);

    List<T> findAll();

    T update(T entity);

    boolean delete(K id);
}
