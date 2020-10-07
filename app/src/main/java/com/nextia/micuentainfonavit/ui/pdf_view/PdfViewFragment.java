package com.nextia.micuentainfonavit.ui.pdf_view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.ui.constancia.pdf_download.PdfConstanciaDownloadViewModel;

public class PdfViewFragment extends Fragment {

    PDFView mPdfView;



    private PdfViewViewModel mViewModel;
    public static PdfViewFragment newInstance() {
        return new PdfViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(getActivity()).get(PdfViewViewModel.class);
        View view= inflater.inflate(R.layout.fragment_pdf_view, container, false);
        mPdfView= view.findViewById(R.id.pdfView);
        mPdfView.fromFile(mViewModel.getFile().getValue()).load();
        return view;
    }


}