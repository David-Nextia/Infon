package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.user.UserResponse;

public class SaldoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SaldoViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("fragmento cuanto ahorro tengo");
    }
    public void getUserSharePrefs(OnFinishRequestListener listener){
        Context context= (Context)listener;

    }

    public UserResponse getSharedPreferences(Context context){
        SharedPreferences  mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        UserResponse obj = gson.fromJson(json, UserResponse.class);
        return obj;

    }

    public LiveData<String> getText() {
        return mText;
    }
}