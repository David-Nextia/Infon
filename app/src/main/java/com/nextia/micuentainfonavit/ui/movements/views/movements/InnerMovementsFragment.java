package com.nextia.micuentainfonavit.ui.movements.views.movements;
/**
 * view of movimientos inside movements
 */

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.mensual_report.MensualReportResponse;
import com.nextia.domain.models.mensual_report.PeriodResponse;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.domain.models.reports.ReportMovsBody;
import com.nextia.domain.models.reports.ReportMovsResponse;
import com.nextia.domain.models.saldo_movimientos.Movimiento;
import com.nextia.domain.models.saldo_movimientos.MovsResponse;
import com.nextia.domain.models.saldo_movimientos.SaldoMovimientosResponse;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentInnerMovementsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.MovementsViewModel;
import com.nextia.micuentainfonavit.ui.movements.logic_views.ViewsConfig;
import com.nextia.micuentainfonavit.ui.movements.views.movements.adapter.AdapterMovements;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InnerMovementsFragment extends Fragment implements OnFinishRequestListener {
    private View rootView;
    FragmentInnerMovementsBinding binding;
    CreditUseCase creditUseCase = new CreditUseCase();
    Spinner spinnerCredit;
    int expandedListHeight=0;
    NavController navController;
    PdfViewViewModel pdfViewModel;
    String period;
    String token1="";
    boolean started=false;
    File historic, mensual,movs;
    String mensualReporturl, movsReporturl;
    String credit;
    HistoricResponse object_final;
    private MovementsViewModel viewModel;
    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       if(getActivity()!=null && isAdded())
       {binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inner_movements, container, false);

        spinnerCredit = binding.spCreditType;
        pdfViewModel = new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        rootView = binding.rootView;
        //binding.progressBar2.animate().alpha(0.0f);
        viewModel = new ViewModelProvider(getActivity()).get(MovementsViewModel.class);
        setSpinner();
        setOnclicks();
        viewModel.getSaldosMovimientos().observe(getViewLifecycleOwner(), new Observer<SaldoMovimientosResponse>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onChanged(SaldoMovimientosResponse saldoMovimientosResponse) {
                String type="";
                try{
                    type= saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV1TipoCredito()+" "+saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV10TipoCreditoFam();
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
                if(!viewModel.getConfig().getValue().getModulos().get(ViewsConfig.MOVEMENTS))
                {
                    setBlur(saldoMovimientosResponse);
                    binding.scrollview.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return true;
                        }
                    });
                    binding.mensualImg.setOnClickListener(null);
                    binding.historicImg.setOnClickListener(null);
                    binding.movsImg.setOnClickListener(null);
                    binding.textDownloadMovs.setOnClickListener(null);
                    binding.textDownloadMensual.setOnClickListener(null);
                    binding.textDownloadHistoric.setOnClickListener(null);


                }
             if(saldoMovimientosResponse!=null)
             { if(saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getOpcionesPago().getV11Sdoliqpes().trim().equals("0.00")){
                    //Toast.makeText(getContext(),"es cer",Toast.LENGTH_LONG).show();

                    String liquid1="<b>" + "Tipo de liquidación: "+ "</b> " +saldoMovimientosResponse.getReturnData().getRespuestasDoMovs().getPagosMensualidades().getV3TipoLiquidacion().trim();
                    binding.LiquidType.setText(Html.fromHtml(liquid1));
                    binding.LiquidType.setVisibility(View.VISIBLE);

                }}
            }
        });
        return binding.getRoot();}
       else return null;

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
                    token1=Utils.getSharedPreferencesToken(getContext());
                    creditUseCase.getInfoCreditHistoric(token1, parent.getItemAtPosition(position).toString(), InnerMovementsFragment.this);
                    creditUseCase.getPeriodosDisponibles(token1, parent.getItemAtPosition(position).toString(), InnerMovementsFragment.this);
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_inner_movements);
                    viewModel.getMovements(getContext(), parent.getItemAtPosition(position).toString());
                    binding.movementsContainer.setVisibility(View.GONE);

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
                    movs= Utils.createPdfFromBase64(movsReporturl, "EstadoCuentaMovimientos_"+credit, getActivity(), false);

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
                    historic = Utils.createPdfFromBase64(object_final.getReporte(), "EstadoCuentaHistorico_"+credit, getActivity(), false);

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
                    mensual= Utils.createPdfFromBase64(mensualReporturl, "EstadoCuentaMensual_"+credit, getActivity(), false);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //handle fail response of server
    @Override
    public void onFailureRequest(String message) {
        DialogInfonavit dialog = new DialogInfonavit(getActivity(),"Aviso", message, DialogInfonavit.ONE_BUTTON_DIALOG);
        //binding.progressBar2.animate().alpha(0.0f);
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
       // binding.progressBar2.animate().alpha(0.0f);
        //Utils.hideLoadingSkeleton();

        //respuesta del historico
        if(object instanceof HistoricResponse)
        {
            if(((HistoricResponse)object).getStatusServicio().getCodigo().equals("00"))

            {
                object_final = (HistoricResponse) object;
                binding.historicContainer.setVisibility(View.VISIBLE);
                Utils.hideLoadingSkeleton();

            }
            else{
                binding.historicContainer.setVisibility(View.GONE);
                Utils.hideLoadingSkeleton();
            }




        }

        //respuesta de periodos
        else if(object instanceof PeriodResponse)
        {
           if( ((PeriodResponse)object).getRoot().getPeriodo()!=null)
           {
               //Toast.makeText(getActivity(),((PeriodResponse)object).getRoot().getPeriodo().toString(),Toast.LENGTH_LONG).show();
               period=((PeriodResponse)object).getRoot().getPeriodo();

               creditUseCase.getMensualReport(token1,credit,period,InnerMovementsFragment.this);
               creditUseCase.getReportMovs(token1,credit,"",InnerMovementsFragment.this);
               creditUseCase.getMovsData(token1,credit,InnerMovementsFragment.this);

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
               binding.movementsContainer.setVisibility(View.GONE);
           }
        }

        //respuesta de reporte mensual
        else if(object instanceof MensualReportResponse){
            mensualReporturl=((MensualReportResponse)object).getReporte();
            binding.monthlyContainer.setVisibility(View.VISIBLE);
            binding.txtLastPeriod.setText(period);
        }

        //respuesta movimientos
        else if(object instanceof ReportMovsResponse){
            //Toast.makeText(getActivity(),((ReportMovsResponse)object).getReturn().getReporte(),Toast.LENGTH_LONG).show();


            if(((ReportMovsResponse)object).getReturn().getCodigo().equals("00")){
                binding.movementsContainer.setVisibility(View.VISIBLE);
                movsReporturl=((ReportMovsResponse)object).getReturn().getReporte();
                if(object_final!=null){
                    Utils.hideLoadingSkeleton();
                }
            }else{
                binding.movementsContainer.setVisibility(View.GONE);
                if(object_final!=null){
                    Utils.hideLoadingSkeleton();
                }
            }


        }
        else if(object instanceof MovsResponse){

            Map<String, List<String>> movementsCollection = new LinkedHashMap<String, List<String>>();
            List<String> groupList = new ArrayList<String>();
            List<String> childList;
           MovsResponse movItems=((MovsResponse)object);
            for (int i = 0; i < Integer.parseInt(movItems.getNumeroMovimientos()); i++) {
                childList = new ArrayList<String>();
                String fecha = movItems.getMovimientos().get(i).getFecha();
                String montoTransaccion = movItems.getMovimientos().get(i).getMontoTransaccion();

                groupList.add(fecha + "&" + montoTransaccion);

                String pagoASeguro = movItems.getMovimientos().get(i).getPagoASeguro();
                String pagoAIntereses = movItems.getMovimientos().get(i).getPagoAIntereses();
                String pagoACapital = movItems.getMovimientos().get(i).getPagoACapital();

                childList.add("Pagos a seguros y cuotas" + "&" + pagoASeguro);
                childList.add("Pagos a interés" + "&" + pagoAIntereses);
                childList.add("Pagos a capital" + "&" + pagoACapital);

                movementsCollection.put(groupList.get(i), childList);
            }
            AdapterMovements adapterMovements = new AdapterMovements(getActivity(), groupList, movementsCollection);
            binding.listMovements.setAdapter(adapterMovements);

            /*problem with neste scroll*/
            binding.listMovements.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    setListViewHeight(parent, groupPosition,true);
                    return false;
                }
            });
                setListViewHeight(binding.listMovements, -1,false);
                started=true;



        }



    }
    private void setListViewHeight(ExpandableListView listView, int group, boolean expand) {

        if(getActivity()!=null)
        { ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if(expand || !started){

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

            int height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
            if (height < 10)
                height = 200;
            params.height = height + 30;
            expandedListHeight=params.height;
        }
        else{
            params.height=expandedListHeight;
        }


        listView.setLayoutParams(params);
        listView.requestLayout();}

    }
    void blurView(String credit, String liquid){
        binding.viewAdvice.animate().alpha(1);
        // binding.rootView.animate().alpha(0.1f);
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
        blurView(credit1,liquid1);
    }
}