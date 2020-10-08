package com.nextia.micuentainfonavit.ui.movements.views.mensualidades;
/**
 * view of mensualidades inside movements
 */

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
import android.widget.Spinner;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.util.ArrayList;

public class MensualidadesFragment extends Fragment {

    private View rootView;
    public static MensualidadesFragment newInstance() {
        return new MensualidadesFragment();
    }
    Spinner spinnerCredit;

    //creating view, and instance it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mensualidades, container, false);
        rootView = root.findViewById(R.id.rootView);
        spinnerCredit=root.findViewById(R.id.sp_credit_type);
        fillSpinner();
        return root;
    }

    //function before initial view to show and stop skeleton
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

    //method to fill spinner
    public void fillSpinner() {
        Utils.fillSpinnerWithCredit(getContext(), spinnerCredit);
    }

}