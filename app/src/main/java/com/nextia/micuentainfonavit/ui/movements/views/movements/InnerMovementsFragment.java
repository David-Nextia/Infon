package com.nextia.micuentainfonavit.ui.movements.views.movements;
/**
 * view of movimientos inside movements
 */

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.mensual_report.MensualReportResponse;
import com.nextia.domain.models.mensual_report.PeriodResponse;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.reports.ReportMovsBody;
import com.nextia.domain.models.reports.ReportMovsResponse;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentInnerMovementsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.MovementsViewModel;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InnerMovementsFragment extends Fragment implements OnFinishRequestListener {
    private View rootView;
    FragmentInnerMovementsBinding binding;
    CreditUseCase creditUseCase = new CreditUseCase();
    Spinner spinnerCredit;
    NavController navController;
    PdfViewViewModel pdfViewModel;
    String period;
    File historic, mensual,movs;
    String mensualReporturl, movsReporturl;
    String credit;
    HistoricResponse object_final;
    private MovementsViewModel viewModel;
    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inner_movements, container, false);

        spinnerCredit = binding.spCreditType;
        pdfViewModel = new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        rootView = binding.rootView;
        binding.progressBar2.animate().alpha(0.0f);
        viewModel = new ViewModelProvider(this).get(MovementsViewModel.class);
        setSpinner();
        setOnclicks();
        viewModel.getSaldosMovimientos().observe(getViewLifecycleOwner(), new Observer<SaldoMovimientosResponse>() {
            @Override
            public void onChanged(SaldoMovimientosResponse saldoMovimientosResponse) {
                String type="";
                try{
                    type= saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito().substring(0,1)+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito().substring(1).toLowerCase()+" "+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV10TipoCreditoFam().toLowerCase();
                }catch (Exception e){}
                String sourceString = "<b>" + "Tipo de crédito: "+ "</b> " +type ;
                binding.creditType.setText(Html.fromHtml(sourceString));
             if(saldoMovimientosResponse!=null)
             {if(!saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos2().getTp33MesesDispProrr().trim().equals("00")&&
                        !saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getTablaPagos2().getTp37IniProrr().trim().equals("")){
                    binding.prorroga.setAlpha(0);
                    binding.prorroga.setVisibility(View.VISIBLE);
                    binding.prorroga.animate().alpha(1);
                }}
            }
        });
        return binding.getRoot();

    }

    //fill spinner and set methods
    public void setSpinner() {
        Utils.fillSpinnerWithCredit(getContext(), spinnerCredit);
        binding.spCreditType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position!=0)
//                {
                binding.shareHistoricPdf.animate().alpha(0.0f);
                binding.textDownloadHistoric.animate().alpha(0.0f);

                if (Utils.isNetworkAvailable(getActivity())) {

                    // binding.progressBar2.animate().alpha(1.0f);
                    credit=parent.getItemAtPosition(position).toString();
                    creditUseCase.getInfoCreditHistoric(Utils.getSharedPreferencesToken(getContext()), parent.getItemAtPosition(position).toString(), InnerMovementsFragment.this);
                    creditUseCase.getPeriodosDisponibles(Utils.getSharedPreferencesToken(getContext()), parent.getItemAtPosition(position).toString(), InnerMovementsFragment.this);
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_inner_movements);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
                } else {
                    DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso", getString(R.string.no_internet), DialogInfonavit.ONE_BUTTON_DIALOG);
                    alertdialog.show();
                    binding.historicImg.animate().alpha(0);
                    binding.historicContainer.animate().alpha(0);
                }


                //viewmodel.loadHistoric(getActivity(), parent.getItemAtPosition(position).toString());


                //}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //set Onclicks methods
    public void setOnclicks() {
        binding.historicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mensual= Utils.createPdfFromBase64(object_final.getReporte(), "mensual_"+credit, getActivity(), true);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pdfViewModel.setFile(mensual);
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_movements_to_nav_pdf_viewer);

            }
        });
        binding.mensualImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    historic = Utils.createPdfFromBase64(mensualReporturl, "historic_"+credit, getActivity(), true);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pdfViewModel.setFile(historic);
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_movements_to_nav_pdf_viewer);

            }
        });

        binding.shareHistoricPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (historic != null) {
                    Utils.sharePdf(historic, getActivity());
                }
            }
        });

        binding.movsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    movs = Utils.createPdfFromBase64(movsReporturl, "movs_"+credit, getActivity(), true);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pdfViewModel.setFile(movs);
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_movements_to_nav_pdf_viewer);
            }
        });
        binding.textDownloadMovs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInfonavit dialog = new DialogInfonavit(getContext(), getString(R.string.title_error), "Descarga exitosa:\nEl documento se ha guardado en tu carpeta de descargas.", DialogInfonavit.ONE_BUTTON_DIALOG);
                dialog.show();
                try {
                    movs= Utils.createPdfFromBase64(object_final.getReporte(), "movs_"+credit, getActivity(), false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.textDownloadHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInfonavit dialog = new DialogInfonavit(getContext(), getString(R.string.title_error), "Descarga exitosa:\nEl documento se ha guardado en tu carpeta de descargas.", DialogInfonavit.ONE_BUTTON_DIALOG);
                dialog.show();
                try {
                    historic = Utils.createPdfFromBase64(object_final.getReporte(), "Reporte_histórico_movtos_"+credit, getActivity(), false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.textDownloadMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInfonavit dialog = new DialogInfonavit(getContext(), getString(R.string.title_error), "Descarga exitosa:\nEl documento se ha guardado en tu carpeta de descargas.", DialogInfonavit.ONE_BUTTON_DIALOG);
                dialog.show();
                try {
                    mensual= Utils.createPdfFromBase64(mensualReporturl, "mensual_"+credit, getActivity(), false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //handle fail response of server
    @Override
    public void onFailureRequest(String message) {
        DialogInfonavit dialog = new DialogInfonavit(getContext(), getString(R.string.title_error), message, DialogInfonavit.ONE_BUTTON_DIALOG);
        binding.progressBar2.animate().alpha(0.0f);
        dialog.show();

    }

    //to manage token expired
    @Override
    public void onTokenExpired() {
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

    //handle success response of server
    @Override
    public void onSuccesRequest(Object object, String token) {
        binding.historicContainer.animate().alpha(1);
        binding.shareHistoricPdf.animate().alpha(1.0f);
        binding.historicImg.animate().alpha(1);
        binding.textDownloadHistoric.animate().alpha(1.0f);
        binding.progressBar2.animate().alpha(0.0f);
        //Utils.hideLoadingSkeleton();

        //respuesta del historico
        if(object instanceof HistoricResponse)
        {object_final = (HistoricResponse) object;}

        //respuesta de periodos
        else if(object instanceof PeriodResponse)
        {
           if( ((PeriodResponse)object).getRoot().getPeriodo()!=null)
           {
               //Toast.makeText(getActivity(),((PeriodResponse)object).getRoot().getPeriodo().toString(),Toast.LENGTH_LONG).show();
               period=((PeriodResponse)object).getRoot().getPeriodo();

               creditUseCase.getMensualReport(Utils.getSharedPreferencesToken(getContext()),credit,period,InnerMovementsFragment.this);
               creditUseCase.getReportMovs(Utils.getSharedPreferencesToken(getContext()),credit,"",InnerMovementsFragment.this);

               SimpleDateFormat spf=new SimpleDateFormat("yyyyMM");
               Date newDate= null;
               try {
                   newDate = spf.parse(period);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               spf= new SimpleDateFormat("MMMM yyyy");
               period = spf.format(newDate);




           }else{
               Utils.hideLoadingSkeleton();
           }
        }

        //respuesta de reporte mensual
        else if(object instanceof MensualReportResponse){
            mensualReporturl=((MensualReportResponse)object).getReporte();
            binding.monthlyContainer.setVisibility(View.VISIBLE);
            Utils.hideLoadingSkeleton();
            binding.txtLastPeriod.setText(period);
        }

        else if(object instanceof ReportMovsResponse){
            //Toast.makeText(getActivity(),((ReportMovsResponse)object).getReturn().getReporte(),Toast.LENGTH_LONG).show();

            movsReporturl=((ReportMovsResponse)object).getReturn().getReporte();
            if(((ReportMovsResponse)object).getReturn().getCodigo().equals("00")){
                binding.movementsContainer.setVisibility(View.VISIBLE);
            }


        }



    }
}