package com.nextia.domain;

import com.nextia.domain.login.UserResponse;
import com.nextia.domain.login.UserLogin;
//import com.nextia.micuentainfonavit.domain.user.User;
//import com.nextia.micuentainfonavit.domain.user.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Repository {
    //@Headers({"Authorization: Basic c2VydmljaW9zd2ViOnNhcHBpMjAxOA=="})
    @POST("/RESTAdapter/SndUsuarioAutenticar")
    Call<UserResponse> doLoginJson(@Body UserLogin user, @Header("Authorization") String auth);
}
