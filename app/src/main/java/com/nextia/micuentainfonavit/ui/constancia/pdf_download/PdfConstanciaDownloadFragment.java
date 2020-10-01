package com.nextia.micuentainfonavit.ui.constancia.pdf_download;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentPdfConstanciaDownloadBinding;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

public class PdfConstanciaDownloadFragment extends Fragment implements OnFinishRequestListener<CreditYearInfoResponse> {
    CreditUseCase creditUseCase= new CreditUseCase();
    private PdfConstanciaDownloadViewModel mViewModel;
    FragmentPdfConstanciaDownloadBinding binding;
    public static PdfConstanciaDownloadFragment newInstance() {
        return new PdfConstanciaDownloadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf_constancia_download, container, false);
        mViewModel= new ViewModelProvider(getActivity()).get(PdfConstanciaDownloadViewModel.class);
        //Toast.makeText(getContext(),mViewModel.getCredit().getValue()+" "+mViewModel.getYear().getValue(),Toast.LENGTH_LONG).show();
        creditUseCase.getInfoCreditYear(mViewModel.getCredit().getValue(),mViewModel.getYear().getValue(),PdfConstanciaDownloadFragment.this);

        binding.dowloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showShareIntent(getActivity());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView,R.layout.skeleton_pdf_constancia_download);
    }

    @Override
    public void onFailureRequest(String message) {
        Toast.makeText(getContext(),"no entro",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccesRequest(CreditYearInfoResponse object) {
       binding.setCredit(object);
        binding.tvNumCreditoImprPdf.setText(mViewModel.getCredit().getValue());
        binding.tvAnioImprPdf.setText(mViewModel.getYear().getValue());
        Utils.hideLoadingSkeleton();
        //Toast.makeText(getContext(),"si entro",Toast.LENGTH_LONG).show();
    }
}