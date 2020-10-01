package com.nextia.micuentainfonavit.ui.constancia.pdf_download;

import androidx.databinding.DataBindingUtil;
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

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentPdfConstanciaDownloadBinding;

public class PdfConstanciaDownloadFragment extends Fragment {

    private PdfConstanciaDownloadViewModel mViewModel;
    FragmentPdfConstanciaDownloadBinding binding;
    public static PdfConstanciaDownloadFragment newInstance() {
        return new PdfConstanciaDownloadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pdf_constancia_download, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView,R.layout.skeleton_pdf_constancia_download);
        new CountDownTimer(2000, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}