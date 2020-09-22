package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.R;

public class SaldoFragment extends Fragment implements OnFinishRequestListener<SaldoResponse> {

    private SaldoViewModel saldoViewModel;
    public TextView ahorro;
    ProgressBar progres;
    LinearLayout linear;
    TabLayout tabLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        saldoViewModel =new ViewModelProvider(this).get(SaldoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saldos, container, false);
        Utils utils= new Utils();
        ahorro=root.findViewById(R.id.txtAhorroTotal);
        progres= root.findViewById(R.id.progressBarSaldos);
        linear=root.findViewById(R.id.linearopaque);
        progres.setAlpha(1.0f);

        tabLayout=(TabLayout)root.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        linear.setAlpha(0.3f);
        tabLayout.setClickable(false);
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
       /* SharedPreferences  mPrefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        UserResponse obj = gson.fromJson(json, UserResponse.class);*/
       utils.getSaldo(this, this.getActivity());


        return root;
    }

    @Override
    public void onFailureRequest(String message) {

    }

    @Override
    public void onSuccesRequest(SaldoResponse object) {
        this.getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progres.setAlpha(0.0f);
        linear.setAlpha(1.0f);
        tabLayout.setClickable(true);

        ahorro.setText("$"+object.getSaldoSARTotal().toString());


    }
}