package com.nextia.domain;

import com.google.gson.JsonObject;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.domain.models.user.UserBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Repository {
    String LOGIN_BASE = "/RESTAdapter/SndUsuarioAutenticar";
    String GET_SALDO_BASE ="/RESTAdapter/SndSaldoConsultar";

    @POST(LOGIN_BASE)
    Call<UserResponse> logInMethod(@Body UserBody user, @Header("Authorization") String auth);
    @POST(GET_SALDO_BASE)
    Call<ResponseBody>  getSaldo(@Body SaldoBody saldo,@Header("Authorization") String auth);
}
