package com.nextia.domain;

public interface OnFinishRequestListener<T> {
    void onFailureRequest(String message);
    void onSuccesRequest(T object);
}
