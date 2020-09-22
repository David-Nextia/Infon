package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

import javax.xml.parsers.SAXParser;

public class SaldoViewModel extends ViewModel {
    SaldosUseCase saldos=new SaldosUseCase();
    private MutableLiveData<String> mText;

    public SaldoViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("fragmento cuanto ahorro tengo");
    }


    public LiveData<String> getText() {
        return mText;
    }
}