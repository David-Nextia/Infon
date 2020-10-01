package com.nextia.micuentainfonavit.ui.movements.views.pay_options;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.micuentainfonavit.R;

public class PayOptionsFragment extends Fragment {
    NavController navController;
    private PayOptionsViewModel mViewModel;

    public static PayOptionsFragment newInstance() {
        return new PayOptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay_options, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_movements);
//        navController.navigate(R.id.action_nav_pay_options_to_nav_home);
        mViewModel = ViewModelProviders.of(this).get(PayOptionsViewModel.class);
        // TODO: Use the ViewModel
    }

}