package com.nextia.micuentainfonavit.ui.movements.views.pay_options;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;
import com.nextia.micuentainfonavit.databinding.FragmentPayOptionsBinding;

import java.util.ArrayList;

public class PayOptionsFragment extends Fragment {

    private FragmentPayOptionsBinding binding;
    private PayOptionsViewModel mViewModel;
    private NavController navController;
    private View rootView;
    Spinner spinnerCredit;
    public static PayOptionsFragment newInstance() {
        return new PayOptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_options, container, false);
        spinnerCredit=binding.spCreditType;
        Utils.fillSpinnerWithCredit(getContext(),spinnerCredit);
        rootView = binding.rootView;
        binding.lyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_banks);
            }
        });

        binding.lyMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_market);
            }
        });

        binding.lyUsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
                navController.navigate(R.id.action_nav_pay_options_to_nav_pay_usa);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_pay_options);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }




}