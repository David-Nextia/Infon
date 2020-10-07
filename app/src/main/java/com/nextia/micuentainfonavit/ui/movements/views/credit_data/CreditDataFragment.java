package com.nextia.micuentainfonavit.ui.movements.views.credit_data;

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

public class CreditDataFragment extends Fragment {

    private CreditDataViewModel mViewModel;
    private View rootView;
    Spinner spinnerCredit;
    public static CreditDataFragment newInstance() {
        return new CreditDataFragment();
    }
    ArrayAdapter<String> creditAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_credit_data, container, false);
        spinnerCredit=root.findViewById(R.id.sp_credit_type);
        Utils.fillSpinnerWithCredit(getContext(),spinnerCredit);
        rootView = root.findViewById(R.id.rootView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_credit_data);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }

}