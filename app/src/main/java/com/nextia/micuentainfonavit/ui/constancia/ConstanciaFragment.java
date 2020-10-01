package com.nextia.micuentainfonavit.ui.constancia;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_info.CreditInfoResponse;
import com.nextia.domain.models.credit_info.RespuestUm;
import com.nextia.domain.models.user.Credito;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentConstanciaBinding;
import com.nextia.micuentainfonavit.ui.constancia.pdf_download.PdfConstanciaDownloadViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.util.ArrayList;
import java.util.List;

public class ConstanciaFragment extends Fragment implements OnFinishRequestListener<CreditInfoResponse> {

    private ConstanciaViewModel mViewModel;
   PdfConstanciaDownloadViewModel ViewModelPdf;
    FragmentConstanciaBinding binding;
    ArrayList<Credito> creditos;
    ArrayList<String> creditList =new ArrayList<>();
    ArrayAdapter<String> creditAdapter;
    ArrayAdapter<String> yearAdapter;
    CreditUseCase creditUseCase= new CreditUseCase();
    List<RespuestUm> listItemAnio;
    ArrayList<String> listanios=new ArrayList<>();
    NavController navController;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        ViewModelPdf= new ViewModelProvider(getActivity()).get(PdfConstanciaDownloadViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_constancia, container, false);
        creditos=Utils.getSharedPreferencesUserData(getContext()).getCredito();
        creditList.clear();
        creditList.add("Seleccionar crédito");
        for(int i=0; i<creditos.size();i++){
            creditList.add("0000"+creditos.get(i).getNumeroCredito());
        }
        binding.spSeleccionaCreditoConstancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                creditUseCase.getInfoCredit(parent.getItemAtPosition(position).toString(), ConstanciaFragment.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spAniosConstancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    binding.btnConsultarConstancia.setEnabled(true);
                    ViewModelPdf.setCredit(creditList.get(binding.spSeleccionaCreditoConstancia.getSelectedItemPosition()));
                    ViewModelPdf.setYear(parent.getItemAtPosition(position).toString());
                    //Toast.makeText(getContext(),ViewModelPdf.getCredit().getValue()+" "+ViewModelPdf.getYear().getValue(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnConsultarConstancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_constancia_interes_to_nav_pdf_constancia);

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView,R.layout.skeleton_constancia);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
                binding.btnConsultarConstancia.setEnabled(false);
                creditAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, creditList);
                creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spSeleccionaCreditoConstancia.setAdapter(creditAdapter);
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

    @Override
    public void onFailureRequest(String message) {
        Toast.makeText(getContext(),"fallo",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccesRequest(CreditInfoResponse object) {
        listItemAnio =object.getRespuesta();
        listanios.clear();
        listanios.add("Seleccionar año");
        for(int i = 0; i< listItemAnio.size(); i++){
            listanios.add(listItemAnio.get(i).getEjercicioFiscal());
        }
        yearAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, listanios);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAniosConstancia.setAdapter(yearAdapter);


    }
}