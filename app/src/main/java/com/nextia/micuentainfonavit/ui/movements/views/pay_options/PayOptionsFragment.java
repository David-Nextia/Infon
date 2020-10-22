package com.nextia.micuentainfonavit.ui.movements.views.pay_options;
/**
 * view of opciones de pago inside movements
 */
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nextia.domain.models.saldo_movimientos.OpcionesPago;
import com.nextia.domain.models.saldo_movimientos.PagosMensualidades;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.domain.models.saldo_movimientos.TablaPagos1;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentPayOptionsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.MovementsViewModel;

import java.util.ArrayList;

public class PayOptionsFragment extends Fragment {

    private FragmentPayOptionsBinding binding;
    private NavController navController;
    private View rootView;
    Spinner spinnerCredit;
    private MovementsViewModel viewModel;

    public static PayOptionsFragment newInstance() {
        return new PayOptionsFragment();
    }
    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_options, container, false);
        spinnerCredit=binding.spCreditType;
        rootView = binding.rootView;
        viewModel = new ViewModelProvider(this).get(MovementsViewModel.class);
        setSpinner();
        setOnClicks();
        return binding.getRoot();
    }

    //creates the observer to the data from viewmodel, ad creates success and fail cases
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getSaldosMovimientos().observe(getViewLifecycleOwner(), new Observer<SaldoMovimientosResponse>() {
            @Override
            public void onChanged(SaldoMovimientosResponse saldoMovimientosResponse) {
                if(saldoMovimientosResponse != null && saldoMovimientosResponse.getReturnData() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades() != null){
                    binding.setPagosMensualidades(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades());
                    binding.setOpcionesPago(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago());
                    if(!saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago().getV13SdoliqpesProymesCondesc().trim().equals("")){
                        binding.sectionDebtAmountDiscount.setVisibility(View.VISIBLE);
                    }else{
                        binding.sectionDebtAmountDiscount.setVisibility(View.GONE);
                    }
                    Utils.hideLoadingSkeleton();
                }else {
                    dialogError();
                }
            }
        });

        //To manage on Token Expired
        viewModel.getTokenExpired().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == false) {
                    DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso", getString(R.string.expired_Session), DialogInfonavit.ONE_BUTTON_DIALOG, new DialogInfonavit.OnButtonClickListener() {
                        @Override
                        public void onAcceptClickListener(Button button, AlertDialog dialog) {
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            dialog.dismiss();
                            startActivity(i);
                            getActivity().finish();
                        }
                    });
                    alertdialog.show();
                }
            }
        });
    }


    //setting clicks on view
    public void setOnClicks(){
        binding.lyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_banks);
            }
        });

        binding.lyMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_market);
            }
        });

        binding.lyUsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_usa);
            }
        });
    }

    //method to fill spinner
    public void setSpinner() {
        Utils.fillSpinnerWithCredit(getContext(), spinnerCredit);
        spinnerCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // if (position != 0) {
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_pay_options);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
//                }else{
//                    binding.setOpcionesPago(new OpcionesPago());
//                    binding.setPagosMensualidades(new PagosMensualidades());
//                }
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