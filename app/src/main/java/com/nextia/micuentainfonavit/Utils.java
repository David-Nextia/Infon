package com.nextia.micuentainfonavit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoBody;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.usecases.SaldosUseCase;

import java.io.File;
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
    public static void showLoadingSkeleton(View rootView, int viewId){
        mSkeleton = Skeleton.bind(rootView)
                .load(viewId)
                .color(R.color.shimmer_color)
                .angle(0)
                .show();
    }

    public static void hideLoadingSkeleton(){
        if(mSkeleton != null)
            mSkeleton.hide();
            mSkeleton = null;
    }
    public static int getScreenHeight(Context context){
        int height;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        }
        height = displayMetrics.heightPixels;
        return height;
    }
    public static void showShareIntent(Activity activity){
        String image_url = "http://images.cartradeexchange.com//img//800//vehicle//Honda_Brio_562672_5995_6_1438153637072.jpg";
        String image_url2 = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

        Intent shareIntent = new Intent();
        shareIntent.setType("pdf/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setAction(Intent.ACTION_SEND);
        //without the below line intent will show error
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, image_url2);
        // Target whatsapp:
        //shareIntent.setPackage("com.whatsapp");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        activity.startActivity(Intent.createChooser(shareIntent,"choose one"));

    }

}