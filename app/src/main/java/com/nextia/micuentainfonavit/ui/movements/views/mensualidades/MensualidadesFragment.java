package com.nextia.micuentainfonavit.ui.movements.views.mensualidades;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

public class MensualidadesFragment extends Fragment {

    private MensualidadesViewModel mViewModel;
    private View rootView;
    public static MensualidadesFragment newInstance() {
        return new MensualidadesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mensualidades, container, false);
        rootView = root.findViewById(R.id.rootView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_mensualidades);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

}