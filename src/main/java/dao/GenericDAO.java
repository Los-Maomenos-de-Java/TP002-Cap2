package dao;

import java.util.List;

public interface GenericDAO<T> {
    List<T> findAll();

    int update(T t);
}
