package com.nextia.micuentainfonavit.ui.constancia.pdf_download;
/**
 * class to manage live data of the pdf fragment on constancia de interes and share it between fragments
 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.saldo.SaldoResponse;

public class PdfConstanciaDownloadViewModel extends ViewModel {
    private MutableLiveData<String> _credit= new MutableLiveData<>();
    private MutableLiveData<String> _year= new MutableLiveData<>();
    private MutableLiveData<CreditYearInfoResponse> _creditInfo= new MutableLiveData<>();

    //Get functions of the live data
    public LiveData<String> getCredit(){return _credit;}
    public LiveData<String> getYear(){return _year;}
    public LiveData<CreditYearInfoResponse> getCreditInfo(){return _creditInfo;}

    //Set functions of the live data
    public void setCredit(String credit){_credit.setValue(credit);}
    public void setYear(String year){_year.setValue(year);}
    public void setCreditInfo(CreditYearInfoResponse creditInfo){_creditInfo.setValue(creditInfo);}




}