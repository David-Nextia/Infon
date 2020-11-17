package com.nextia.micuentainfonavit.usecases;
/**
 * class to interact between data module and app module about Credit class
 */
import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;

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



}
