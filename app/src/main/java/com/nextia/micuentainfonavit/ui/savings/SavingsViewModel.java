package com.nextia.micuentainfonavit.ui.savings;
/**
 * view model of view savings that handles the information
 */

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class SavingsViewModel extends ViewModel implements OnFinishRequestListener<SaldoResponse> {
    SaldosUseCase saldos=new SaldosUseCase();
    private MutableLiveData<SaldoResponse> _saldo= new MutableLiveData<>();

    //method to call the DB service and get data
    public void getSaldo(Context context){
        UserResponse user= Utils.getSharedPreferencesUserData(context);
        SaldoBody saldo= new SaldoBody(user.getNss(),user.getRfc())  ;
        saldos.getSaldos(saldo,this);
    }

    //method to obtain data of the viewmodel
    public LiveData<SaldoResponse> getSaldos() {
        return _saldo;
    }

    //method to handle the fail response of the service
    @Override
    public void onFailureRequest(String message) {
        _saldo.setValue(null);
    }

    //method to handle the success response of the service
    @Override
    public void onSuccesRequest(SaldoResponse object) {
        _saldo.setValue(object);
    }
}