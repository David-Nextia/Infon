package com.nextia.micuentainfonavit.ui.home.new_welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

public class NewWelcomeFragment extends Fragment {

    private View rootView;
    TextView name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_welcome, container, false);
        rootView = root.findViewById(R.id.rootView);
        name=root.findViewById(R.id.tv_name_bienvenida);
        name.setText(Utils.getSharedPreferencesUserData(getContext()).getNombre()+"!");
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView, R.layout.skeleton_new_welcome);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}