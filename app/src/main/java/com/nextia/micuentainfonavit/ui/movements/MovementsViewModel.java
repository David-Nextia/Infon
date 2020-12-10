package com.nextia.micuentainfonavit.ui.movements;
/**
 * class to contain mensualidades, datos de mi credito,  opciones de pago and mensaulidades views
 */

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosBody;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.ui.movements.logic_views.MessageConfig;
import com.nextia.micuentainfonavit.ui.movements.logic_views.ViewsConfig;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class MovementsViewModel extends ViewModel implements OnFinishRequestListener<SaldoMovimientosResponse> {
    SaldosUseCase saldos = new SaldosUseCase();
    private MutableLiveData<SaldoMovimientosResponse> _movements = new MutableLiveData<>();
    private MutableLiveData<ViewsConfig> _config = new MutableLiveData<>();
    private MutableLiveData<Boolean> _initiated = new MutableLiveData<>();
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
    public LiveData<ViewsConfig> getConfig() {
        return _config;
    }
    public LiveData<Boolean>getTokenExpired(){return _availableToken;}
    public void setInit(boolean test){_initiated.setValue(test);}
    public LiveData<Boolean>getInit(){return _initiated;}
    public void setConfig(ViewsConfig config){_config.setValue(config);}


    //method to handle the fail response of the service
    @Override
    public void onFailureRequest(String message) {
        setInit(true);
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
        ViewsConfig config= new ViewsConfig(object.getReturnData().getRespuestasDoMovs().getSalidagrals().getTipoCaso(), MessageConfig.buildMessage(object.getReturnData().getRespuestasDoMovs()));
        setConfig(config);
        _movements.setValue(object);
        setInit(true);



    }

    public void cancelMovs(){

        saldos.cancelMovs();

    }

    public void reinitSaldos(){
        saldos.reinit();
    }
}
