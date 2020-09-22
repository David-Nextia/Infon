package com.nextia.micuentainfonavit.ui.saldo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.saldo.SaldoResponse;
import com.nextia.domain.models.user.UserResponse;
import com.nextia.micuentainfonavit.R;

public class SaldoFragment extends Fragment implements OnFinishRequestListener<SaldoResponse> {

    private SaldoViewModel saldoViewModel;
    public TextView ahorro;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        saldoViewModel =new ViewModelProvider(this).get(SaldoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saldos, container, false);
        ahorro=root.findViewById(R.id.txtAhorroTotal);
        SharedPreferences  mPrefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("UsuarioData", "");
        UserResponse obj = gson.fromJson(json, UserResponse.class);


        return root;
    }

    @Override
    public void onFailureRequest(String message) {

    }

    @Override
    public void onSuccesRequest(SaldoResponse object) {
        ahorro.setText(object.getSaldoSARTotal().toString());
    }
}