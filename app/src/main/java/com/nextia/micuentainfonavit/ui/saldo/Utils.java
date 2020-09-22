package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

public class Utils {
    SaldosUseCase saldos=new SaldosUseCase();
    public void getSaldo(OnFinishRequestListener listener, Context context){
        UserResponse user=getSharedPreferences(context.getApplicationContext());
        SaldoBody saldo= new SaldoBody(user.getNss(),user.getRfc())  ;
        saldos.getSaldos(saldo,listener);

    }

    private UserResponse getSharedPreferences(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        UserResponse obj = gson.fromJson(json, UserResponse.class);
        return obj;

    }
}
