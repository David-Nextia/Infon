package com.nextia.micuentainfonavit.ui.movements.views.movements;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.micuentainfonavit.MainActivity;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;
import java.io.FileNotFoundException;

public class InnerMovementsViewModel extends ViewModel implements OnFinishRequestListener<HistoricResponse> {
    Activity activity;
    private MutableLiveData<HistoricResponse> _historic= new MutableLiveData<>();
    private MutableLiveData<File> _file= new MutableLiveData<>();
    private MutableLiveData<String> _message= new MutableLiveData<>();
    CreditUseCase creditUseCase= new CreditUseCase();
    @Override
    public void onFailureRequest(String message) {
        _historic.setValue(null);
        _message.setValue(message);
    }

    @Override
    public void onSuccesRequest(HistoricResponse object, String token) {
        _historic.setValue(object);
        try {
            _file.setValue(Utils.createPdfFromBase64(object.getReporte(),"historic", activity));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadHistoric(Activity activity, String credit){
        creditUseCase.getInfoCreditHistoric(Utils.getSharedPreferencesToken(activity),credit,this);
        this.activity=activity;
    }
    public LiveData<HistoricResponse> getHistoric() {
        return _historic;
    }

}
