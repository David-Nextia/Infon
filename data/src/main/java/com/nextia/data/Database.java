package com.nextia.data;
/**
 * class that contains methods to access the repository calls
 */

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.AvisosPDFBody;
import com.nextia.domain.models.aviso_suspension.AvisosPDFResponse;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_info.CreditInfoResponse;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.mensual_report.MensualReportBody;
import com.nextia.domain.models.mensual_report.MensualReportResponse;
import com.nextia.domain.models.mensual_report.PeriodResponse;
import com.nextia.domain.models.mensual_report.creditBody;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.reports.ReportMovsBody;
import com.nextia.domain.models.reports.ReportMovsResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.saldo_movimientos.MovsBody;
import com.nextia.domain.models.saldo_movimientos.MovsResponse;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosBody;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.domain.models.user.UserBody;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Database {
    public static final String AUTH="Basic c2VydmljaW9zd2ViOnNhcHBpMjAxOA==";
//  = "Basic "+ Base64.getEncoder().encodeToString("serviciosweb:sappi2018".getBytes());

    //To login the user and get the UserResponse
    public void doLogin(UserBody user, final OnFinishRequestListener<UserResponse> listener){
        Call<UserResponse> doLoginJS =RetrofitService.getApiLoginService().logInMethod(user);
        doLoginJS.enqueue(new Callback<UserResponse>() {
            @Override
            //On wrong credentials login returns credit object instead credit list, and that triggers OnFailure
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code()>400){
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        if(jObjError.getJSONObject("StatusServicio").getString("codigo").equals("LOGINMCI20025")){
                            listener.onFailureRequest("Con este usuario no podemos proporcionarte acceso, para más información comunícate a Infonatel al 55 9172 5050 en la Ciudad de México al 800 008 3900 desde cualquier parte del país.");
                        }
                        else
                        { listener.onFailureRequest(jObjError.getJSONObject("StatusServicio").getString("mensaje"));}

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    String t=response.body().getStatusServicio().getCodigo();
                    if(t.contains("LOGINMCI20010") || t.contains("LOGINMCI20001")){
                        listener.onSuccesRequest(response.body(),response.headers().get("Authorization"));}
                    else if(t.contains("LOGINMCI20025")){
                        listener.onFailureRequest("Con este usuario no podemos proporcionarte acceso, para más información comunícate a Infonatel al 55 9172 5050 en la Ciudad de México al 800 008 3900 desde cualquier parte del país.");

                    }
                    else { listener.onFailureRequest(response.body().getStatusServicio().getMensaje());
                    }
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                listener.onFailureRequest(t.getMessage());

            }
        });

    }

    //To  get the SaldosResponse class from DB
    public void getSaldos(SaldoBody body, String token, final OnFinishRequestListener<SaldoResponse> listener){

        DataBaseFoundation database= new DataBaseFoundation<SaldoBody>();
        Call<SaldoResponse> getSaldo =RetrofitService.getApiService().getSaldo(body,token);
        database.getData(getSaldo,listener);
    }

    //To get the credit info of each credit of the user
    public void getCredifInfo(CreditInfoBody body, String token, final OnFinishRequestListener<CreditInfoResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<CreditInfoBody>();
        Call<CreditInfoResponse> getInfoCredit =RetrofitService.getApiService().getCreditInfo(body,token);
        database.getData(getInfoCredit,listener);
    }

    //To get the info on credit per year
    public void getCredifInfoYear(CreditYearInfoBody body, String token, final OnFinishRequestListener<CreditYearInfoResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<CreditYearInfoBody>();
        Call<CreditYearInfoResponse> getInfoCreditYear =RetrofitService.getApiService().getCreditInfoYear(body, token);
        database.getData(getInfoCreditYear,listener);
    }

    //To get the urlBase64 of the credit
    public void getCredifInfoHistoric(CreditInfoBody body, String token, final OnFinishRequestListener<HistoricResponse> listener){
        DataBaseFoundation database= new DataBaseFoundation<CreditYearInfoBody>();
        Call<HistoricResponse> getInfoCreditHistoric =RetrofitService.getApiService().getCreditInfoHistoric(body, token);
        database.getData(getInfoCreditHistoric,listener);
    }

    //To get the urlBase64 of the credit
    public void getConsultPDFNotice(AvisosPDFBody avisosPDFBody, String token, final OnFinishRequestListener<HistoricResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<String>();
        Call<AvisosPDFResponse> getPDFConsultNotice = RetrofitService.getApiService().getConsultPDFNotice(avisosPDFBody, token);
        database.getData(getPDFConsultNotice, listener);
    }

    //To get balances and movements
    public void getSaldosMovimientos(SaldoMovimientosBody body, String token, final OnFinishRequestListener<SaldoResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<SaldoBody>();
        Call<SaldoMovimientosResponse> getSaldoMovimientos = RetrofitService.getApiService().getSaldoMovimientos(body, token);
        database.getData(getSaldoMovimientos, listener);
    }


    //To get periods
    public void getPeriodosDisponibles(creditBody body, String token, final OnFinishRequestListener<PeriodResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<creditBody>();
        Call<PeriodResponse> getperiodosDisponibles = RetrofitService.getApiService().getperiodosDisponibles(body,token);
        database.getData(getperiodosDisponibles, listener);
    }

    //To get mensual report
    public void getMensualReport(MensualReportBody body, String token, final OnFinishRequestListener<MensualReportResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<MensualReportBody>();
        Call<MensualReportResponse> getMensualReport = RetrofitService.getApiService().getReporteMensual(body,token);
        database.getData(getMensualReport, listener);
    }

    //To get period movs report
    public void getReportMovs(ReportMovsBody body, String token, final OnFinishRequestListener<ReportMovsResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<ReportMovsBody>();
        Call<ReportMovsResponse> getReportMovs = RetrofitService.getApiService().getReporteMovs(body,token);
        database.getData(getReportMovs, listener);
    }

    //To get period movs data
    public void getDataMovs(MovsBody body, String token, final OnFinishRequestListener<MovsResponse> listener) {
        DataBaseFoundation database = new DataBaseFoundation<MovsBody>();
        Call<MovsResponse> getReportMovs = RetrofitService.getApiService().getMovsData(body,token);
        database.getData(getReportMovs, listener);
    }

}
