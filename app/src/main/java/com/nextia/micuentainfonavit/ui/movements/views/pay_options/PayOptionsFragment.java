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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.nextia.micuentainfonavit.ui.movements.logic_views.MessageConfig;
import com.nextia.micuentainfonavit.ui.movements.logic_views.ViewsConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        viewModel = new ViewModelProvider(getActivity()).get(MovementsViewModel.class);
        viewModel.setInit(false);

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
                String type="";
                String messagesTypeCredit = "";
                if(saldoMovimientosResponse != null && saldoMovimientosResponse.getReturnData() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago() != null && saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades() != null){
                    binding.setPagosMensualidades(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades());
                    binding.setOpcionesPago(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago());
                    if(!saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago().getV13SdoliqpesProymesCondesc().trim().equals("")){
                        //binding.sectionDebtAmountDiscount.setVisibility(View.VISIBLE);
                    }else{
                        binding.sectionDebtAmountDiscount.setVisibility(View.GONE);
                    }
                    Utils.hideLoadingSkeleton();
                    /* if(!saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos2().getTp33MesesDispProrr().trim().equals("00")&&
                            !saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos2().getTp37IniProrr().trim().equals("")){
                        binding.prorroga.setAlpha(0);
                        binding.prorroga.setVisibility(View.VISIBLE);
                        binding.prorroga.animate().alpha(1);
                    } */
                    try{
                        type= saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito()+" "+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV10TipoCreditoFam();
                        messagesTypeCredit = MessageConfig.buildMessage(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs());
                    } catch (Exception e){}
                    String sourceString = "<b>" + "Tipo de crédito: "+ "</b> " +type ;
                    binding.creditType.setText(Html.fromHtml(sourceString));
                    if(!messagesTypeCredit.equals("")) {
                        binding.prorroga.setText(messagesTypeCredit);
                        binding.imgMoreInfo.setVisibility(View.VISIBLE);
                    }
                    if(!viewModel.getConfig().getValue().getModulos().get(ViewsConfig.PAY_OPTIONS)) {
                        setBlur(saldoMovimientosResponse);
                        binding.lnrTypeLinear.setVisibility(View.GONE);
                        binding.lyUsa.setOnClickListener(null);
                        binding.lyMarket.setOnClickListener(null);
                        binding.lyBank.setOnClickListener(null);
                        binding.spCreditType.setEnabled(false);
                    }
                    binding.imgMoreInfo.setOnClickListener(view-> {
                        if(binding.prorroga.getVisibility() == View.VISIBLE) {
                            binding.imgMoreInfo.setImageResource(R.drawable.ic_expand_more);
                            binding.prorroga.animate().alpha(1);
                            binding.prorroga.setVisibility(View.GONE);
                            binding.prorroga.setAlpha(0);
                        } else {
                            binding.imgMoreInfo.setImageResource(R.drawable.ic_arrow_up);
                            binding.prorroga.setAlpha(0);
                            binding.prorroga.setVisibility(View.VISIBLE);
                            binding.prorroga.animate().alpha(1);
                        }
                    });

                    binding.lnrTypeLinear.setOnClickListener(v -> {
                        if(binding.imgMoreInfo.getVisibility() == View.VISIBLE){
                            binding.imgMoreInfo.performClick();
                        }
                    });
                }else {
                    if(viewModel.getInit().getValue())
                    {dialogError();}
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
                if(Utils.isNetworkAvailable(getActivity())){
                    viewModel.cancelMovs();
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_pay_options);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
                }
                else{
                    DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso",getString(R.string.no_internet), DialogInfonavit.ONE_BUTTON_DIALOG);
                    alertdialog.show();

                }


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
    void blurView(String credit, String liquid){
        binding.viewAdvice.animate().alpha(1);
        //binding.rootView.animate().alpha(0.1f);
        binding.typeCredit2.setText(credit);
        binding.liquidType.setText(liquid);
        binding.blurView.animate().alpha(1);



    }
    void setBlur(SaldoMovimientosResponse saldoMovimientosResponse){
        String type="";
        try{
            String one=saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito().trim();
            String two=saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV10TipoCreditoFam().trim();
            type= one+" "+two;
        }catch (Exception e){}
        String date= saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV2FechaLiquidacion().trim();
        //String date=item.get(0).getFCREAVIS();
        SimpleDateFormat spf=new SimpleDateFormat("yyyyMMdd");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd.MM.yyyy");
        date = spf.format(newDate);
        String credit1="TU CRÉDITO "+type+" FUE LIQUIDADO EL "+date;
        String liquid1="Tipo de liquidación: \n"+(" "+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV3TipoLiquidacion()).trim();
        binding.info.setText(  viewModel.getConfig().getValue().getMensaje());
        blurView(credit1,liquid1);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //viewModel.cancelMovs();

    }
}