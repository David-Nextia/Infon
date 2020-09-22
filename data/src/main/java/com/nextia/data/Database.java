package com.nextia.data;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.domain.models.user.UserBody;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database {
    public static final String AUTH = "Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());
    public void DoLogin(String userstr, String password, final OnFinishRequestListener<UserResponse> listener){
        UserBody user = new UserBody(userstr,password);
        Call<UserResponse> doLoginJS =RetrofitService.getApiService().logInMethod(user,AUTH);
        doLoginJS.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getEmailPersonal()!="" || response.body().getEmailPersonal().isEmpty()==false){
               listener.onSuccesRequest(response.body());}
                else{ listener.onFailureRequest(response.body().getStatusServicio().getMensaje());
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                listener.onFailureRequest(t.getMessage());
            }
        });

    }
}
