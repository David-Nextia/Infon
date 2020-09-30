package com.nextia.micuentainfonavit.ui.movements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MovementsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MovementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("fragmento saldos y movimientos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}