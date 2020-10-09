package com.nextia.micuentainfonavit.ui.movements.views.movements;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.reports.HistoricResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentInnerMovementsBinding;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;
import java.io.FileNotFoundException;

import okhttp3.internal.Util;

public class InnerMovementsFragment extends Fragment implements OnFinishRequestListener<HistoricResponse> {

    private InnerMovementsViewModel mViewModel;
    private View rootView;
    FragmentInnerMovementsBinding binding;
    CreditUseCase creditUseCase= new CreditUseCase();
    public static InnerMovementsFragment newInstance() {
        return new InnerMovementsFragment();
    }
    Spinner spinnerCredit;
    NavController navController;
    ArrayAdapter<String> creditAdapter;
    PdfViewViewModel pdfViewModel;
    File historic;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inner_movements, container, false);
        spinnerCredit=binding.spCreditType;
        pdfViewModel= new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        rootView = binding.rootView;
        binding.progressBar2.animate().alpha(0.0f);
        Utils.fillSpinnerWithCredit(getContext(),spinnerCredit);
        binding.historicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfViewModel.setFile(historic);
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_movements_to_nav_pdf_viewer);
            }
        });

        binding.shareHistoricPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(historic!=null){
                    Utils.sharePdf(historic,getActivity());
                }
               // Utils.showShareIntent(getActivity());
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
        binding.spCreditType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    binding.shareHistoricPdf.animate().alpha(0.0f);
                    binding.textDownloadHistoric.animate().alpha(0.0f);
                    binding.progressBar2.animate().alpha(1.0f);
                    creditUseCase.getInfoCreditHistoric(parent.getItemAtPosition(position).toString(),InnerMovementsFragment.this);}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return binding.getRoot();

    }
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_inner_movements);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }


    @Override
    public void onFailureRequest(String message) {

    }

    @Override
    public void onSuccesRequest(HistoricResponse object) {
        binding.shareHistoricPdf.animate().alpha(1.0f);
        binding.textDownloadHistoric.animate().alpha(1.0f);
        binding.progressBar2.animate().alpha(0.0f);
        try {
            historic=Utils.createPdfFromBase64(object.getReporte(),"historic", getActivity());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}