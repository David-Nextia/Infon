package com.nextia.domain;


/**
 * interface to trigger the finish of the request on server
 */
public interface OnFinishRequestListener<T> {
    void onFailureRequest(String message);
    void onSuccesRequest(T object, String token);
}
