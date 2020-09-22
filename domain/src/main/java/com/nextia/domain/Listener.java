package com.nextia.domain;

public interface Listener<T> {
    void onFailure(String message);
    T getObject(T object);

}
