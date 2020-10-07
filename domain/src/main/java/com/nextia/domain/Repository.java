package com.nextia.domain;

import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_info.CreditInfoResponse;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.domain.models.user.UserBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Repository {
    String LOGIN_BASE = "/RESTAdapter/SndUsuarioAutenticar";
    String GET_SALDO_BASE ="/RESTAdapter/SndSaldoConsultar";
    String GET_CREDIT_INFO_BASE ="/RESTAdapter/SndConstanciaInteresSolicitar";
    String GET_CREDIT_INFO_YEAR_BASE ="/RESTAdapter/SndConstanciaInteresSolicitarAnio";
    String GET_CREDIT_INFO_HISTORIC_BASE ="/RESTAdapter/SndEdoCuentaHistoricoConsultar";

    @POST(LOGIN_BASE)
    Call<UserResponse> logInMethod(@Body UserBody user, @Header("Authorization") String auth);
    @POST(GET_SALDO_BASE)
    Call<SaldoResponse>  getSaldo(@Body SaldoBody saldo, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_BASE)
    Call<CreditInfoResponse>  getCreditInfo(@Body CreditInfoBody credito, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_YEAR_BASE)
    Call<CreditYearInfoResponse>  getCreditInfoYear(@Body CreditYearInfoBody credito, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_HISTORIC_BASE)
    Call<HistoricResponse>  getCreditInfoHistoric(@Body CreditInfoBody credito, @Header("Authorization") String auth);
}
