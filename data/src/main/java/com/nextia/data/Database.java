package com.nextia.data;

import com.nextia.domain.login.UserResponse;
import com.nextia.domain.login.UserLogin;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database {
    public static final String AUTH = "Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());
    public void DoLogin(String userstr, String password, final OnLoginFinished onLoginFinished){
        UserLogin user = new UserLogin(userstr,password);
        final UserResponse responseUserResponse = new UserResponse();
        //responseUser= new User("","",false,new ArrayList<Credito>(),"","","",false,2,"","", new Seguridad(""),new StatusServicio("",""),"");
        Call<UserResponse> doLoginJS =RetrofitService.getApiService().doLoginJson(user,AUTH);
        doLoginJS.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getEmailPersonal()!="" || response.body().getEmailPersonal().isEmpty()==false){
               onLoginFinished.OnSuccess(response.body());}
                else{
                   // onLoginFinished.OnError(response.body().getStatusServicio().getMensaje());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                onLoginFinished.OnError("jeje");
            }
        });







    }
}
