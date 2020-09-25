package com.nextia.micuentainfonavit.ui.movements.views.mensualidades;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.micuentainfonavit.R;

public class MensualidadesFragment extends Fragment {

    private MensualidadesViewModel mViewModel;

    public static MensualidadesFragment newInstance() {
        return new MensualidadesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mensualidades, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MensualidadesViewModel.class);
        // TODO: Use the ViewModel
    }

}