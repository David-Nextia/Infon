package com.nextia.micuentainfonavit.ui.profile;
/**
 * class that shows the profile info of the user
 */

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.databinding.FragmentProfileBinding;
import com.nextia.micuentainfonavit.Utils;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    //Creates the view and bind it
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setUser(Utils.getSharedPreferencesUserData(getContext()));
        return binding.getRoot();


    }

    //function before initial view to show and stop skeleton
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