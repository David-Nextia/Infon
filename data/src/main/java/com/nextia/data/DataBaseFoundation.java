package com.nextia.data;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserBody;
import com.nextia.domain.models.user.UserResponse;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseFoundation<T> {
    public static final String AUTH = "Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());

    public void getData(Call<T> fun, final OnFinishRequestListener<T> listener){

       fun.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
              listener.onSuccesRequest(response.body());
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onFailureRequest(t.getMessage());

            }
        });

    }

}
