package com.nextia.micuentainfonavit.ui.movements.views.credit_data;
/**
 * view of datos de mi credito inside movements
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

import android.os.CountDownTimer;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nextia.domain.models.saldo_movimientos.OpcionesPago;
import com.nextia.domain.models.saldo_movimientos.OriginacionCredito;
import com.nextia.domain.models.saldo_movimientos.PagosMensualidades;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentCreditDataBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.constancia.pdf_download.PdfConstanciaDownloadFragment;
import com.nextia.micuentainfonavit.ui.movements.MovementsViewModel;

import java.util.ArrayList;

public class CreditDataFragment extends Fragment {
    FragmentCreditDataBinding binding;
    private View rootView;
    Spinner spinnerCredit;
    private MovementsViewModel viewModel;

    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_credit_data, container, false);
        spinnerCredit = binding.spCreditType;
        viewModel = new ViewModelProvider(this).get(MovementsViewModel.class);
        rootView = binding.rootView;
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
                String type="";
                if(saldoMovimientosResponse != null && saldoMovimientosResponse.getReturnData() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOriginacionCredito() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades() != null){
                    binding.setOriginacionCredito(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOriginacionCredito());
                    binding.setPagosMensualidad(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades());
                    Utils.hideLoadingSkeleton();
                    try{
                        type= saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito().substring(0,1)+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito().substring(1).toLowerCase()+" "+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV10TipoCreditoFam().toLowerCase();
                    }catch (Exception e){}
                    String sourceString = "<b>" + "Tipo de crédito: "+ "</b> " +type ;
                    binding.creditType.setText(Html.fromHtml(sourceString));
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
    public void setSpinner() {
        Utils.fillSpinnerWithCredit(getContext(), spinnerCredit);
        spinnerCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // if (position != 0) {

                if(Utils.isNetworkAvailable(getActivity())){
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_credit_data);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
                }
                else{
                    DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso",getString(R.string.no_internet), DialogInfonavit.ONE_BUTTON_DIALOG);
                    alertdialog.show();

                }

//                }else{
//                    binding.setPagosMensualidad(new PagosMensualidades());
//                    binding.setOriginacionCredito(new OriginacionCredito());
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