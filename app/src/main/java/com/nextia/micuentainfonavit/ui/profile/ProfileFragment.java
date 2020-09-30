package com.nextia.micuentainfonavit.ui.profile;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.databinding.FragmentProfileBinding;
import com.nextia.micuentainfonavit.Utils;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setUser(Utils.getSharedPreferencesUserData(getContext()));
        return binding.getRoot();


    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(binding.rootView,R.layout.skeleton_profile);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}