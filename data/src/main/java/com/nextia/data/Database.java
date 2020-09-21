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
    public User DoLogin(UserLogin user){

        User responseUser;
        responseUser= new User("","",false,new ArrayList<Credito>(),"","","",false,2,"","", new Seguridad(""),new StatusServicio("",""),"");
        Call<User> doLoginJS =RetrofitService.getApiService().doLoginJson(user);
        doLoginJS.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int I;
                //print(response.body().getEmailPersonal());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                int I;
//                print("hey");
            }
        });



    return responseUser;




    }
}
