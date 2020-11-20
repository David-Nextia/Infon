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
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentInnerMovementsBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.ui.savings.SavingsViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;
import java.io.FileNotFoundException;

import okhttp3.internal.Util;

public class InnerMovementsFragment extends Fragment implements OnFinishRequestListener<HistoricResponse> {
    private View rootView;
    FragmentInnerMovementsBinding binding;
    CreditUseCase creditUseCase = new CreditUseCase();

    Spinner spinnerCredit;
    NavController navController;
    PdfViewViewModel pdfViewModel;
    File historic;
    String credit;
    HistoricResponse object_final;

    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inner_movements, container, false);

        spinnerCredit = binding.spCreditType;
        pdfViewModel = new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        rootView = binding.rootView;
        binding.progressBar2.animate().alpha(0.0f);
        setSpinner();
        setOnclicks();
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
                    Utils.showLoadingSkeleton(rootView, R.layout.skeleton_inner_movements);
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
                    historic = Utils.createPdfFromBase64(object_final.getReporte(), "historic_"+credit, getActivity(), true);

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
        binding.mensualImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showShareIntent(getActivity());
            }
        });
        binding.movementsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showShareIntent(getActivity());
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
    public void onSuccesRequest(HistoricResponse object, String token) {
        binding.historicContainer.animate().alpha(1);
        binding.shareHistoricPdf.animate().alpha(1.0f);
        binding.historicImg.animate().alpha(1);
        binding.textDownloadHistoric.animate().alpha(1.0f);
        binding.progressBar2.animate().alpha(0.0f);
        Utils.hideLoadingSkeleton();
        object_final = object;

    }
}