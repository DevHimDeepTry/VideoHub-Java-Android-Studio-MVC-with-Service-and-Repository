package com.example.videohub.utils;

public interface Callback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}
