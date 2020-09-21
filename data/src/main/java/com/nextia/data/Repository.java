package com.nextia.data;

import com.google.gson.JsonObject;
import com.nextia.domain.User;
import com.nextia.domain.UserLogin;
//import com.nextia.micuentainfonavit.domain.user.User;
//import com.nextia.micuentainfonavit.domain.user.UserLogin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Repository {
    @Headers({"Authorization: Basic c2VydmljaW9zd2ViOnNhcHBpMjAxOA=="})
    @POST("/RESTAdapter/SndUsuarioAutenticar")
    Call<User> doLoginJson(@Body UserLogin user);
}
