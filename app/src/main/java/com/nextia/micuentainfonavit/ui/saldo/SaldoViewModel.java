package com.nextia.micuentainfonavit.ui.saldo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SaldoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SaldoViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("fragmento cuanto ahorro tengo");
    }
    public void getUserSharePrefs(){

    }

    public LiveData<String> getText() {
        return mText;
    }
}