package com.example.videohub.repositories.interfaces;

import com.example.videohub.utils.Callback;
import java.util.List;

public interface IBaseRepository<T> {
    void insert(String key, T item, Callback<Boolean> callback);
    void update(String key, T item);
    void delete(String key);
    void getAll(Callback<List<T>> callback);
    void getById(String key, Callback<T> callback);
}
