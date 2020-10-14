package com.nextia.micuentainfonavit.usecases;

import com.nextia.data.Database;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.AvisosPDFBody;
import com.nextia.domain.models.credit_info.CreditInfoBody;
import com.nextia.domain.models.credit_year_info.CreditYearInfoBody;

public class NoticeSuspensionCase {
    Database database = new Database();
    //To get the notice of suspension, retention from database
    public void getConsultPDFNotice(String credit, String token, OnFinishRequestListener listener){
        AvisosPDFBody avisosPDFBody= new AvisosPDFBody();
        avisosPDFBody.setNumeroCredito(credit);
        database.getConsultPDFNotice(avisosPDFBody,token,listener);
    }

}
