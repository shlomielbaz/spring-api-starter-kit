package com.shlomielbaz.interfaces;


import java.util.List;

public interface ICrusService<T> {
    T create(T entity);

    T getById(Long id);

    List<T> getAll();

    T update(T entity);

    void delete(Long id);
}