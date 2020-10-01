package com.nextia.data;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_info.CreditInfoResponse;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.domain.models.user.UserBody;
import com.nextia.domain.models.welcome.WelcomeCard;

import java.util.ArrayList;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;

public class Database {
    public static final String AUTH="Basic c2VydmljaW9zd2ViOnNhcHBpMjAxOA==";
          //  = "Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());



    public void doLogin(UserBody user, final OnFinishRequestListener<UserResponse> listener){
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



    public void getSaldos(SaldoBody body, final OnFinishRequestListener<SaldoResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<SaldoBody>();
        Call<SaldoResponse> getSaldo =RetrofitService.getApiService().getSaldo(body,AUTH);
        database.getData(getSaldo,listener);
    }
    public void getCredifInfo(CreditInfoBody body, final OnFinishRequestListener<CreditInfoResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<CreditInfoBody>();
        Call<CreditInfoResponse> getInfoCredit =RetrofitService.getApiService().getCreditInfo(body,AUTH);
        database.getData(getInfoCredit,listener);
    }
    public void getCredifInfoYear(CreditYearInfoBody body, final OnFinishRequestListener<CreditYearInfoResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<CreditYearInfoBody>();
        Call<CreditYearInfoResponse> getInfoCreditYear =RetrofitService.getApiService().getCreditInfoYear(body, AUTH);
        database.getData(getInfoCreditYear,listener);
    }


}
