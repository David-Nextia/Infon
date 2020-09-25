package com.nextia.micuentainfonavit;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

import java.text.DecimalFormat;

public class Utils {
    SaldosUseCase saldos=new SaldosUseCase();
    public void getSaldo(OnFinishRequestListener listener, Context context){
        UserResponse user= getSharedPreferencesUserData(context.getApplicationContext());
        SaldoBody saldo= new SaldoBody(user.getNss(),user.getRfc())  ;
        saldos.getSaldos(saldo,listener);

    }

    public static UserResponse getSharedPreferencesUserData(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        return gson.fromJson(json, UserResponse.class);

    }

    public static String getSharedPreferencesEmail(Context context){
        SharedPreferences mPrefs = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return mPrefs.getString("emailUser", "");

    }

    public static String formatMoney(double num){
        String money;
        if (num==0.0){money="$0.00";}
        else{  DecimalFormat formatter = new DecimalFormat("#,###.00");
            money="$"+formatter.format(num);}

        return money;
    }

    public static void saveToSharedPreferences(Context context, String tag, String object){
        SharedPreferences mPrefs =context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString(tag, object);
        prefsEditor.commit();

    }

    private static ViewSkeletonScreen mSkeleton;
    public static void showLoadingSkeleton(View rootView){
        mSkeleton = Skeleton.bind(rootView)
                .load(R.layout.skeleton_savings)
                .color(R.color.shimmer_color)
                .angle(0)
                .show();
    }

    public static void hideLoadingSkeleton(){
        if(mSkeleton != null)
            mSkeleton.hide();
            mSkeleton = null;
    }
}
