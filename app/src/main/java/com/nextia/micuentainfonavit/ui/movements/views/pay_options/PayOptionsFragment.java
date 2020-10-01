package com.nextia.micuentainfonavit.ui.movements.views.pay_options;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.databinding.FragmentPayOptionsBinding;

public class PayOptionsFragment extends Fragment {

    private FragmentPayOptionsBinding binding;
    private PayOptionsViewModel mViewModel;
    private NavController navController;

    public static PayOptionsFragment newInstance() {
        return new PayOptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pay_options, container, false);

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PayOptionsViewModel.class);
        // TODO: Use the ViewModel
    }

}