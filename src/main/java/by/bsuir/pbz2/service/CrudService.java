package by.bsuir.pbz2.service;

import java.util.List;

public interface CrudService<K, T> {
    T create(T dto);

    T getById(K id);

    List<T> getAll();

    T update(T dto);

    void delete(K id);
}
