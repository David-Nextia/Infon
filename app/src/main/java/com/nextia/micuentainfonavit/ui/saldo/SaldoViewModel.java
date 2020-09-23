package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class SaldoViewModel extends ViewModel implements OnFinishRequestListener<SaldoResponse> {
    SaldosUseCase saldos=new SaldosUseCase();
    private MutableLiveData<SaldoResponse> _saldo;
    public SaldoViewModel() {
        _saldo = new MutableLiveData<>();
    }
    public void getSaldo(Context context){
        UserResponse user= Utils.getSharedPreferencesUserData(context);
        SaldoBody saldo= new SaldoBody(user.getNss(),user.getRfc())  ;
        saldos.getSaldos(saldo,this);
    }
    public LiveData<SaldoResponse> getSaldos() {
        return _saldo;
    }



    @Override
    public void onFailureRequest(String message) {

    }

    @Override
    public void onSuccesRequest(SaldoResponse object) {
        _saldo.setValue(object);

    }
}