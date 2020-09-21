package com.nextia.data;

import com.nextia.domain.Credito;
import com.nextia.domain.Seguridad;
import com.nextia.domain.StatusServicio;
import com.nextia.domain.User;
import com.nextia.domain.UserLogin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static jdk.nashorn.internal.objects.Global.print;

public class Database {
    public User DoLogin(String userstr,String password, final OnLoginFinished onLoginFinished){
        UserLogin user = new UserLogin(userstr,password);
        final User responseUser= new User();
        //responseUser= new User("","",false,new ArrayList<Credito>(),"","","",false,2,"","", new Seguridad(""),new StatusServicio("",""),"");
        Call<User> doLoginJS =RetrofitService.getApiService().doLoginJson(user);
        doLoginJS.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getEmailPersonal()!=""){
               onLoginFinished.OnSuccess(response.body());}
                else{
                   // onLoginFinished.OnError(response.body().getStatusServicio().getMensaje());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLoginFinished.OnError("jeje");
            }
        });



    return responseUser;




    }
}
