package com.connectify.model.dao;

interface DAO<T, K> {
    boolean insert(T entity);
    T get(K key);
    boolean update(T entity);
    boolean delete(K key);
}
