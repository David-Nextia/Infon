package com.nextia.micuentainfonavit.ui.movements;
/**
 * class to contain mensualidades, datos de mi credito,  opccines de pago and mensaulidades views
 */

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosBody;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class MovementsViewModel extends ViewModel implements OnFinishRequestListener<SaldoMovimientosResponse> {
    SaldosUseCase saldos = new SaldosUseCase();
    private MutableLiveData<SaldoMovimientosResponse> _movements = new MutableLiveData<>();
    private MutableLiveData<Boolean> _availableToken = new MutableLiveData<>();

    //method to call the DB service and get data
    public void getMovements(Context context, String credito) {
        SaldoMovimientosBody body = new SaldoMovimientosBody(credito);
        saldos.getSaldosMovimientos(body, Utils.getSharedPreferencesToken(context), this);
    }

    //method to obtain data of the viewmodel
    public LiveData<SaldoMovimientosResponse> getSaldosMovimientos() {
        return _movements;
    }

    public LiveData<Boolean>getTokenExpired(){return _availableToken;}

    //method to handle the fail response of the service
    @Override
    public void onFailureRequest(String message) {
        _movements.setValue(null);
    }

    //to manage token expired
    @Override
    public void onTokenExpired() {
        _availableToken.setValue(false);
    }

    //method to handle the success response of the service
    @Override
    public void onSuccesRequest(SaldoMovimientosResponse object, String token) {
        _movements.setValue(object);
    }
}
