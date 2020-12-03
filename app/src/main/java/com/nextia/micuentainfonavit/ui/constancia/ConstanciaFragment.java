package com.nextia.micuentainfonavit.ui.constancia;
/**
 * class of view Constancia de intereses
 */

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoResponse;
import com.nextia.domain.models.credit_info.RespuestUm;
import com.nextia.domain.models.user.Credito;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentConstanciaBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.constancia.pdf_download.PdfConstanciaDownloadViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.util.ArrayList;
import java.util.List;

public class ConstanciaFragment extends Fragment implements OnFinishRequestListener<CreditInfoResponse> {

    PdfConstanciaDownloadViewModel ViewModelPdf;
    FragmentConstanciaBinding binding;
    ArrayList<Credito> creditos;
    ArrayList<String> creditList = new ArrayList<>();
    ArrayAdapter<String> creditAdapter;
    ArrayAdapter<String> yearAdapter;
    CreditUseCase creditUseCase = new CreditUseCase();
    List<RespuestUm> listItemAnio;
    ArrayList<String> listanios = new ArrayList<>();
    NavController navController;

    //creating and instancing view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewModelPdf = new ViewModelProvider(getActivity()).get(PdfConstanciaDownloadViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_constancia, container, false);
        setSpinners();
        setButton();
        return binding.getRoot();
    }

    //function before initial view to show and stop skeleton
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView, R.layout.skeleton_constancia);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();

            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    //fill spinner with credits from sharedpreferences and set methods
    public void setSpinners() {
        //Set Data year
        listanios.clear();
        listanios.add("Selecciona un año");
        yearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listanios);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAniosConstancia.setAdapter(yearAdapter);

        creditos = Utils.getSharedPreferencesUserData(getContext()).getCredito();
        creditList.clear();
        //creditList.add("Selecciona una cuenta");
        for (int i = 0; i < creditos.size(); i++) {
            creditList.add(creditos.get(i).getNumeroCredito());
        }
        binding.spSeleccionaCreditoConstancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position!=0)
//                {
                if (Utils.isNetworkAvailable(getActivity())) {
                    creditUseCase.getInfoCredit("0000" + parent.getItemAtPosition(position).toString(), Utils.getSharedPreferencesToken(getContext()), ConstanciaFragment.this);
                } else {
                    DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Aviso", getString(R.string.no_internet), DialogInfonavit.ONE_BUTTON_DIALOG);
                    alertdialog.show();

                }

                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spAniosConstancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    binding.btnConsultarConstancia.setEnabled(true);
                    ViewModelPdf.setCredit("0000" + creditList.get(binding.spSeleccionaCreditoConstancia.getSelectedItemPosition()));
                    ViewModelPdf.setYear(parent.getItemAtPosition(position).toString());
                }else{
                    binding.btnConsultarConstancia.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnConsultarConstancia.setEnabled(false);
        creditAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, creditList);
        creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSeleccionaCreditoConstancia.setAdapter(creditAdapter);
    }

    //setButton methods and states
    public void setButton() {
        binding.btnConsultarConstancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_constancia_interes_to_nav_pdf_constancia);

            }
        });
    }

    //To manage on fail request
    @Override
    public void onFailureRequest(String message) {
        Toast.makeText(getContext(), "No se pudieron obtener los datos", Toast.LENGTH_LONG).show();
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

    //To manage on Succes request
    @Override
    public void onSuccesRequest(CreditInfoResponse object, String token) {
        listanios.clear();
        listanios.add("Selecciona un año");

        if(object != null && object.getRespuesta() != null) {
            listItemAnio = object.getRespuesta();
            for (int i = 0; i < listItemAnio.size(); i++) {
                if (i < 5) {
                    listanios.add(listItemAnio.get(i).getEjercicioFiscal());
                }
            }
        }
        
        yearAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listanios);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAniosConstancia.setAdapter(yearAdapter);

        if(listanios.size() > 1){
            binding.spAniosConstancia.setSelection(1);
        }
    }
}