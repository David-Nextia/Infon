package com.nextia.micuentainfonavit.ui.movements.views.mensualidades;
/**
 * view of mensualidades inside movements
 */

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.domain.models.saldo_movimientos.TablaPagos1;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentMensualidadesBinding;
import com.nextia.micuentainfonavit.databinding.FragmentSavingsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.MovementsViewModel;
import com.nextia.micuentainfonavit.ui.savings.SavingsViewModel;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class MensualidadesFragment extends Fragment {

    private View rootView;
    private MovementsViewModel viewModel;
    FragmentMensualidadesBinding binding;
    public static MensualidadesFragment newInstance() {
        return new MensualidadesFragment();
    }

    Spinner spinnerCredit;

    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mensualidades, container, false);
        rootView = binding.rootView;
        spinnerCredit = binding.spCreditType;
        viewModel = new ViewModelProvider(this).get(MovementsViewModel.class);
        setSpinner();
        return binding.getRoot();
    }

    //creates the observer to the data from viewmodel, ad creates success and fail cases
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getSaldosMovimientos().observe(getViewLifecycleOwner(), new Observer<SaldoMovimientosResponse>() {
            @Override
            public void onChanged(SaldoMovimientosResponse saldoMovimientosResponse) {
                if(saldoMovimientosResponse != null && saldoMovimientosResponse.getReturnData() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos1() != null){
                    binding.setSaldo(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos1());
                    Utils.hideLoadingSkeleton();
                }else {
                    dialogError();
                }
            }
        });
    }

    //function before initial view to show and stop skeleton
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView, R.layout.skeleton_mensualidades);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }


    //method to fill spinner
    public void setSpinner() {
        Utils.fillSpinnerWithCredit(getContext(), spinnerCredit);
        spinnerCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_mensualidades);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
                }else{
                    binding.setSaldo(new TablaPagos1());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //method that shows the dalog error
    private void dialogError(){
        DialogInfonavit alertdialog = new DialogInfonavit(getContext(), getString(R.string.title_error),getString(R.string.message_server_error), DialogInfonavit.ONE_BUTTON_DIALOG, new DialogInfonavit.OnButtonClickListener() {
            @Override
            public void onAcceptClickListener(Button button, AlertDialog dialog) {
                dialog.dismiss();
                spinnerCredit.setSelection(0);
                Utils.hideLoadingSkeleton();
            }
        });
        alertdialog.show();
    }
}