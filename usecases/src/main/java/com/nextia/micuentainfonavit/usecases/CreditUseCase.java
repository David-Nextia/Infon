package com.nextia.micuentainfonavit.usecases;
/**
 * class to interact between data module and app module about Credit class
 */
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;
import com.nextia.domain.models.mensual_report.MensualReportBody;
import com.nextia.domain.models.mensual_report.creditBody;
import com.nextia.domain.models.reports.ReportMovsBody;
import com.nextia.domain.models.saldo_movimientos.MovsBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreditUseCase {
    Database database = new Database();
    //To get the info of credit from database
    public void getInfoCredit(String credito, String token, OnFinishRequestListener listener){
        CreditInfoBody credit= new CreditInfoBody();
        credit.setNumeroCredito(credito);
        database.getCredifInfo(credit,token,listener);
    }
    //To get the info of credit per year from database
    public void getInfoCreditYear( String token,String credito, String anio,OnFinishRequestListener listener){
        CreditYearInfoBody body= new CreditYearInfoBody();
        body.setAnio(anio);
        body.setNumeroCredito(credito);
        database.getCredifInfoYear(body,token,listener);

    }
    //To get the Base64 credit pdf from database
    public void getInfoCreditHistoric(String token,String credito, OnFinishRequestListener listener){
        CreditInfoBody credit= new CreditInfoBody();
        credit.setNumeroCredito(credito);
        database.getCredifInfoHistoric(credit,token,listener);
    }

    public void getPeriodosDisponibles(String token,String credito, OnFinishRequestListener listener){
        creditBody credit= new creditBody();
        credit.setCredito(credito);
        database.getPeriodosDisponibles(credit,token,listener);
    }
    public void getMensualReport(String token,String credito, String periodo, OnFinishRequestListener listener){
        MensualReportBody report= new MensualReportBody();
        report.setNumeroCredito(credito);
        report.setPeriodo(periodo);
        database.getMensualReport(report,token,listener);
    }
    public void getReportMovs(String token,String credito, String nombre, OnFinishRequestListener listener){
        ReportMovsBody report= new ReportMovsBody();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        String day1="";
        String month1="";
        String month2="";
        c2.set(Calendar.MONTH,c2.get(Calendar.MONTH)-2);
        c1.set(Calendar.MONTH,c1.get(Calendar.MONTH)+1);
        if(String.valueOf(c2.get(Calendar.MONTH)).length()==1){
            month2="0"+String.valueOf(c2.get(Calendar.MONTH));
        }else{
            month2=String.valueOf(c2.get(Calendar.MONTH));
        }
        if(String.valueOf(c1.get(Calendar.MONTH)).length()==1){
            month1="0"+String.valueOf(c1.get(Calendar.MONTH));
        }
        else{
            month1=String.valueOf(c1.get(Calendar.MONTH));
        }
        if(String.valueOf(c1.get(Calendar.DAY_OF_YEAR)).length()==1){
           day1="0"+String.valueOf(c1.get(Calendar.DAY_OF_YEAR));
        }
        else{
            day1=String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }



        String period=c2.get(Calendar.YEAR)+month2+"01"+"-"+c1.get(Calendar.YEAR)+month1+day1;
        report.setCredito(credito);
        report.setDesde(period);
        report.setNombreAcreditado(nombre);
        report.setTipoCantidades("PESOS");
        database.getReportMovs(report,token,listener);
    }

    public void getMovsData(String token,String credito, OnFinishRequestListener listener){
       MovsBody report= new MovsBody();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        String day1="";
        String month1="";
        String month2="";
        c2.set(Calendar.MONTH,c2.get(Calendar.MONTH)-2);
        c1.set(Calendar.MONTH,c1.get(Calendar.MONTH)+1);
        if(String.valueOf(c2.get(Calendar.MONTH)).length()==1){
            month2="0"+String.valueOf(c2.get(Calendar.MONTH));
        }else{
            month2=String.valueOf(c2.get(Calendar.MONTH));
        }
        if(String.valueOf(c1.get(Calendar.MONTH)).length()==1){
            month1="0"+String.valueOf(c1.get(Calendar.MONTH));
        }
        else{
            month1=String.valueOf(c1.get(Calendar.MONTH));
        }
        if(String.valueOf(c1.get(Calendar.DAY_OF_YEAR)).length()==1){
            day1="0"+String.valueOf(c1.get(Calendar.DAY_OF_YEAR));
        }
        else{
            day1=String.valueOf(c1.get(Calendar.DAY_OF_MONTH));
        }



        String period1=c2.get(Calendar.YEAR)+month2+"01";
        String period2=c1.get(Calendar.YEAR)+month1+day1;
        report.setNumeroCreditoEntrada(credito);
        report.setFechaInicioEntrada(period1);
        report.setFechaFinEntrada(period2);
        report.setTipoTransaccion("1");

        database.getDataMovs(report,token,listener);
    }



}
