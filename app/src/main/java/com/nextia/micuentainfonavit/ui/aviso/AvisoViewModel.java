package com.nextia.micuentainfonavit.ui.aviso;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.AvisosPDFResponse;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.usecases.NoticeSuspensionCase;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class AvisoViewModel extends ViewModel implements OnFinishRequestListener<AvisosPDFResponse> {
    NoticeSuspensionCase noticeSuspensionCase = new NoticeSuspensionCase();
    private MutableLiveData<AvisosPDFResponse> _aviso= new MutableLiveData<>();
    private MutableLiveData<Boolean> _availableToken= new MutableLiveData<>();


    //method to call the DB service and get data
    public void getAvisoDB(Context context,String credit,String token){
        UserResponse user= Utils.getSharedPreferencesUserData(context);
        SaldoBody saldo= new SaldoBody(user.getNss(),user.getRfc())  ;
        noticeSuspensionCase.getConsultPDFNotice(credit,token,this);
    }
    public LiveData<Boolean> getTokenExpired(){return _availableToken;}

    //method to obtain data of the viewmodel
    public LiveData<AvisosPDFResponse> getAviso() {
        return _aviso;
    }


    @Override
    public void onFailureRequest(String message) {
        _aviso.setValue(null);
    }

    @Override
    public void onTokenExpired() {
        _availableToken.setValue(false);
    }

    @Override
    public void onSuccesRequest(AvisosPDFResponse object, String token) {
        _aviso.setValue(object);
    }
}
