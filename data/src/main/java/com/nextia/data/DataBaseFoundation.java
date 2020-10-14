package com.nextia.data;
/**
 * class to make a foundation of database requests that implement the same method
 */

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.user.UserBody;
import com.nextia.domain.models.user.UserResponse;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataBaseFoundation<T> {
    public static final String AUTH = "Basic c2VydmljaW9zd2ViOnNhcHBpMjAxOA==";
            //"Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());

    //To get te data from server, and manage responses
    public void getData(Call<T> fun, final OnFinishRequestListener<T> listener){

       fun.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(response.code()==401){
                    listener.onTokenExpired();
                }
                else{
                if(response.body() instanceof HistoricResponse){
                    if(((HistoricResponse) response.body()).getStatusServicio().getCodigo().contains("00")){
                        listener.onSuccesRequest(response.body(),"");
                    }
                    else{
                        listener.onFailureRequest(((HistoricResponse) response.body()).getStatusServicio().getMensaje());
                    }
                }
                if(response.body() instanceof CreditYearInfoResponse){
                    if(((CreditYearInfoResponse) response.body()).getDatosTecnicos().getCodigoRespuesta().equals("00")){
                        listener.onSuccesRequest(response.body(),"");
                    }
                    else{
                        listener.onFailureRequest(((CreditYearInfoResponse) response.body()).getDatosTecnicos().getDescripcionRespuesta());
                    }
                }
                else{
                    listener.onSuccesRequest(response.body(),"");
                }}

            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onFailureRequest(t.getMessage());

            }
        });

    }

}
