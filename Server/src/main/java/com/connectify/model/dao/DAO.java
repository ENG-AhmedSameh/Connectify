package com.connectify.model.dao;

import java.util.List;

public interface DAO<T>{
    boolean insert(T entity);
    <V> T get(V key);
    boolean update(T key);
    <V> boolean delete(V key);
}
