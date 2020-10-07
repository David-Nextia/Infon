package com.nextia.micuentainfonavit.ui.constancia.pdf_download;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.domain.OnFinishRequestListener;
import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentPdfConstanciaDownloadBinding;
import com.nextia.micuentainfonavit.foundations.DialogInfonavit;
import com.nextia.micuentainfonavit.ui.pdf_view.PdfViewViewModel;
import com.nextia.micuentainfonavit.usecases.CreditUseCase;

import java.io.File;

public class PdfConstanciaDownloadFragment extends Fragment implements OnFinishRequestListener<CreditYearInfoResponse> {
    CreditUseCase creditUseCase= new CreditUseCase();
    PdfViewViewModel pdfViewModel;
    private PdfConstanciaDownloadViewModel mViewModel;
    FragmentPdfConstanciaDownloadBinding binding;
    NavController navController;
    File file;
    public static PdfConstanciaDownloadFragment newInstance() {
        return new PdfConstanciaDownloadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf_constancia_download, container, false);
        mViewModel= new ViewModelProvider(getActivity()).get(PdfConstanciaDownloadViewModel.class);
        pdfViewModel= new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        //Toast.makeText(getContext(),mViewModel.getCredit().getValue()+" "+mViewModel.getYear().getValue(),Toast.LENGTH_LONG).show();
        creditUseCase.getInfoCreditYear(mViewModel.getCredit().getValue(),mViewModel.getYear().getValue(),PdfConstanciaDownloadFragment.this);

        binding.dowloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_pdf_constancia_to_nav_pdf_viewer);
            }
        });
        binding.sharePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Utils.sharePdf(file,getActivity());
                //Utils.showShareIntent(getActivity());
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
        DialogInfonavit alertdialog = new DialogInfonavit(getContext(), getString(R.string.title_error),getString(R.string.message_server_error), DialogInfonavit.ONE_BUTTON_DIALOG);
        alertdialog.show();
    }

    @Override
    public void onSuccesRequest(CreditYearInfoResponse object) {
       binding.setCredit(object);
        binding.tvNumCreditoImprPdf.setText(mViewModel.getCredit().getValue());
        binding.tvAnioImprPdf.setText(mViewModel.getYear().getValue());
        mViewModel.setCreditInfo(object);
        try{
            file= Utils.createPdfFromCanvas(mViewModel,"Constancia",getActivity());
        }catch (Exception e){

        }

        pdfViewModel.setFile(file);
        Utils.hideLoadingSkeleton();

    }
}