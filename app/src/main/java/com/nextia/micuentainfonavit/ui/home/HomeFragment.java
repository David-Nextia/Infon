package com.nextia.micuentainfonavit.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.nextia.domain.models.welcome.WelcomeCard;
import com.nextia.micuentainfonavit.R;
import com.nextia.micuentainfonavit.Utils;

import java.util.ArrayList;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment {

    /**
     * variables
     *
     * recyclerview: adapter to show each item
     * homeViewmodel: model with the data
     * rootView: main view of each card item
     */

    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = root.findViewById(R.id.recyclerwelcome);
        rootView = root.findViewById(R.id.rootView);
        ScrollingPagerIndicator recyclerIndicator = root.findViewById(R.id.indicatorrecycler);
        CardAdapter adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);
        recyclerIndicator.attachToRecyclerView(recyclerView);
        homeViewModel.getCards().observe(getViewLifecycleOwner(), new Observer<ArrayList<WelcomeCard>>() {
            @Override
            public void onChanged(ArrayList<WelcomeCard> welcomeCards) {
                adapter.setData(welcomeCards);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.showLoadingSkeleton(rootView,R.layout.skeleton_home);
        new CountDownTimer(1500, 1000) {
            public void onFinish() {
                Utils.hideLoadingSkeleton();
            }

            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}