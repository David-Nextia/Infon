package com.nextia.domain;
/**
 * interface of the repository methods to get data from server
 */
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
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosBody;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
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
    String GET_CONSULT_PDF_NOTICE = "/RESTAdapter/SndAvisosPDFConsultar";
    String GET_SALDO_MOVEMENTS_BASE ="/RESTAdapter/SndConsultaSdoMovsAppMovil";
    String GET_PERIODOS_DISPONIBLES="/RESTAdapter/SndPeriodosDisponiblesAppMovil";
    String GET_REPORTE_MENSUAL="/RESTAdapter/SndEdoCuentaMensualConsultar";
    String GET_REPORTE_MOVS="/RESTAdapter/SndReporteSaldosMovsAppmovil";

    @POST(LOGIN_BASE)
    Call<UserResponse> logInMethod(@Body UserBody user);
    @POST(GET_SALDO_BASE)
    Call<SaldoResponse>  getSaldo(@Body SaldoBody saldo, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_BASE)
    Call<CreditInfoResponse>  getCreditInfo(@Body CreditInfoBody credito, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_YEAR_BASE)
    Call<CreditYearInfoResponse>  getCreditInfoYear(@Body CreditYearInfoBody credito, @Header("Authorization") String auth);
    @POST(GET_CREDIT_INFO_HISTORIC_BASE)
    Call<HistoricResponse>  getCreditInfoHistoric(@Body CreditInfoBody credito, @Header("Authorization") String auth);
    @POST(GET_CONSULT_PDF_NOTICE)
    Call<AvisosPDFResponse>  getConsultPDFNotice(@Body AvisosPDFBody avisosPDF, @Header("Authorization") String auth);
    @POST(GET_SALDO_MOVEMENTS_BASE)
    Call<SaldoMovimientosResponse>  getSaldoMovimientos(@Body SaldoMovimientosBody saldo, @Header("Authorization") String auth);
    @POST(GET_PERIODOS_DISPONIBLES)
    Call<PeriodResponse>  getperiodosDisponibles(@Body creditBody saldo, @Header("Authorization") String auth);
    @POST(GET_REPORTE_MENSUAL)
    Call<MensualReportResponse>  getReporteMensual(@Body MensualReportBody saldo, @Header("Authorization") String auth);
    @POST(GET_REPORTE_MOVS)
    Call<ReportMovsResponse>  getReporteMovs(@Body ReportMovsBody saldo, @Header("Authorization") String auth);
}
