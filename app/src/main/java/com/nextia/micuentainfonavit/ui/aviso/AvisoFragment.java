package com.nextia.micuentainfonavit.ui.aviso;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.nextia.domain.models.user.Credito;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentAvisoBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;

import java.util.ArrayList;

public class AvisoFragment extends Fragment {

    private AvisoViewModel mViewModel;
    FragmentAvisoBinding binding;
    public static AvisoFragment newInstance() {
        return new AvisoFragment();
    }
    ArrayList<Credito> creditos;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> hey=new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_aviso, container, false);
        creditos= Utils.getSharedPreferencesUserData(getContext()).getCredito();
        hey.clear();
        hey.add("Seleccionar crédito");
        for(int i=0; i<creditos.size();i++){
            hey.add("0000"+creditos.get(i).getNumeroCredito());
        }
        binding.spCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                { DialogInfonavit dialog= new DialogInfonavit(getContext(), getString(R.string.title_error),getString(R.string.message_server_error), DialogInfonavit.ONE_BUTTON_DIALOG);
                dialog.show();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView,R.layout.skeleton_aviso);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
                arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, hey);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spCredit.setAdapter(arrayAdapter);
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}