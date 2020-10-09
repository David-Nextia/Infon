package com.nextia.micuentainfonavit.ui.constancia.pdf_download;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.domain.models.saldo.SaldoResponse;

public class PdfConstanciaDownloadViewModel extends ViewModel {
    private MutableLiveData<String> _credit= new MutableLiveData<>();
    private MutableLiveData<String> _year= new MutableLiveData<>();
    private MutableLiveData<CreditYearInfoResponse> _creditInfo= new MutableLiveData<>();
    public LiveData<String> getCredit(){return _credit;}
    public LiveData<String> getYear(){return _year;}
    public LiveData<CreditYearInfoResponse> getCreditInfo(){return _creditInfo;}
    public void setCredit(String credit){_credit.setValue(credit);}
    public void setYear(String year){_year.setValue(year);}
    public void setCreditInfo(CreditYearInfoResponse creditInfo){_creditInfo.setValue(creditInfo);}




}