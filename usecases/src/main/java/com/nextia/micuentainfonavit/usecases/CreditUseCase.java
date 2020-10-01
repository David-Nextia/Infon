package com.nextia.micuentainfonavit.usecases;

import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;

public class CreditUseCase {
    Database database = new Database();
    public void getInfoCredit(String credito, OnFinishRequestListener listener){
        CreditInfoBody credit= new CreditInfoBody();
        credit.setNumeroCredito(credito);
        database.getCredifInfo(credit,listener);
    }

    public void getInfoCreditYear(String credito, String anio,OnFinishRequestListener listener){
        CreditYearInfoBody body= new CreditYearInfoBody();
        body.setAnio(anio);
        body.setNumeroCredito(credito);
        database.getCredifInfoYear(body,listener);

    }
}
