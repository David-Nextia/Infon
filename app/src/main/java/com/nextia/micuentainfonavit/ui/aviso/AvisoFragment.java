package com.nextia.micuentainfonavit.ui.aviso;
/**
 * fragment of view aviso, suspensión y retención, it appears on the Main activity
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

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.aviso_suspension.AvisosPDFResponse;
import com.nextia.domain.models.user.Credito;
import com.nextia.micuentainfonavit.LoginActivity;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentAvisoBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.movements.views.movements.InnerMovementsFragment;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.ui.savings.SavingsViewModel;
import com.nextia.micuentainfonavit.ui.savings.tabs.ViewPagerAdapter;
import com.nextia.micuentainfonavit.usecases.NoticeSuspensionCase;
import com.nextia.micuentainfonavit.usecases.UserUseCase;

import java.io.File;
import java.util.ArrayList;

public class AvisoFragment extends Fragment {
    NoticeSuspensionCase noticeSuspensionCase = new NoticeSuspensionCase();
    FragmentAvisoBinding binding;
    ArrayList<Credito> creditos;
    ArrayAdapter<String> arrayAdapter;
    PdfViewViewModel pdfViewModel;
    NavController navController;
    ArrayList<String> Creditlist = new ArrayList<>();
    DialogInfonavit dialog;
    private AvisoViewModel mViewModel;
    File archivo;
    String selectedCredit;

    //To create view and instance methods
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_aviso, container, false);
        setSpinner();
        mViewModel = new ViewModelProvider(this).get(AvisoViewModel.class);
        binding.progressBar2.animate().alpha(0.0f);
        pdfViewModel= new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        mViewModel.getAviso().observe(getViewLifecycleOwner(), new Observer<AvisosPDFResponse>() {
            @Override
            public void onChanged(AvisosPDFResponse avisosPDFResponse) {
                if (avisosPDFResponse != null) {
                    binding.progressBar2.animate().alpha(0.0f);
                    Utils.hideLoadingSkeleton();
                    String response=avisosPDFResponse.getStatusServicio().getCodigo();
                    if (response.equals("02")) {
                        DialogInfonavit alertdialog = new DialogInfonavit(getActivity(), "Atención", "Este crédito no tiene avisos disponibles", DialogInfonavit.ONE_BUTTON_DIALOG);
                        alertdialog.show();
                        binding.suspensionUnsucess.setVisibility(View.VISIBLE);
                        binding.suspensionUnsucess.animate().alpha(1.0f);
                        binding.pdfView.animate().alpha(0.0f);
                        binding.pdfView.setVisibility(View.GONE);

                    } else {
                        binding.pdfView.setVisibility(View.VISIBLE);
                        binding.pdfView.animate().alpha(1.0f);
                        binding.suspensionUnsucess.animate().alpha(0.0f);
                        binding.suspensionUnsucess.setVisibility(View.GONE);
                        String tipAvis=avisosPDFResponse.getDatosAvisos().getItem().get(0).getTIPAVIS();
                        String clasAviso=avisosPDFResponse.getDatosAvisos().getItem().get(0).getCLASE_DEL_AVISO();
                        if(tipAvis.equals("")|| tipAvis.equals("02") && clasAviso.equals("R") ){
                            binding.avisoTypeTitle.setText("AVISO  PARA  RETENCIÓN  DE  DESCUENTOS");
                            archivo=Utils.createPdfFromCanvas(mViewModel,"aviso_retencion",getActivity(),2);
                        }
                        else if(tipAvis.equals("12")||tipAvis.equals("12") && clasAviso.equals("S")){
                            binding.avisoTypeTitle.setText("AVISO  DE  SUSPENSIÓN  DE  DESCUENTOS");
                            archivo=Utils.createPdfFromCanvas(mViewModel,"aviso_suspension",getActivity(),3);
                        }
                        else if(tipAvis.equals("10") && clasAviso.equals("S")){
                            binding.avisoTypeTitle.setText("AVISO DE SUSPENSIÓN POR PRÓXIMA LIQUIDACIÓN DE CRÉDITO");
                            archivo=Utils.createPdfFromCanvas(mViewModel,"aviso_liquidacion",getActivity(),4);

                        }
                        else if(tipAvis.equals("03") || tipAvis.equals("07")  && clasAviso.equals("R")){
                            binding.avisoTypeTitle.setText( "AVISO  DE  MODIFICACIÓN  AL  FACTOR  DE  DESCUENTOS");
                            archivo=Utils.createPdfFromCanvas(mViewModel,"aviso_modificacion",getActivity(),5);
                        }

                    }
                } else {
                    dialog.show();
                    Utils.hideLoadingSkeleton();
                    binding.progressBar2.animate().alpha(0.0f);
                    binding.pdfView.animate().alpha(0.0f);
                    binding.pdfView.setVisibility(View.VISIBLE);
                }

            }
        });
        mViewModel.getTokenExpired().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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
        binding.sharePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.sharePdf(archivo,getActivity());
            }
        });

        binding.dowloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfViewModel.setFile(archivo);
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_aviso_suspension_to_nav_pdf_viewer);
            }
        });
        return binding.getRoot();


    }

    //fill spinner with credits from sharedpreferences and set methods
    public void setSpinner() {
        creditos = Utils.getSharedPreferencesUserData(getContext()).getCredito();
        Creditlist.clear();
        //Creditlist.add("Selecciona una cuenta");
        for (int i = 0; i < creditos.size(); i++) {
            Creditlist.add("0000" + creditos.get(i).getNumeroCredito());
        }
        binding.spCredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //binding.progressBar2.animate().alpha(1.0f);
                selectedCredit = parent.getSelectedItem().toString();
                mViewModel.getAvisoDB(getContext(), selectedCredit.substring(4), Utils.getSharedPreferencesToken(getContext()));
                Utils.showLoadingSkeleton(binding.rootView, R.layout.skeleton_aviso);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Creditlist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spCredit.setAdapter(arrayAdapter);
    }

}
